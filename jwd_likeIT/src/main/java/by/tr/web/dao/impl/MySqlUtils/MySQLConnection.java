package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnection {
	
	// Later default values may be saved in xml file
	private static final String HOST = "localhost";
	private static final String DATABASE = "java_users";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "12345";
	private static final String PORT = ":3306/";
	private static final String PREFIX = "jdbc:mysql://";
	private static final String DRIVER_CLASSNAME = "com.mysql.jdbc.Driver";

	
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
	
	Class.forName(DRIVER_CLASSNAME);
		
		String connectionURL = PREFIX + HOST + PORT + DATABASE;
	
		Connection connection = DriverManager.getConnection(connectionURL, USER_NAME, PASSWORD);
	
		return connection;
	}
		
}
