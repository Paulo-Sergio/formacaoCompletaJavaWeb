package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class LoginDao {

	private static Connection connection;

	public LoginDao() {
		connection = SingleConnection.getConnection();
	}

	public boolean validarLogin(String login, String senha) throws Exception {
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
