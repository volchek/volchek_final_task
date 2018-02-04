package by.tr.web.dao.database.pool;

public final class ConnectionPoolFactory {

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
