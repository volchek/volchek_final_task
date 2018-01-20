package by.tr.web.dao.database.util.pool;

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
