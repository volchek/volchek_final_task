package by.tr.web.dao.impl;

import java.util.Properties;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.impl.mysql_util.ConnectionPoolFactory;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.properties_util.PropertiesReader;

public class DatabaseDaoImpl implements DatabaseDao {

	private PropertiesReader reader = new PropertiesReader();
	private ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static int MAX_POOL_SIZE = 10;
	private String CONNECTION_URL;

	@Override
	public void initConnectionPool() {
		Properties properties = reader.readDatabaseProperties();
		if (properties == null) {
			System.err.println("EMPTY!!!");
		} else {
			String database = properties.getProperty("database");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			String host = properties.getProperty("host");
			String port = properties.getProperty("port");
			String driver = properties.getProperty("driver");
			CONNECTION_URL = "jdbc:mysql://" + host + ":" + port + "/" + database;
			System.out.println(CONNECTION_URL);
			try {
				connPool.initializePool(CONNECTION_URL, username, password, driver, MAX_POOL_SIZE);
			} catch (MySqlException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clearConnectionPool() {

		connPool.finishWork();

	}

}
