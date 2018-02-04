package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.AnswerDao;
import by.tr.web.dao.database.pool.ConnectionPool;
import by.tr.web.dao.database.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.submitter.AnswerQuerySubmitter;
import by.tr.web.dao.impl.mysql.submitter.QuestionQuerySubmitter;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.Text;

public class MySQLAnswerDaoImpl implements AnswerDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private static final Logger logger = LogManager.getLogger(MySQLAnswerDaoImpl.class.getName());

	@Override
	public Text addAnswer(int questionId, String text, int userId) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			AnswerQuerySubmitter.insertAnswer(conn, questionId, userId, text);

			Question question = findQuestion(conn, questionId);
			return question;
		} catch (MySqlException ex) {
			logger.error("Can't add a new answer to the question with id = " + questionId);
			throw new DaoException("Failed to add a new answer and insert it into the database", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public Answer findAnswerById(int answerId) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			List<Answer> answers = AnswerQuerySubmitter.selectAnswerById(conn, answerId);
			if (answers != null) {
				return answers.get(0);
			}
			return null;
		} catch (MySqlException ex) {
			logger.error("Can't find an answer with id = " + answerId);
			throw new DaoException("Failed to select an answer", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public int findQuestionIdForTheAnswer(int answerId) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			return AnswerQuerySubmitter.findQuestionIdForTheAnswer(conn, answerId);
		} catch (MySqlException ex) {
			logger.error("Can't select a question id for the answer with id = " + answerId);
			throw new DaoException("Failed to select a question id for the answer", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public Question editAnswer(int answerId, int userId, String text) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			AnswerQuerySubmitter.updateAnswer(conn, answerId, userId, text);
			int questionId = AnswerQuerySubmitter.findQuestionIdForTheAnswer(conn, answerId);
			Question question = findQuestion(conn, questionId);
			return question;
		} catch (MySqlException ex) {
			logger.error("Can't edit the answer with id = " + answerId);
			throw new DaoException("Failed to update the answer", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public boolean deleteAnswer(int answerId, int userId) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			return AnswerQuerySubmitter.deleteAnswer(conn, answerId, userId);
		} catch (MySqlException ex) {
			logger.error("Can't delete the answer with id = " + answerId);
			throw new DaoException("Failed to delete the answer", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	private Question findQuestion(Connection conn, int questionId) throws MySqlException {

		Question question = QuestionQuerySubmitter.selectQuestionById(conn, questionId);

		List<Answer> answerList = AnswerQuerySubmitter.selectAnswersToTheQuestion(conn, questionId);
		question.setAnswers(answerList);

		return question;
	}
}
