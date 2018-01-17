package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.entity.Question;
import by.tr.web.entity.User;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;

public class MySQLQuestionQuery {

	private final static String INSERT_QUESTION_QUERY = "INSERT INTO Questions (title, text, userId, creationDatetime) "
			+ "VALUES (?, ?, ?, ?)";

	private final static String INSERT_QUESTION_LANGUAGES = "INSERT INTO Questions2Languages (questionId, languageId) "
			+ "VALUES (?, ?)";

	private final static String INSERT_QUESTION_TAGS = "INSERT INTO Questions2Keywords (questionId, keywordId) "
			+ "VALUES (?, ?)";

	private final static String SELECT_QUESTION_BY_ID = 
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, "
			+ "GROUP_CONCAT(DISTINCT	l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ',') "
			+ "FROM likeit2.questions AS q "
			+ "LEFT JOIN questions2keywords AS qk "
			+ "ON q.questionId = qk.questionId "
			+ "INNER JOIN likeit2.keywords AS k "
			+ "ON k.keywordId = qk.keywordId "
			+ "LEFT JOIN likeit2.questions2languages AS ql "
			+ "ON q.questionId = ql.questionId "
			+ "INNER JOIN likeit2.languages AS l "
			+ "ON ql.languageId = l.languageId "
			+ "WHERE q.questionId = ?;";

	private final static Logger logger = LogManager.getLogger(MySQLQuestionQuery.class.getName());

	public Question addQuestion(Connection conn, User author, String title, String text, List<String> languages,
			List<String> tags) throws MySqlException {

		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);
			int userId = author.getId();
			ps = prepareQuestionInsertionStatement(conn, ps, userId, title, text);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int questionId = rs.getInt(1);
				insertAllQuestionLanguages(conn, questionId, languages);
				insertAllQuestionTags(conn, questionId, tags);
				conn.commit();
				Question question = selectQuestion(conn, questionId);
				if (question != null){
					question.setAuthor(author);
				}
				return question;
			} else {
				conn.rollback();
			}
			return null;
		} catch (SQLException ex) {
			logger.error("Can't insert question of user with id = " + author.getId());
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error("Can't rollback transaction");
			}
			throw new MySqlException("Failed to execute command and insert a new question", ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("Can't set autocommit mode");
			}
			closeStatement(ps);
		}
	}

	private void insertAllQuestionLanguages(Connection conn, int questionId, List<String> languages)
			throws MySqlException {
		LanguageSet languageSet = LanguageSetSingleton.getInstance().getLanguageSet();
		Map<String, Integer> allLanguages = languageSet.getLanguageToIdSet();
		for (String language : languages) {
			if (language == null || language.isEmpty()) {
				continue;
			}
			int languageId = allLanguages.get(language.toLowerCase());
			insertOneQuestionLanguage(conn, questionId, languageId);
		}
	}

	private void insertOneQuestionLanguage(Connection conn, int questionId, int languageId) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_QUESTION_LANGUAGES);
			ps.setInt(1, questionId);
			ps.setInt(2, languageId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert language with id = " + languageId + " for question with id = " + questionId);
			throw new MySqlException("Can't insert question languages", ex);
		}
	}

	private void insertAllQuestionTags(Connection conn, int questionId, List<String> tags) throws MySqlException {
		TagSet tagSet = TagSetSingleton.getInstance().getTagSet();
		Map<String, Integer> allTags = tagSet.getTagToIdSet();
		for (String tag : tags) {
			if (tag == null || tag.isEmpty()) {
				continue;
			}
			int tagId = allTags.get(tag.toLowerCase());
			insertOneQuestionTag(conn, questionId, tagId);
		}
	}

	private void insertOneQuestionTag(Connection conn, int questionId, int tagId) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_QUESTION_TAGS);
			ps.setInt(1, questionId);
			ps.setInt(2, tagId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert tag with id = " + tagId + " for question with id = " + questionId);
			throw new MySqlException("Can't insert question tags", ex);
		}
	}

	private static PreparedStatement prepareQuestionInsertionStatement(Connection conn, PreparedStatement ps, int id,
			String title, String text) throws SQLException {

		ps = conn.prepareStatement(INSERT_QUESTION_QUERY, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, title);
		ps.setString(2, text);
		ps.setInt(3, id);
		ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

		return ps;
	}

	private static void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can not close Statement");
		}
	}

	private Question selectQuestion(Connection conn, int questionId) {
		
		Question question = null;
		try (PreparedStatement ps = conn.prepareStatement(SELECT_QUESTION_BY_ID)) {
			ps.setInt(1, questionId);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()){
					question = new Question();
					question.setId(rs.getInt(1));
					question.setTitle(rs.getString(2));
					question.setText(rs.getString(3));
					question.setCreationDate(rs.getDate(4));
					String strLangs = rs.getString(5);
					List<String> langs = Arrays.asList(strLangs.split("\\s*,\\s*"));
					question.setLanguages(langs);
					String strTags = rs.getString(6);
					List<String> tags = Arrays.asList(strTags.split("\\s*,\\s*"));
					question.setTags(tags);
				}
			} 
		} catch (SQLException e) {
			logger.error("Can't create a question object");
		}
		return question;
	}

}
