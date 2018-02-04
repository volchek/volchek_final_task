package by.tr.web.dao.impl.mysql.submitter;

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

import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.query.LanguageQuery;
import by.tr.web.dao.impl.mysql.util.DatabaseField;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.user.User;

public final class LanguageQuerySubmitter {

	private final static Logger logger = LogManager.getLogger(LanguageQuerySubmitter.class);

	private static LanguageSet languageSet = LanguageSetSingleton.getInstance().getLanguageSet();

	public static Map<String, Integer> getAllLanguageInfo(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(LanguageQuery.SELECT_ALL_LANGUAGES)) {

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

	public static List<String> getLanguageNames(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(LanguageQuery.SELECT_LANGUAGE_NAMES)) {
			return extractLanguageList(rs);
		} catch (SQLException ex) {
			logger.error("Can't get a list of language names");
			throw new MySqlException("Can't get a list of language names");
		}
	}

	public static List<String> findLanguageFrequency(Connection conn) throws MySqlException {
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery((LanguageQuery.SELECT_LANGUAGE_FREQUENCY))) {
			return extractLanguageList(rs);
		} catch (SQLException ex) {
			logger.error("Can't retrieve language frequency");
			throw new MySqlException("Can't retrieve language frequency");
		}
	}

	public static void getUserLanguages(Connection conn, User user) throws MySqlException {

		Map<String, Integer> userLanguages = getUserLanguages(conn, user.getId());
		user.setLanguages(userLanguages);
	}

	public static Map<String, Integer> getUserLanguages(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(LanguageQuery.SELECT_ALL_LANGUAGES_FOR_USER)) {
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

	public static void insertUserLanguages(Connection conn, Map<String, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<String, Integer> entry : userLanguages.entrySet()) {
			String currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			insertOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public static void insertOneUserLanguage(Connection conn, int userId, String language, Integer score)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(LanguageQuery.INSERT_USER_LANGUAGE)) {
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

	public static void updateUserLanguages(Connection conn, Map<String, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<String, Integer> entry : userLanguages.entrySet()) {
			String currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			updateOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public static void updateOneUserLanguage(Connection conn, int userId, String language, Integer score)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(LanguageQuery.UPDATE_USER_LANGUAGE)) {
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

	public static void deleteUserLanguages(Connection conn, Map<String, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<String, Integer> entry : userLanguages.entrySet()) {
			String currentLanguage = entry.getKey();
			deleteOneUserLanguage(conn, userId, currentLanguage);
		}
	}

	public static void deleteOneUserLanguage(Connection conn, int userId, String language) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(LanguageQuery.DELETE_USER_LANGUAGE)) {
			Integer langId = languageSet.getLanguageId(language.toLowerCase());
			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.execute();
		} catch (SQLException ex) {
			logger.error("Can't delete language " + language + " for user " + userId);
			throw new MySqlException("Can't delete language " + language, ex);
		}
	}

	public static boolean updateUserLanguages(Connection conn, User currentUser, User modifiedUser)
			throws MySqlException {

		Map<String, Integer> updatedLanguages = selectLanguagesForUpdating(currentUser, modifiedUser);
		Map<String, Integer> insertedLanguages = selectLanguagesForInserting(currentUser, modifiedUser);
		Map<String, Integer> deletedLanguages = selectLanguagesForRemoving(currentUser, modifiedUser);

		int userId = currentUser.getId();
		insertUserLanguages(conn, insertedLanguages, userId);
		updateUserLanguages(conn, updatedLanguages, userId);
		deleteUserLanguages(conn, deletedLanguages, userId);
		return true;
	}

	public static void insertLanguage(Connection conn, String language) throws MySqlException {
		try (PreparedStatement ps = conn.prepareStatement(LanguageQuery.INSERT_LANGUAGE)) {
			ps.setString(1, language);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert the language '" + language + "'");
			throw new MySqlException("Can't insert a new language", ex);
		}
	}

	private static Map<String, Integer> selectLanguagesForInserting(User currentUser, User modifiedUser) {

		Map<String, Integer> userLanguages = currentUser.getLanguages();
		Map<String, Integer> newUserLanguages = modifiedUser.getLanguages();

		Map<String, Integer> languagesForInserting = new HashMap<String, Integer>();

		for (Map.Entry<String, Integer> language : newUserLanguages.entrySet()) {
			String key = language.getKey();
			if (!userLanguages.containsKey(key)) {
				languagesForInserting.put(key, language.getValue());
			}
		}
		return languagesForInserting;
	}

	private static Map<String, Integer> selectLanguagesForUpdating(User currentUser, User modifiedUser) {

		Map<String, Integer> userLanguages = currentUser.getLanguages();
		Map<String, Integer> newUserLanguages = modifiedUser.getLanguages();

		Map<String, Integer> languagesForUpdating = new HashMap<String, Integer>();

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

	private static Map<String, Integer> selectLanguagesForRemoving(User currentUser, User modifiedUser) {

		Map<String, Integer> userLanguages = currentUser.getLanguages();
		Map<String, Integer> newUserLanguages = modifiedUser.getLanguages();

		Map<String, Integer> languagesForRemoving = new HashMap<String, Integer>();

		for (Map.Entry<String, Integer> language : userLanguages.entrySet()) {
			String key = language.getKey();
			if (!newUserLanguages.containsKey(key)) {
				languagesForRemoving.put(key, language.getValue());
			}
		}
		return languagesForRemoving;
	}

	private static List<String> extractLanguageList(ResultSet rs) throws SQLException {
		List<String> languages = new ArrayList<String>();
		while (rs.next()) {
			String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
			languages.add(langName);
		}
		return languages;
	}

	private LanguageQuerySubmitter() {

	}

}
