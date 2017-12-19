package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;

import org.apache.logging.log4j.LogManager;

public class ConnectionUtil {

	private final static Logger logger = LogManager.getLogger();

	public static Connection getConnection() throws MySqlFatalException {
		return MySQLConnection.getMySQLConnection();
	}

	public static void close(Connection connection) {
		try {
			if (connection != null){
				connection.close();				
			}
		} catch (SQLException ex) {
			logger.warn("Can not close Database Connection");
			new MySqlException("Can not close Database Connection");
		}
	}

}
