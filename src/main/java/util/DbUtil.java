package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	
	private DbUtil() {
	}
	private static final String URL = "jdbc:mysql://localhost:3306/work_test";
	static {
		try {				
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection connect(String user, String password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, user, password);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
