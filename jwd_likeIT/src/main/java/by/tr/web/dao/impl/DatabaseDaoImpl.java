package by.tr.web.dao.impl;

import java.util.ResourceBundle;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseDaoImpl implements DatabaseDao {

	private ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(DatabaseDaoImpl.class.getName());

	@Override
	public void initConnectionPool() throws FatalDaoException {

		ResourceBundle bundle = ResourceBundle.getBundle("database");

		String database = bundle.getString("database");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		String host = bundle.getString("host");
		String port = bundle.getString("port");
		String driver = bundle.getString("driver");
		String collation = bundle.getString("collation");
		String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
		if (collation.equals("utf-8")) {
			connectionUrl += "?useUnicode=true&characterEncoding=utf-8";
		}

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
		ResourceBundle bundle = ResourceBundle.getBundle("pool");
		String poolSize = bundle.getString("max_size");
		int size = Integer.parseInt(poolSize);
		connPool.setMaxSize(size);
	}

}
