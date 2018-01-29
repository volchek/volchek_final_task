package by.tr.web.dao.language.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.mysql.submitter.LanguageQuerySubmitter;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.database.util.pool.ConnectionPool;
import by.tr.web.dao.database.util.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.language.LanguageDao;
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
			LanguageQuerySubmitter query = new LanguageQuerySubmitter();

			Map<String, Integer> languages = query.getAllLanguageInfo(conn);
			fillLanguageMaps(languages);

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get information about all languages", ex);
			throw new DaoException("Failed to execute command and extract information about all languages", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public List<String> findFrequentLanguages() throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			LanguageQuerySubmitter query = new LanguageQuerySubmitter();
			List<String> languages = query.findLanguageFrequency(conn);
			return languages;
		} catch (MySqlException e) {
			logger.error("Can't execute query and extract information about language frequency");
			throw new DaoException("Failed to execute command and find language frequency");
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
