package by.tr.web.dao.impl;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;
import by.tr.web.dao.impl.mysql_util.properties_util.PropertiesReader;

public class DatabaseDaoImpl implements DatabaseDao {

	private PropertiesReader reader = new PropertiesReader();
	private ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger();

	@Override
	public void initConnectionPool() throws FatalDaoException {
		Properties properties = null;

		try {
			properties = reader.readDatabaseProperties();
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException(ex);
		}

		String database = properties.getProperty("database");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String driver = properties.getProperty("driver");
		String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;

		try {
			connPool.initializePool(connectionUrl, username, password, driver);
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't initialize connection pool", ex);
		}

		try {
			setSpecialPoolProperties();
		} catch (MySqlException e) {
			logger.warn("Can't read connection pool properties. Default values will be used.");
		}
	}

	@Override
	public void clearConnectionPool() {
		connPool.finishWork();
	}
	
	private void setSpecialPoolProperties() throws MySqlException {
		Properties properties = new Properties();
		properties = reader.readPoolProperties();
		int size = Integer.parseInt(properties.getProperty("max_size"));
		connPool.setMaxSize(size);
	}

}
