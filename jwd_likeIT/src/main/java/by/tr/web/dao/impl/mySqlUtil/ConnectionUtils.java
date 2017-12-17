package by.tr.web.dao.impl.mySqlUtil;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import by.tr.web.dao.impl.mySqlUtil.mySqlException.MySqlException;
import by.tr.web.dao.impl.mySqlUtil.mySqlException.MySqlFatalException;
import by.tr.web.dao.impl.mySqlUtil.mySqlException.MySqlMessage;

public class ConnectionUtils {

	private final static Logger logger = LogManager.getLogger();

	public static Connection getConnection() throws MySqlFatalException {
		return MySQLConnection.getMySQLConnection();
	}

	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException ex) {
			logger.warn(MySqlMessage.DATABASE_CLOSE_ERROR);
			new MySqlException(MySqlMessage.DATABASE_CLOSE_ERROR, ex);
		}
	}

}
