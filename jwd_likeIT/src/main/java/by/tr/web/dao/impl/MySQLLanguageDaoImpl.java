package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.database.pool.ConnectionPool;
import by.tr.web.dao.database.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.submitter.LanguageQuerySubmitter;
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

			Map<String, Integer> languages = LanguageQuerySubmitter.getAllLanguageInfo(conn);
			fillLanguageMaps(languages);

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get information about all languages");
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
			List<String> languages = LanguageQuerySubmitter.findLanguageFrequency(conn);
			return languages;
		} catch (MySqlException ex) {
			logger.error("Can't execute query and extract information about language frequency");
			throw new DaoException("Failed to execute command and find language frequency", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public void addLanguage(String language) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			LanguageQuerySubmitter.insertLanguage(conn, language);
		} catch (MySqlException ex) {
			logger.error("Can't execute query and insert a language with name '" + language + "'");
			throw new DaoException("Failed to execute command and insert a new language", ex);
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

		langSet.clearLanguageSet();

		for (Map.Entry<String, Integer> oneLanguageInfo : languageInfo.entrySet()) {
			langSet.addLanguage(oneLanguageInfo.getKey(), oneLanguageInfo.getValue());
		}
	}

}
