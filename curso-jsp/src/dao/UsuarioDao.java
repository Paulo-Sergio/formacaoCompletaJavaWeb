package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.Usuario;

public class UsuarioDao {

	private static Connection connection;

	public UsuarioDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Usuario usuario) {
		try {
			String sql = "INSERT INTO usuarios (login, nome, senha, telefone) VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getTelefone());

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

	public void atualizar(Usuario usuario) {
		try {
			String sql = "UPDATE usuarios SET login = ?, nome = ?, senha = ?, telefone = ? WHERE id = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getTelefone());
			stmt.setLong(5, usuario.getId());

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

	public List<Usuario> listarTodos() throws Exception {
		List<Usuario> usuarios = new ArrayList<>();

		String sql = "SELECT * FROM usuarios";

		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setTelefone(resultSet.getString("telefone"));

			usuarios.add(usuario);
		}

		return usuarios;

	}

	public void deletar(Long id) {
		try {
			String sql = "DELETE FROM usuarios WHERE id = ?";

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

	public Usuario buscarPorId(Long id) throws Exception {
		String sql = "SELECT * FROM usuarios WHERE id = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setTelefone(resultSet.getString("telefone"));
			return usuario;
		}

		return null;
	}
	
	public boolean isExistePorLogin(String login) throws Exception {
		String sql = "SELECT count(1) as qtd FROM usuarios WHERE login = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, login);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") > 0;
		}

		return false;
	}
	
	public boolean autenticar(String login, String senha) throws Exception {
		String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, login);
		stmt.setString(2, senha);

		ResultSet resultSet = stmt.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}

}
