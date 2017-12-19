package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;

public class MySQLConnection {

	// Later default values may be saved in xml file
	private static final String HOST = "localhost";
	private static final String DATABASE = "LikeIT";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "12345";
	private static final String PORT = ":3306/";
	private static final String PREFIX = "jdbc:mysql://";
	private static final String DRIVER_CLASSNAME = "com.mysql.jdbc.Driver";
	private static final String CONNECTION_URL = PREFIX + HOST + PORT + DATABASE;

	private final static Logger logger = LogManager.getLogger();

	public static Connection getMySQLConnection() throws MySqlFatalException {

		try {
			Class.forName(DRIVER_CLASSNAME);
			Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
			return connection;
		} catch (ClassNotFoundException ex) {
			logger.fatal("Driver not found");
			throw new MySqlFatalException("Driver not found", ex);
		} catch (SQLException ex) {
			logger.fatal("Can't connect to database " + CONNECTION_URL);
			throw new MySqlFatalException("Can't connect to database");
		}
	}

}
