package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.Produto;

public class ProdutoDao {

	private static Connection connection;

	public ProdutoDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Produto produto) {
		try {
			String sql = "INSERT INTO produtos (nome, quantidade, valor) VALUES (?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());

			stmt.execute();

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void atualizar(Produto produto) {
		try {
			String sql = "UPDATE produtos SET nome = ?, quantidade = ?, valor = ? WHERE id = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());
			stmt.setLong(4, produto.getId());

			stmt.executeUpdate();

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<Produto> listarTodos() throws Exception {
		List<Produto> produtos = new ArrayList<>();

		String sql = "SELECT * FROM produtos";

		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			Produto produto = new Produto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getInt("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));

			produtos.add(produto);
		}

		return produtos;

	}

	public void deletar(Long id) {
		try {
			String sql = "DELETE FROM produtos WHERE id = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, id);

			stmt.execute();

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public Produto buscarPorId(Long id) throws Exception {
		String sql = "SELECT * FROM produtos WHERE id = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			Produto produto = new Produto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getInt("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			return produto;
		}

		return null;
	}

	public boolean isExistePorNome(String nome) throws Exception {
		String sql = "SELECT count(1) as qtd FROM produtos WHERE nome = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, nome);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") > 0;
		}

		return false;
	}

}
