package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.entity.User;
import by.tr.web.entity.language.Language;
import by.tr.web.entity.language.LanguageCommand;

public class MySQLLanguageQuery {

	private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM Languages";
	private static final String SELECT_ALL_LANGUAGES_FOR_USER = "SELECT lang.languageId, " 
			+ "lang.language, us.level "
			+ "FROM likeit2.users2languages AS us " + "INNER JOIN likeit2.languages AS lang "
			+ "ON lang.languageId = us.languageId " + "WHERE us.userId = ?;";
	// private static final String FIND_LANGUAGE = "SELECT * FROM Languages
	// WHERE language = ?;";
	private static final String INSERT_LANGUAGE = "INSERT INTO users2languages " + "(userId, languageId, level) "
			+ "VALUES (?, ?, ?);";

	private Map<String, Integer> languages = new HashMap<String, Integer>();

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

		LanguageCommand languageCommand = LanguageCommand.getInstance();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(SELECT_ALL_LANGUAGES_FOR_USER);
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String langName = rs.getString(DatabaseField.LANGUAGE_NAME);
				Integer level = rs.getInt(DatabaseField.LANGUAGE_LEVEL);
				user.addLanguage(languageCommand.getLanguage(langName), level);
			}
		} catch (SQLException ex) {
			// add logging
			throw new MySqlException("Can't get language list for user " + user.getId(), ex);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

	public void insertUserLanguages(Connection conn, User user, int userId) throws MySqlException, SQLException {

		Map<Language, Integer> userLanguages = user.getLanguages();

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

	public Map<String, Integer> getLanguages() {
		return languages;
	}

}
