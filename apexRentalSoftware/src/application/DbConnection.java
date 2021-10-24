package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static Connection con = null;

	// private ctor to stop object creation
	private DbConnection() {
	}

	// static initialization block connects to database using URl and credentials
	static {
		String mySqlUrl = "jdbc:mysql://localhost:3306/apex_rental_software";
		String user = "root";
		String pass = "";

		try {
			con = DriverManager.getConnection(mySqlUrl, user, pass);
		} catch (SQLException e) {
			System.out.println("ERROR: Failed to connect!");
			e.printStackTrace();
		}
	}

	// method to access connection
	public static Connection getConnection() {
		return con;
	}
}
