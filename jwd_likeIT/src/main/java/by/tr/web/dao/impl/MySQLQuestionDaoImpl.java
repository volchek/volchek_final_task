package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.QuestionDao;
import by.tr.web.dao.database.pool.ConnectionPool;
import by.tr.web.dao.database.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.submitter.AnswerQuerySubmitter;
import by.tr.web.dao.impl.mysql.submitter.QuestionQuerySubmitter;
import by.tr.web.dao.impl.util.KeywordType;
import by.tr.web.dao.impl.util.TextCreator;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;

public class MySQLQuestionDaoImpl implements QuestionDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLQuestionDaoImpl.class.getName());

	@Override
	public Question addQuestion(int authorId, String title, List<String> languages, List<String> tags, String text)
			throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			Question question = QuestionQuerySubmitter.addQuestion(conn, authorId, title, text, languages, tags);
			List<Answer> answers = AnswerQuerySubmitter.selectAnswersToTheQuestion(conn, question.getId());
			question.setAnswers(answers);
			return question;

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get a language list", ex);
			throw new DaoException("Failed to add a new question into the database", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public Question editQuestion(int questionId, int userId, String oldText, String newText) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();

			QuestionQuerySubmitter.updateQuestion(conn, questionId, userId,
					TextCreator.createUpdatedText(oldText, newText));
			Question question = QuestionQuerySubmitter.selectQuestionById(conn, questionId);
			return question;

		} catch (MySqlException ex) {
			logger.error("Can't execure query and update the question with id = " + questionId);
			throw new DaoException("Failed to update the question", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public boolean deleteQuestion(int questionId, int userId) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();

			QuestionQuerySubmitter.deleteQuestion(conn, questionId, userId);
			return true;
		} catch (MySqlException ex) {
			logger.error("Can't execure query and delete the question with id = " + questionId);
			throw new DaoException("Failed to delete the question", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public Question findQuestionById(int questionId) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			Question question = QuestionQuerySubmitter.selectQuestionById(conn, questionId);

			if (question == null) {
				throw new DaoException("Question with id=" + questionId + "doesn't exist");
			}

			List<Answer> answers = AnswerQuerySubmitter.selectAnswersToTheQuestion(conn, questionId);
			question.setAnswers(answers);
			return question;

		} catch (MySqlException ex) {
			logger.error("Can't execure query and select a question with id = " + questionId);
			throw new DaoException("Failed to select a question", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public List<Question> findQuestionByLanguage(List<String> languages) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			List<Question> questionList = null;
			if (languages.size() > 1) {
				questionList = QuestionQuerySubmitter.selectQuestionByLanguageOrTag(conn, languages,
						KeywordType.LANGUAGE);
			} else {
				questionList = QuestionQuerySubmitter.selectQuestionByLanguage(conn, languages);
			}
			getAnswerCount(conn, questionList);
			return questionList;

		} catch (MySqlException ex) {
			logger.error("Can't execure query and select questions asked in language list: " + languages.toString());
			throw new DaoException("Failed to select a question list", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public List<Question> findQuestionByTag(List<String> tags) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			List<Question> questionList = QuestionQuerySubmitter.selectQuestionByLanguageOrTag(conn, tags,
					KeywordType.TAG);
			getAnswerCount(conn, questionList);
			return questionList;
		} catch (MySqlException ex) {
			logger.error("Can't execure query and select questions asked in tag list: " + tags.toString());
			throw new DaoException("Failed to select a question list", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public List<Question> showLastQuestions() throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			List<Question> questionList = QuestionQuerySubmitter.selectQuestionFeed(conn);
			getAnswerCount(conn, questionList);
			return questionList;
		} catch (MySqlException ex) {
			logger.error("Can't execure query and select last questions");
			throw new DaoException("Failed to select a question list", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public List<Question> showLastQuestionsForRegisteredUser(int userId) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			List<Question> questionList = QuestionQuerySubmitter.selectQuestionFeed(conn, userId);

			if (questionList == null || questionList.isEmpty()) {
				questionList = QuestionQuerySubmitter.selectQuestionFeed(conn);
			}

			for (Question question : questionList) {
				int questionId = question.getId();
				int countAnswers = AnswerQuerySubmitter.findCountAnswers(conn, questionId);
				question.setCountAnswers(countAnswers);
			}
			return questionList;
		} catch (MySqlException ex) {
			logger.error("Can't execure query and select last questions for the registered user with id = " + userId);
			throw new DaoException("Failed to select a question list", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	private void getAnswerCount(Connection conn, List<Question> questionList) throws MySqlException {

		for (Question question : questionList) {
			int questionId = question.getId();
			int countAnswers = AnswerQuerySubmitter.findCountAnswers(conn, questionId);
			question.setCountAnswers(countAnswers);
		}
	}

}
