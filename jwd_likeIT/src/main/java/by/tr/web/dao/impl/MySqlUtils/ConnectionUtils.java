package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.dao.exception.DaoException;


public class ConnectionUtils {
	
	private static final String ERROR_MESSAGE = "Can't close database";


	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		return MySQLConnection.getMySQLConnection();
	}
   
    
	public static void close(Connection connection) {
		try {
			connection.close();
		} 
		catch (SQLException ex) {
			new DaoException(ERROR_MESSAGE, ex);
		}
    }
  
}	

