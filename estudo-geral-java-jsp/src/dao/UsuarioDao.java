package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDataBaseteste;
import model.Usuario;

public class UsuarioDao {

	private static Connection connection;

	public UsuarioDao() {
		connection = ConnectionDataBaseteste.getConnection();
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

}
