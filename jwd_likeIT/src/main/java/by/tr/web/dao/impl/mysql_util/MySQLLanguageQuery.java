package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.entity.User;
import by.tr.web.entity.language.Language;
import by.tr.web.entity.language.LanguageCommand;

public class MySQLLanguageQuery {

	private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM Languages";

	private static final String SELECT_ALL_LANGUAGES_FOR_USER = "SELECT lang.languageId, " + "lang.language, us.level "
			+ "FROM likeit2.users2languages AS us " + "INNER JOIN likeit2.languages AS lang "
			+ "ON lang.languageId = us.languageId " + "WHERE us.userId = ?;";

	private static final String INSERT_LANGUAGE = "INSERT INTO users2languages " + "(userId, languageId, level) "
			+ "VALUES (?, ?, ?);";

	private static final String UPDATE_USER_LANGUAGE = "UPDATE users2languages "
			+ "SET level = ? WHERE userId = ? and languageId = ?;";

	private static final String DELETE_USER_LANGUAGE = "DELETE FROM users2languages"
			+ "	WHERE userId = ? and languageId = ?;";

	private Map<String, Integer> languages = new ConcurrentHashMap<String, Integer>();

	public void getAllLanguages(Connection conn) throws SQLException, MySqlException {

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_LANGUAGES);
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				Integer langID = rs.getInt(DatabaseField.LANGUAGE_ID);
				languages.put(langName, langID);
			}
		} catch (SQLException ex) {
			// add logging
			throw new MySqlException("Can't get language list", ex);

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void getUserLanguages(Connection conn, User user) throws SQLException, MySqlException {

		Map<Language, Integer> userLanguages = getUserLanguages(conn, user.getId());
		user.setLanguages(userLanguages);

	}

	/*
	 * LanguageCommand languageCommand = LanguageCommand.getInstance();
	 * PreparedStatement ps = null;
	 * 
	 * try { ps = conn.prepareStatement(SELECT_ALL_LANGUAGES_FOR_USER);
	 * ps.setInt(1, user.getId()); ResultSet rs = ps.executeQuery(); while
	 * (rs.next()) { String langName =
	 * rs.getString(DatabaseField.LANGUAGE_NAME); Integer level =
	 * rs.getInt(DatabaseField.LANGUAGE_LEVEL);
	 * user.addLanguage(languageCommand.getLanguage(langName), level); } } catch
	 * (SQLException ex) { // add logging throw new
	 * MySqlException("Can't get language list for user " + user.getId(), ex); }
	 * finally { if (ps != null) { ps.close(); } } }
	 */
	public Map<Language, Integer> getUserLanguages(Connection conn, int userId) throws SQLException, MySqlException {

		LanguageCommand languageCommand = LanguageCommand.getInstance();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(SELECT_ALL_LANGUAGES_FOR_USER);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			Map<Language, Integer> userLanguages = new HashMap<Language, Integer>();
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				Integer level = rs.getInt(DatabaseField.LANGUAGE_LEVEL);
				userLanguages.put(languageCommand.getLanguage(langName), level);
			}
			return userLanguages;
		} catch (SQLException ex) {
			// add logging
			throw new MySqlException("Can't get language list for user " + userId, ex);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

	public void insertUserLanguages(Connection conn, Map<Language, Integer> userLanguages, int userId)
			throws MySqlException, SQLException {

		for (Map.Entry<Language, Integer> entry : userLanguages.entrySet()) {
			Language currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			insertOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public void insertOneUserLanguage(Connection conn, int userId, Language language, Integer score)
			throws MySqlException, SQLException {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(INSERT_LANGUAGE);
			Integer langId = languages.get(language.getName());
			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.setInt(3, score);
			ps.executeUpdate();
		} catch (SQLException ex) {
			String error = "Can't insert language " + language.getName() + " for user " + userId;
			// Add logging;
			throw new SQLException(error, ex);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

	public void updateUserLanguages(Connection conn, Map<Language, Integer> userLanguages, int userId)
			throws MySqlException, SQLException {

		for (Map.Entry<Language, Integer> entry : userLanguages.entrySet()) {
			Language currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			updateOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public void updateOneUserLanguage(Connection conn, int userId, Language language, Integer score)
			throws MySqlException, SQLException {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(UPDATE_USER_LANGUAGE);
			Integer langId = languages.get(language.getName());
			ps.setInt(1, score);
			ps.setInt(2, userId);
			ps.setInt(3, langId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			String error = "Can't update language " + language.getName() + " for user " + userId;
			// Add logging;
			throw new SQLException(error, ex);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

	public void deleteUserLanguages(Connection conn, Map<Language, Integer> userLanguages, int userId)
			throws MySqlException, SQLException {

		for (Map.Entry<Language, Integer> entry : userLanguages.entrySet()) {
			Language currentLanguage = entry.getKey();
			deleteOneUserLanguage(conn, userId, currentLanguage);
		}
	}

	public void deleteOneUserLanguage(Connection conn, int userId, Language language)
			throws MySqlException, SQLException {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(DELETE_USER_LANGUAGE);
			Integer langId = languages.get(language.getName());
			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.execute();
		} catch (SQLException ex) {
			String error = "Can't delete language " + language.getName() + " for user " + userId;
			// Add logging;
			throw new SQLException(error, ex);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

	public boolean updateUserLanguages(Connection conn, User currentUser, User modifiedUser) {

		Map<Language, Integer> updatedLanguages = new HashMap<Language, Integer>();
		Map<Language, Integer> insertedLanguages = new HashMap<Language, Integer>();
		Map<Language, Integer> deletedLanguages = new HashMap<Language, Integer>();
		sortLanguages(currentUser, modifiedUser, updatedLanguages, insertedLanguages, deletedLanguages);

		try {
			conn.setAutoCommit(false);
			updateLanguageList(conn);
			int userId = currentUser.getId();
			insertUserLanguages(conn, insertedLanguages, userId);
			updateUserLanguages(conn, updatedLanguages, userId);
			deleteUserLanguages(conn, deletedLanguages, userId);
			conn.commit();
			return true;
		} catch (SQLException | MySqlException e) {
			try {
				// add logging
				conn.rollback();
			} catch (SQLException e1) {
				// add logging
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// Add logging
			}
		}
		return false;
	}

	public Map<String, Integer> getLanguages() {
		return languages;
	}

	private void sortLanguages(User currentUser, User modifiedUser, Map<Language, Integer> updatedLang,
			Map<Language, Integer> insertedLang, Map<Language, Integer> removedLang) {

		Map<Language, Integer> oldLanguages = currentUser.getLanguages();
		Map<Language, Integer> newLanguages = modifiedUser.getLanguages();

		for (Map.Entry<Language, Integer> oneLang : newLanguages.entrySet()) {
			Language key = oneLang.getKey();
			if (oldLanguages.containsKey(key)) {
				if (!(oldLanguages.get(key)).equals(oneLang.getValue())) {
					updatedLang.put(key, oneLang.getValue());
				}
			} else {
				insertedLang.put(key, oneLang.getValue());
			}
		}

		for (Map.Entry<Language, Integer> oneLang : oldLanguages.entrySet()) {
			Language key = oneLang.getKey();
			if (!newLanguages.containsKey(key)) {
				removedLang.put(key, oneLang.getValue());
			}
		}
	}

	private void updateLanguageList(Connection conn) throws SQLException, MySqlException {
		getLanguages().clear();
		getAllLanguages(conn);
	}

}
