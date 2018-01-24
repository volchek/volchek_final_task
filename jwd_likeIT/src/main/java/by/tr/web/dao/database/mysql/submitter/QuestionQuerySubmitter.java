package by.tr.web.dao.database.mysql.submitter;

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

import by.tr.web.dao.database.mysql.query.QuestionQuery;
import by.tr.web.dao.database.mysql.query.util.KeywordType;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;

public class QuestionQuerySubmitter {

	private final static String PATTERN_TO_SPLIT_TAGS = "\\s*,\\s*";
	private final static int SEARCH_CRITERIA_COUNT = 3;

	private final static Logger logger = LogManager.getLogger(QuestionQuerySubmitter.class.getName());

	public Question addQuestion(Connection conn, int authorId, String title, String text, List<String> languages,
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

	public Question selectQuestionById(Connection conn, int questionId) throws MySqlException {

		Question question = null;
		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTION_BY_ID)) {
			ps.setInt(1, questionId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				question = new Question();
				setMainQuestionFields(rs, question);
				question.setAuthorLogin(rs.getString(7));
			}
			return question;
		} catch (SQLException ex) {
			logger.error("Can't create a question object for the question with id=" + questionId);
			throw new MySqlException("Failed to execute command and select a question", ex);
		}
	}

	public List<Question> selectQuestionByLanguageOrTag(Connection conn, List<String> keywords, KeywordType keywordType)
			throws MySqlException {

		PreparedStatement ps = null;
		List<Integer> keywordIdList = null;
		try {
			if (keywordType.equals(KeywordType.LANGUAGE)) {
				ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_BY_LANGUAGE);
				keywordIdList = getLanguageIdList(keywords);
			} else if (keywordType.equals(KeywordType.TAG)) {
				ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_BY_TAG);
				keywordIdList = getTagIdList(keywords);
			}

			ps.setInt(1, keywordIdList.get(0));
			ps.setInt(2, keywordIdList.get(1));
			ps.setInt(3, keywordIdList.get(2));

			ResultSet rs = ps.executeQuery();
			List<Question> questionList = new ArrayList<Question>();
			while (rs.next()) {
				Question question = new Question();
				setMainQuestionFields(rs, question);
				question.setAuthorLogin(rs.getString(7));
				question.setAverageMark(extractAverageMark(rs, 8));
				questionList.add(question);
			}
			return questionList;
		} catch (SQLException ex) {
			logger.error("Can't execute a query and extract information about questions asked in some keywords");
			throw new MySqlException("Failed to execute command and select question list", ex);
		}
	}

	public List<Question> selectUserQuestion(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_USER_QUESTIONS)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			List<Question> questions = new ArrayList<Question>();

			while (rs.next()) {
				Question question = new Question();
				setMainQuestionFields(rs, question);
				question.setAuthorLogin(rs.getString(7));
				BigDecimal mark = rs.getBigDecimal(8);
				question.setAverageMark(mark == null ? null : mark.doubleValue());
				questions.add(question);
			}
			return questions;

		} catch (SQLException ex) {
			logger.error("Can't execute query and select questions of the user with id = " + userId);
			throw new MySqlException("Can't execute select query", ex);
		}
	}

	public List<Question> selectQuestionsWithUserAnswer(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(QuestionQuery.SELECT_QUESTIONS_WITH_USER_ANSWERS)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			List<Question> questions = new ArrayList<Question>();

			while (rs.next()) {
				Question question = new Question();
				setMainQuestionFields(rs, question);
				question.setAuthorLogin(rs.getString(7));
				BigDecimal mark = rs.getBigDecimal(8);
				question.setAverageMark(mark == null ? null : mark.doubleValue());
				Answer answer = new Answer();
				answer.setId(rs.getInt(8));
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
			ps = conn.prepareStatement(QuestionQuery.INSERT_QUESTION_LANGUAGES);
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

	private void setMainQuestionFields(ResultSet rs, Question question) throws SQLException, MySqlException {

		if (rs.getObject(1) == null){
			throw new MySqlException("The query result is empty");
		}
		
		question.setId(rs.getInt(1));
		question.setTitle(rs.getString(2));
		question.setText(rs.getString(3));
		question.setCreationDate(rs.getDate(4));
				
		List<String> languages = getDataList(rs, 5);
		question.setLanguages(languages);

		List<String> tags = getDataList(rs, 6);
		question.setTags(tags);
	}

	private List<String> getDataList(ResultSet rs, int column) throws SQLException {
		String strLangs = rs.getString(column);
		List<String> langs = Arrays.asList(strLangs.split(PATTERN_TO_SPLIT_TAGS));
		return langs;
	}

	private Double extractAverageMark(ResultSet rs, int columnNumber) throws SQLException {
		BigDecimal mark = rs.getBigDecimal(columnNumber);
		Double averageMark = (mark == null ? null : mark.doubleValue());
		return averageMark;
	}

	private List<Integer> getLanguageIdList(List<String> languages) {

		LanguageSet standardLanguages = LanguageSetSingleton.getInstance().getLanguageSet();

		List<Integer> langIdList = new ArrayList<Integer>();
		for (String lang : languages) {
			int langId = standardLanguages.getLanguageId(lang);
			langIdList.add(langId);
		}
		completeTagOrLanguageArray(langIdList);
		return langIdList;
	}

	private List<Integer> getTagIdList(List<String> tags) {

		TagSet standardTags = TagSetSingleton.getInstance().getTagSet();

		List<Integer> tagIdList = new ArrayList<Integer>();
		for (String tag : tags) {
			int tagId = standardTags.getTagId(tag);
			tagIdList.add(tagId);
		}
		completeTagOrLanguageArray(tagIdList);
		return tagIdList;
	}

	private void completeTagOrLanguageArray(List<Integer> elements) {
		while (elements.size() < SEARCH_CRITERIA_COUNT) {
			elements.add(elements.get(0));
		}
	}

}
