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

	public void atualizar(Usuario usuario) {
		try {
			String sql = "UPDATE usuarios SET login = ?, senha = ? WHERE id = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setLong(3, usuario.getId());

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
			usuario.setSenha(resultSet.getString("senha"));

			usuarios.add(usuario);
		}

		return usuarios;

	}

	public void deletar(String login) {
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

	public Usuario buscarPorLogin(String login) throws Exception {
		String sql = "SELECT * FROM usuarios WHERE login = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, login);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			return usuario;
		}

		return null;
	}

}
