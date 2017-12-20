package by.tr.web.dao.impl.mysql_util;

import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;

public class ConnectionPoolFactory {

	private static final ConnectionPoolFactory instance = new ConnectionPoolFactory();

	private final ConnectionPool pool = new ConnectionPool();

	private ConnectionPoolFactory() {
	}

	public ConnectionPool getConnectionPool() {
		return pool;
	}

	public static ConnectionPoolFactory getInstance() {
		return instance;
	}

}
