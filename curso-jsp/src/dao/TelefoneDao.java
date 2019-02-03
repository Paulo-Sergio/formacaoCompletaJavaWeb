package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.Telefone;
import model.Usuario;

public class TelefoneDao {

	private static Connection connection;

	public TelefoneDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Telefone telefone) {
		try {
			String sql = "INSERT INTO telefones (usuario_id, numero, tipo) VALUES (?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, telefone.getUsuario().getId());
			stmt.setString(2, telefone.getNumero());
			stmt.setString(3, telefone.getTipo());

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

	public List<Telefone> listarTodos(Long idUsuario) throws Exception {
		Usuario usuario = new UsuarioDao().buscarPorId(idUsuario);
		
		List<Telefone> telefones = new ArrayList<Telefone>();
		String sql = "SELECT * FROM telefones WHERE usuario_id = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, idUsuario);

		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			Telefone telefone = new Telefone();
			telefone.setId(resultSet.getLong("id"));
			telefone.setUsuario(usuario);
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));

			telefones.add(telefone);
		}

		return telefones;

	}

	public void deletar(Long id) {
		try {
			String sql = "DELETE FROM telefones WHERE id = ?";

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

}
