package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.MySQLLanguageQuery;
import by.tr.web.dao.impl.mysql_util.MySQLUserQuery;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;

public class MySQLLanguageDaoImpl implements LanguageDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLLanguageDaoImpl.class.getName());

	@Override
	public List<String> getLanguageList() throws DaoException, FatalDaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			MySQLLanguageQuery query = new MySQLLanguageQuery();

			List<String> languages = query.getLanguageNames(conn);
			return languages;

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get a language list", ex);
		} finally {
			connPool.closeConnection(conn);
		}
		return null;
	}

}
