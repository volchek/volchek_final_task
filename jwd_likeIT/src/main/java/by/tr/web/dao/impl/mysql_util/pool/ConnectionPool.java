package by.tr.web.dao.impl.mysql_util.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;

public class ConnectionPool {

	private String url;
	private String userName;
	private String password;
	private int maxSize = 5;

	private BlockingQueue<Connection> freeConnQueue;
	private BlockingQueue<Connection> workingConnQueue;

	private final static Logger logger = LogManager.getLogger();

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

	public Connection getConnection() throws MySqlFatalException {
		Connection conn = null;
		if (freeConnQueue.size() > 0) {
			try {
				conn = freeConnQueue.take();
				workingConnQueue.put(conn);
			} catch (InterruptedException ex) {
				logger.error("Can't get connection");
				throw new MySqlFatalException("Can't get connection", ex);
			}
		}
		return conn;
	}

	public void closeConnection(Connection conn) throws MySqlException {
		if (conn == null) {
			logger.error("Empty connection");
			throw new MySqlException("Empty connection");
		}
		if (!workingConnQueue.remove(conn)) {
			logger.error("Try remove unexpected connection");
			throw new MySqlException("Try remove unexpected connection");
		}

		try {
			freeConnQueue.put(conn);
		} catch (InterruptedException ex) {
			logger.error("Can't add connection to queue");
			throw new MySqlException("Can't add connection", ex);
		}
	}

	public void finishWork() {
		Iterator<Connection> it = freeConnQueue.iterator();
		try {
			while (it.hasNext()) {
				closeOneConnection(it.next());
			}
		} catch (MySqlException ex) {
			logger.error("Can not close Database Connection");
		}
	}

	private void closeOneConnection(Connection conn) throws MySqlException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			throw new MySqlException("Can not close Database Connection", ex);
		}
	}

	private void createConnections() throws MySqlException {
		for (int i = 0; i < maxSize; i++) {
			Connection connection;
			try {
				connection = DriverManager.getConnection(url, userName, password);
				freeConnQueue.put(connection);
			} catch (SQLException ex) {
				logger.error("Can not create a new connection");
				throw new MySqlException("Can not create a new connection");
			} catch (InterruptedException ex) {
				logger.error("Can not add a new connection");
				throw new MySqlException("Can not add a new connection");
			}
		}
	}

}
