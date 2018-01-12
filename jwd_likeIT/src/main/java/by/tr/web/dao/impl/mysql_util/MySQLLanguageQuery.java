package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private final static Logger logger = LogManager.getLogger(MySQLLanguageQuery.class.getName());

	private Map<String, Integer> languages = new ConcurrentHashMap<String, Integer>();

	public void getAllLanguages(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL_LANGUAGES)) {
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				Integer langID = rs.getInt(DatabaseField.LANGUAGE_ID);
				languages.put(langName, langID);
			}
		} catch (SQLException ex) {
			logger.error("Can't get full language list");
			throw new MySqlException("Can't get full language list", ex);
		}
	}

	public void getUserLanguages(Connection conn, User user) throws MySqlException {

		Map<Language, Integer> userLanguages = getUserLanguages(conn, user.getId());
		user.setLanguages(userLanguages);
	}

	public Map<Language, Integer> getUserLanguages(Connection conn, int userId) throws MySqlException {

		LanguageCommand languageCommand = LanguageCommand.getInstance();

		try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_LANGUAGES_FOR_USER)) {
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
			logger.error("Can't get language list for user " + userId);
			throw new MySqlException("Can't get language list for user " + userId, ex);
		}
	}

	public void insertUserLanguages(Connection conn, Map<Language, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<Language, Integer> entry : userLanguages.entrySet()) {
			Language currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			insertOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public void insertOneUserLanguage(Connection conn, int userId, Language language, Integer score)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(INSERT_LANGUAGE)) {
			Integer langId = languages.get(language.getName());
			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.setInt(3, score);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert language " + language.getName() + " for user " + userId);
			throw new MySqlException("Can't insert language " + language.getName(), ex);
		}
	}

	public void updateUserLanguages(Connection conn, Map<Language, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<Language, Integer> entry : userLanguages.entrySet()) {
			Language currentLanguage = entry.getKey();
			Integer currentScore = entry.getValue();
			updateOneUserLanguage(conn, userId, currentLanguage, currentScore);
		}
	}

	public void updateOneUserLanguage(Connection conn, int userId, Language language, Integer score)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(UPDATE_USER_LANGUAGE)) {
			Integer langId = languages.get(language.getName());
			ps.setInt(1, score);
			ps.setInt(2, userId);
			ps.setInt(3, langId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't update language " + language.getName() + " for user " + userId);
			throw new MySqlException("Can't update user language " + language.getName(), ex);
		}
	}

	public void deleteUserLanguages(Connection conn, Map<Language, Integer> userLanguages, int userId)
			throws MySqlException {

		for (Map.Entry<Language, Integer> entry : userLanguages.entrySet()) {
			Language currentLanguage = entry.getKey();
			deleteOneUserLanguage(conn, userId, currentLanguage);
		}
	}

	public void deleteOneUserLanguage(Connection conn, int userId, Language language) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(DELETE_USER_LANGUAGE)) {
			Integer langId = languages.get(language.getName());
			ps.setInt(1, userId);
			ps.setInt(2, langId);
			ps.execute();
		} catch (SQLException ex) {
			logger.error("Can't delete language " + language.getName() + " for user " + userId);
			throw new MySqlException("Can't delete language " + language.getName(), ex);
		}
	}

	public boolean updateUserLanguages(Connection conn, User currentUser, User modifiedUser) throws MySqlException {

		Map<Language, Integer> updatedLanguages = selectLanguagesForUpdating(currentUser, modifiedUser);
		Map<Language, Integer> insertedLanguages = selectLanguagesForInserting(currentUser, modifiedUser);
		Map<Language, Integer> deletedLanguages = selectLanguagesForRemoving(currentUser, modifiedUser);

		updateLanguageList(conn);
		int userId = currentUser.getId();
		insertUserLanguages(conn, insertedLanguages, userId);
		updateUserLanguages(conn, updatedLanguages, userId);
		deleteUserLanguages(conn, deletedLanguages, userId);
		return true;
	}

	public Map<String, Integer> getLanguages() {
		return languages;
	}

	private void updateLanguageList(Connection conn) throws MySqlException {
		getLanguages().clear();
		getAllLanguages(conn);
	}

	private Map<Language, Integer> selectLanguagesForInserting(User currentUser, User modifiedUser) {

		Map<Language, Integer> languagesForInserting = new HashMap<Language, Integer>();

		Map<Language, Integer> userLanguages = currentUser.getLanguages();
		Map<Language, Integer> newUserLanguages = modifiedUser.getLanguages();

		for (Map.Entry<Language, Integer> language : newUserLanguages.entrySet()) {
			Language key = language.getKey();
			if (!userLanguages.containsKey(key)) {
				languagesForInserting.put(key, language.getValue());
			}
		}
		return languagesForInserting;
	}

	private Map<Language, Integer> selectLanguagesForUpdating(User currentUser, User modifiedUser) {

		Map<Language, Integer> languagesForUpdating = new HashMap<Language, Integer>();

		Map<Language, Integer> userLanguages = currentUser.getLanguages();
		Map<Language, Integer> newUserLanguages = modifiedUser.getLanguages();

		for (Map.Entry<Language, Integer> language : newUserLanguages.entrySet()) {
			Language key = language.getKey();
			if (userLanguages.containsKey(key)) {
				if (!userLanguages.get(key).equals(language.getValue())) {
					languagesForUpdating.put(key, language.getValue());
				}
			}
		}
		return languagesForUpdating;
	}

	private Map<Language, Integer> selectLanguagesForRemoving(User currentUser, User modifiedUser) {

		Map<Language, Integer> languagesForRemoving = new HashMap<Language, Integer>();

		Map<Language, Integer> userLanguages = currentUser.getLanguages();
		Map<Language, Integer> newUserLanguages = modifiedUser.getLanguages();

		for (Map.Entry<Language, Integer> language : userLanguages.entrySet()) {
			Language key = language.getKey();
			if (!newUserLanguages.containsKey(key)) {
				languagesForRemoving.put(key, language.getValue());
			}
		}
		return languagesForRemoving;
	}

}
