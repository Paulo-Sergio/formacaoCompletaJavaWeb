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
			String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());

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

	public List<Usuario> listarTodos() throws Exception {
		List<Usuario> usuarios = new ArrayList<>();

		String sql = "SELECT * FROM usuarios";

		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			Usuario usuario = new Usuario();
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));

			usuarios.add(usuario);
		}

		return usuarios;

	}
	
	public void delete(String login) {
		try {
			String sql = "DELETE FROM usuarios WHERE login = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, login);

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
