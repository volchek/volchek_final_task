package by.tr.web.dao.impl.mysql_util.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;

public class ConnectionPool {

	private String url;
	private String userName;
	private String password;
	private int maxSize;

	private BlockingQueue<Connection> freeConnQueue;
	private BlockingQueue<Connection> workingConnQueue;

	public ConnectionPool() {
	}

	public ConnectionPool(String url, String userName, String password, String driver, int maxSize)
			throws MySqlException {
		initializePool(url, userName, password, driver, maxSize);
	}

	public void initializePool(String url, String userName, String password, String driver, int maxSize)
			throws MySqlException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			throw new MySqlException("Can not register driver", ex);
		}
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.maxSize = maxSize;
		freeConnQueue = new ArrayBlockingQueue<>(maxSize);
		workingConnQueue = new ArrayBlockingQueue<>(maxSize);
		createConnections();
	}

	public Connection getConnection() throws MySqlException {
		Connection conn = null;
		if (freeConnQueue.size() > 0) {
			try {
				conn = freeConnQueue.take();
				workingConnQueue.put(conn);
			} catch (InterruptedException ex) {
				throw new MySqlException("Can't get connection", ex);
			}
		}
		return conn;
	}

	public void closeConnection(Connection conn) throws MySqlException {
		if (conn == null) {
			throw new MySqlException("Empty connection");
		}
		if (!workingConnQueue.remove(conn)) {
			throw new MySqlException("Try remove unexpected connection");
		}

		try {
			freeConnQueue.put(conn);
		} catch (InterruptedException ex) {
			throw new MySqlException("Can't add connection", ex);
		}
	}

	public void finishWork() {
		Iterator<Connection> it = freeConnQueue.iterator();
		while (it.hasNext()) {
			closeOneConnection(it.next());
		}
	}

	private void closeOneConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			new MySqlException("Can not close Database Connection", ex);
		}
	}

	private void createConnections() {
		for (int i = 0; i < maxSize; i++) {
			Connection connection;
			try {
				connection = DriverManager.getConnection(url, userName, password);
				freeConnQueue.put(connection);
			} catch (SQLException ex) {
				new MySqlException("Can not create new connection");
			} catch (InterruptedException ex) {
				new MySqlException("Can not add new connection");
			}
		}
	}

}
