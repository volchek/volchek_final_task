package by.tr.web.dao.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.query.util.DatabaseField;
import by.tr.web.entity.User;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;

public class MySQLLanguageQuery {

	private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM Languages";
	private static final String SELECT_LANGUAGE_NAMES = "SELECT language FROM Languages";
	private static final String SELECT_ALL_LANGUAGES_FOR_USER = "SELECT lang.languageId, " + "lang.language, us.level "
			+ "FROM likeit2.users2languages AS us " + "INNER JOIN likeit2.languages AS lang "
			+ "ON lang.languageId = us.languageId " + "WHERE us.userId = ?;";
	private static final String INSERT_LANGUAGE = "INSERT INTO users2languages " + "(userId, languageId, level) "
			+ "VALUES (?, ?, ?);";
	private static final String UPDATE_USER_LANGUAGE = "UPDATE users2languages "
			+ "SET level = ? WHERE userId = ? and languageId = ?;";
	private static final String DELETE_USER_LANGUAGE = "DELETE FROM users2languages"
			+ "	WHERE userId = ? and languageId = ?;";

	private final static Logger logger = LogManager.getLogger(MySQLLanguageQuery.class.getName());

	private LanguageSet languageSet = LanguageSetSingleton.getInstance().getLanguageSet();

	public Map<String, Integer> getAllLanguageInfo(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL_LANGUAGES)) {
			Map<String, Integer> languages = new HashMap<String, Integer>();
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				Integer langID = rs.getInt(DatabaseField.LANGUAGE_ID);
				languages.put(langName, langID);
			}
			return languages;
		} catch (SQLException ex) {
			logger.error("Can't get full language list");
			throw new MySqlException("Can't get full language list", ex);
		}
	}

	public List<String> getLanguageNames(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_LANGUAGE_NAMES)) {
			List<String> languages = new ArrayList<String>();
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				languages.add(langName);
			}
			return languages;
		} catch (SQLException ex) {
			logger.error("Can't get a list of language names");
			throw new MySqlException("Can't get a list of language names");
		}
	}

	public void getUserLanguages(Connection conn, User user) throws MySqlException {

		Map<String, Integer> userLanguages = getUserLanguages(conn, user.getId());
		user.setLanguages(userLanguages);
	}

	public Map<String, Integer> getUserLanguages(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_LANGUAGES_FOR_USER)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			Map<String, Integer> userLanguages = new HashMap<String, Integer>();
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				Integer level = rs.getInt(DatabaseField.LANGUAGE_LEVEL);
				userLanguages.put(langName, level);
			}
			return userLanguages;
		} catch (SQLException ex) {
			logger.error("Can't get language list for user " + userId);
			throw new MySqlException("Can't get language list for user " + userId, ex);
		}
	}

	public void insertUserLanguages(Connection conn, Map<String, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<String, Integer> entry : userLanguages.entrySet()) {
			String currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			insertOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public void insertOneUserLanguage(Connection conn, int userId, String language, Integer score)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(INSERT_LANGUAGE)) {
			Integer langId = languageSet.getLanguageId(language.toLowerCase());

			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.setInt(3, score);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert language " + language + " for user " + userId);
			throw new MySqlException("Can't insert language " + language, ex);
		}
	}

	public void updateUserLanguages(Connection conn, Map<String, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<String, Integer> entry : userLanguages.entrySet()) {
			String currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			updateOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public void updateOneUserLanguage(Connection conn, int userId, String language, Integer score)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(UPDATE_USER_LANGUAGE)) {
			Integer langId = languageSet.getLanguageId(language.toLowerCase());
			ps.setInt(1, score);
			ps.setInt(2, userId);
			ps.setInt(3, langId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't update language " + language + " for user " + userId);
			throw new MySqlException("Can't update user language " + language, ex);
		}
	}

	public void deleteUserLanguages(Connection conn, Map<String, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<String, Integer> entry : userLanguages.entrySet()) {
			String currentLanguage = entry.getKey();
			deleteOneUserLanguage(conn, userId, currentLanguage);
		}
	}

	public void deleteOneUserLanguage(Connection conn, int userId, String language) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(DELETE_USER_LANGUAGE)) {
			Integer langId = languageSet.getLanguageId(language.toLowerCase());
			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.execute();
		} catch (SQLException ex) {
			logger.error("Can't delete language " + language + " for user " + userId);
			throw new MySqlException("Can't delete language " + language, ex);
		}
	}

	public boolean updateUserLanguages(Connection conn, User currentUser, User modifiedUser) throws MySqlException {

		Map<String, Integer> updatedLanguages = selectLanguagesForUpdating(currentUser, modifiedUser);
		Map<String, Integer> insertedLanguages = selectLanguagesForInserting(currentUser, modifiedUser);
		Map<String, Integer> deletedLanguages = selectLanguagesForRemoving(currentUser, modifiedUser);

		int userId = currentUser.getId();
		insertUserLanguages(conn, insertedLanguages, userId);
		updateUserLanguages(conn, updatedLanguages, userId);
		deleteUserLanguages(conn, deletedLanguages, userId);
		return true;
	}

	private Map<String, Integer> selectLanguagesForInserting(User currentUser, User modifiedUser) {

		Map<String, Integer> languagesForInserting = new HashMap<String, Integer>();

		Map<String, Integer> userLanguages = currentUser.getLanguages();
		Map<String, Integer> newUserLanguages = modifiedUser.getLanguages();

		for (Map.Entry<String, Integer> language : newUserLanguages.entrySet()) {
			String key = language.getKey();
			if (!userLanguages.containsKey(key)) {
				languagesForInserting.put(key, language.getValue());
			}
		}
		return languagesForInserting;
	}

	private Map<String, Integer> selectLanguagesForUpdating(User currentUser, User modifiedUser) {

		Map<String, Integer> languagesForUpdating = new HashMap<String, Integer>();

		Map<String, Integer> userLanguages = currentUser.getLanguages();
		Map<String, Integer> newUserLanguages = modifiedUser.getLanguages();

		for (Map.Entry<String, Integer> language : newUserLanguages.entrySet()) {
			String key = language.getKey();
			if (userLanguages.containsKey(key)) {
				if (!userLanguages.get(key).equals(language.getValue())) {
					languagesForUpdating.put(key, language.getValue());
				}
			}
		}
		return languagesForUpdating;
	}

	private Map<String, Integer> selectLanguagesForRemoving(User currentUser, User modifiedUser) {

		Map<String, Integer> languagesForRemoving = new HashMap<String, Integer>();

		Map<String, Integer> userLanguages = currentUser.getLanguages();
		Map<String, Integer> newUserLanguages = modifiedUser.getLanguages();

		for (Map.Entry<String, Integer> language : userLanguages.entrySet()) {
			String key = language.getKey();
			if (!newUserLanguages.containsKey(key)) {
				languagesForRemoving.put(key, language.getValue());
			}
		}
		return languagesForRemoving;
	}

}
