package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.impl.mysql_util.MySQLLanguageQuery;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;

public class MySQLLanguageDaoImpl implements LanguageDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLLanguageDaoImpl.class.getName());

	@Override
	public void extractAllLanguageInfo() throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			MySQLLanguageQuery query = new MySQLLanguageQuery();

			Map<String, Integer> languages = query.getAllLanguageInfo(conn);
			fillLanguageMaps(languages);

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get information about all languages", ex);
			throw new DaoException("Failed to execute command and extract information about all languages", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	private void fillLanguageMaps(Map<String, Integer> languageInfo) throws DaoException {

		if (languageInfo == null) {
			throw new DaoException("There isn't information about languages in the database");
		}

		LanguageSetSingleton langSetSingleton = LanguageSetSingleton.getInstance();
		LanguageSet langSet = langSetSingleton.getLanguageSet();

		for (Map.Entry<String, Integer> oneLanguageInfo : languageInfo.entrySet()) {
			langSet.addLanguage(oneLanguageInfo.getKey(), oneLanguageInfo.getValue());
		}

	}

}
