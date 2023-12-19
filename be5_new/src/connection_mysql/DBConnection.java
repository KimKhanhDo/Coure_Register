package connection_mysql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection makeConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_be5?serverTimezone=UTC",
					"newuser1",
					"khanh");
			return conn;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
