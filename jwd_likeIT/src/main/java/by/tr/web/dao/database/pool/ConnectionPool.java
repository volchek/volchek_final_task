package by.tr.web.dao.database.pool;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Iterator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.exception.database.MySqlFatalException;

public class ConnectionPool {

	private String url;
	private String userName;
	private String password;
	private int maxSize = 5;

	private BlockingQueue<Connection> freeConnQueue;
	private BlockingQueue<Connection> workingConnQueue;

	private final static Logger logger = LogManager.getLogger(ConnectionPool.class.getName());

	public ConnectionPool() {
	
	}

	public ConnectionPool(String url, String userName, String password, String driver) throws MySqlFatalException {
		initializePool(url, userName, password, driver);
	}

	public void initializePool(String url, String userName, String password, String driver) throws MySqlFatalException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			logger.fatal("Can not register driver");
			throw new MySqlFatalException("Can not register driver", ex);
		}
		
		this.url = url;
		this.userName = userName;
		this.password = password;
		
		freeConnQueue = new ArrayBlockingQueue<>(maxSize);
		workingConnQueue = new ArrayBlockingQueue<>(maxSize);
		
		try {
			createConnections();
		} catch (MySqlException ex) {
			throw new MySqlFatalException(ex);
		}
	}

	public void setMaxSize(int size) {
		maxSize = size;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Connection getConnection() throws MySqlException {
		Connection conn = null;
		if (!freeConnQueue.isEmpty()) {
			try {
				conn = freeConnQueue.take();
				workingConnQueue.put(conn);
			} catch (InterruptedException ex) {
				logger.error("Can't get free connection");
				throw new MySqlException("Can't get connection", ex);
			}
		}
		return conn;
	}

	public boolean isConnectionClose(Connection conn){
		if (workingConnQueue.contains(conn)){
			return false;
		}
		return true;
	}

	public void closeConnection(Connection conn) {
		if (conn == null) {
			logger.error("You try close empty connection");		}

		if (!workingConnQueue.remove(conn)) {
			logger.error("Try remove unexpected connection");
		}

		try {
			freeConnQueue.put(conn);
		} catch (InterruptedException ex) {
			logger.error("Can't add connection to queue");
		}
	}
	
	public void closeConnection(Connection conn, Statement st) throws MySqlException {
		closeConnection(conn);
		closeStatement(st);
	}

	public void closeConnection(Connection conn, Statement st, ResultSet rs) throws MySqlException {
		closeConnection(conn);
		closeStatement(st);
		closeResultSet(rs);
	}

	public void finishWork() {
		Iterator<Connection> it = freeConnQueue.iterator();
		try {
			while (it.hasNext()) {
				closeOneConnection(it.next());
			}
		} catch (MySqlException ex) {
			logger.error("Can't close database Connection");
		}
	}

	private void createConnections() throws MySqlException {
		for (int i = 0; i < maxSize; i++) {
			Connection connection = null;
			try {
				connection = DriverManager.getConnection(url, userName, password);
				freeConnQueue.put(connection);
			} catch (SQLException ex) {
				logger.error("Can't create a new connection", ex);
				throw new MySqlException("Can't create a new connection");
			} catch (InterruptedException ex) {
				logger.error("Can't add a new connection");
				throw new MySqlException("Can't add a new connection");
			}
		}
	}

	private void closeOneConnection(Connection conn) throws MySqlException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			throw new MySqlException("Can't close database Connection", ex);
		}
	}

	private void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can't close Statement");
		}
	}

	private void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can't close Result Set");
		}
	}
}
