package by.tr.web.dao.impl.mysql.submitter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.query.QuestionQuery;
import by.tr.web.dao.impl.mysql.util.DatabaseField;
import by.tr.web.dao.impl.util.KeywordType;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;

public final class QuestionQuerySubmitter {

	private final static String PATTERN_TO_SPLIT_TAGS = "\\s*,\\s*";
	private final static int SEARCH_CRITERIA_COUNT = 3;
	private final static int FEED_LIMIT = 100;

	private final static Logger logger = LogManager.getLogger(QuestionQuerySubmitter.class.getName());

	public static Question addQuestion(Connection conn, int authorId, String title, String text, List<String> languages,
			List<String> tags) throws MySqlException {

		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);
			int userId = authorId;
			ps = prepareQuestionInsertionStatement(conn, ps, userId, title, text);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int questionId = rs.getInt(1);
				insertAllQuestionLanguages(conn, questionId, languages);
				insertAllQuestionTags(conn, questionId, tags);
				conn.commit();
				Question question = selectQuestionById(conn, questionId);
				return question;
			} else {
				conn.rollback();
			}
			return null;
		} catch (SQLException ex) {
			logger.error("Can't insert question of user with id = " + authorId);
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

	public static void updateQuestion(Connection conn, int questionId, int userId, String text) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.UPDATE_QUESTION_TEXT)) {

			ps.setString(1, text);
			ps.setInt(2, questionId);
			ps.setInt(3, userId);

			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't update the question with id=" + questionId);
			throw new MySqlException("Failed to execute command and update a question", ex);
		}
	}

	public static void deleteQuestion(Connection conn, int questionId, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.DELETE_QUESTION)) {
			ps.setInt(1, questionId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't delete the question with id=" + questionId + "written by user with id=" + userId);
			throw new MySqlException("Failed to execute command and delete a question", ex);
		}
	}

	public static Question selectQuestionById(Connection conn, int questionId) throws MySqlException {

		Question question = null;
		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTION_BY_ID)) {
			ps.setInt(1, questionId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				question = new Question();
				setMainQuestionFields(rs, question);
			}
			return question;
		} catch (SQLException ex) {
			logger.error("Can't create a question object for the question with id=" + questionId);
			throw new MySqlException("Failed to execute command and select a question", ex);
		}
	}

	public static List<Question> selectQuestionFeed(Connection conn) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_LAST_QUESTIONS)) {
			ps.setInt(1, FEED_LIMIT);
			ResultSet rs = ps.executeQuery();

			return createQuestionList(rs);
		} catch (SQLException ex) {
			logger.error("Can't select questions for the news feed");
			throw new MySqlException("Failed to execute command and select a question list", ex);
		}
	}

	public static List<Question> selectQuestionFeed(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_LAST_QUESTIONS_FOR_USER)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			return createQuestionList(rs);
		} catch (SQLException ex) {
			logger.error("Can't select questions for the user with id=" + userId);
			throw new MySqlException("Failed to execute command and select a question list", ex);
		}
	}

	public static List<Question> selectQuestionByLanguage(Connection conn, List<String> languages)
			throws MySqlException {
		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_BY_LANGUAGE)) {

			int langId = getLanguageIdList(languages).get(0);
			ps.setInt(1, langId);
			ResultSet rs = ps.executeQuery();

			return createQuestionList(rs);
		} catch (SQLException ex) {
			logger.error("Can't execute a query and extract information language " + languages.get(0));
			throw new MySqlException("Failed to execute command and select question list", ex);
		}
	}

	public static List<Question> selectQuestionByLanguageOrTag(Connection conn, List<String> keywords,
			KeywordType keywordType) throws MySqlException {

		PreparedStatement ps = null;
		List<Integer> keywordIdList = null;
		try {
			if (keywordType.equals(KeywordType.LANGUAGE)) {
				ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_BY_LANGUAGES);
				keywordIdList = getLanguageIdList(keywords);
			} else if (keywordType.equals(KeywordType.TAG)) {
				ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_BY_TAGS);
				keywordIdList = getTagIdList(keywords);
			}

			ps.setInt(1, keywordIdList.get(0));
			ps.setInt(2, keywordIdList.get(1));
			ps.setInt(3, keywordIdList.get(2));

			ResultSet rs = ps.executeQuery();
			return createQuestionList(rs);
		} catch (SQLException ex) {
			logger.error("Can't execute a query and extract information about questions asked in some keywords");
			throw new MySqlException("Failed to execute command and select question list", ex);
		}
	}

	public static List<Question> selectUserQuestion(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_USER_QUESTIONS)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			return createQuestionList(rs);

		} catch (SQLException ex) {
			logger.error("Can't execute query and select questions of the user with id = " + userId);
			throw new MySqlException("Can't execute select query", ex);
		}
	}

	public static List<Question> selectQuestionsWithUserAnswer(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_WITH_USER_ANSWERS)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			List<Question> questions = new ArrayList<Question>();

			while (rs.next()) {
				Question question = new Question();
				setMainQuestionFields(rs, question);

				Answer answer = new Answer();
				answer.setId(rs.getInt(9));

				question.addAnswer(answer);
				questions.add(question);
			}
			return questions;

		} catch (SQLException ex) {
			logger.error(
					"Can't execute query and select questions that contains an answer of the user with id = " + userId);
			throw new MySqlException("Can't execute select query", ex);
		}
	}

	private static void insertAllQuestionLanguages(Connection conn, int questionId, List<String> languages)
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

	private static void insertOneQuestionLanguage(Connection conn, int questionId, int languageId)
			throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(QuestionQuery.INSERT_QUESTION_LANGUAGES);
			ps.setInt(1, questionId);
			ps.setInt(2, languageId);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert language with id = " + languageId + " for question with id = " + questionId);
			throw new MySqlException("Can't insert question languages", ex);
		}
	}

	private static void insertAllQuestionTags(Connection conn, int questionId, List<String> tags)
			throws MySqlException {

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

	private static void insertOneQuestionTag(Connection conn, int questionId, int tagId) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(QuestionQuery.INSERT_QUESTION_TAGS);
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

		ps = conn.prepareStatement(QuestionQuery.INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS);

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

	private static List<Question> createQuestionList(ResultSet rs) throws MySqlException, SQLException {

		List<Question> questionList = new ArrayList<Question>();

		while (rs.next()) {
			Question question = new Question();
			setMainQuestionFields(rs, question);
			questionList.add(question);
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can not close ResultSet");
		}
		return questionList;
	}

	private static void setMainQuestionFields(ResultSet rs, Question question) throws SQLException, MySqlException {

		if (rs.getObject(1) == null) {
			throw new MySqlException("The query result is empty");
		}

		question.setId(rs.getInt(DatabaseField.QUESTION_ID));
		question.setTitle(rs.getString(DatabaseField.QUESTION_TITLE));
		question.setText(rs.getString(DatabaseField.QUESTION_TEXT));
		question.setCreationDate(rs.getDate(DatabaseField.QUESTION_DATETIME));

		List<String> languages = getDataList(rs, 5);
		question.setLanguages(languages);

		List<String> tags = getDataList(rs, 6);
		question.setTags(tags);

		question.setAuthorLogin(rs.getString(7));
		BigDecimal mark = rs.getBigDecimal(8);
		question.setAverageMark(mark == null ? null : mark.doubleValue());
	}

	private static List<String> getDataList(ResultSet rs, int column) throws SQLException {
		String strLangs = rs.getString(column);
		List<String> langs = Arrays.asList(strLangs.split(PATTERN_TO_SPLIT_TAGS));
		return langs;
	}

	private static List<Integer> getLanguageIdList(List<String> languages) {

		LanguageSet standardLanguages = LanguageSetSingleton.getInstance().getLanguageSet();

		List<Integer> langIdList = new ArrayList<Integer>();
		for (String lang : languages) {
			if (lang != null && !lang.isEmpty()) {
				int langId = standardLanguages.getLanguageId(lang);
				langIdList.add(langId);
			}
		}
		completeTagOrLanguageArray(langIdList);
		return langIdList;
	}

	private static List<Integer> getTagIdList(List<String> tags) {

		TagSet standardTags = TagSetSingleton.getInstance().getTagSet();

		List<Integer> tagIdList = new ArrayList<Integer>();
		for (String tag : tags) {
			if (tag != null && !tag.isEmpty()) {
				int tagId = standardTags.getTagId(tag);
				tagIdList.add(tagId);
			}
		}
		completeTagOrLanguageArray(tagIdList);
		return tagIdList;
	}

	private static void completeTagOrLanguageArray(List<Integer> elements) {
		while (elements.size() < SEARCH_CRITERIA_COUNT) {
			elements.add(elements.get(0));
		}
	}

	private QuestionQuerySubmitter() {

	}

}
