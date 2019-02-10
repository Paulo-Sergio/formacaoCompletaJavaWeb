package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDataBase {

	private static String banco = "jdbc:mysql://localhost:3306/formacao_completa_javaweb_curso_jsp";
	private static String password = "";
	private static String user = "root";
	private static Connection connection = null;

	static {
		conectar();
	}

	public ConnectionDataBase() {
		conectar();
	}

	public static Connection getConnection() {
		return connection;
	}

	private static void conectar() {
		try {

			if (connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro ao conectar com banco de dados... " + e.getMessage());
		}
	}
}
