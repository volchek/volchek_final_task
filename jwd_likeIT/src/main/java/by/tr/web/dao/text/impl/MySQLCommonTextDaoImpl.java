package by.tr.web.dao.text.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.mysql.submitter.AnswerQuerySubmitter;
import by.tr.web.dao.database.mysql.submitter.MarkQuerySubmitter;
import by.tr.web.dao.database.mysql.submitter.QuestionQuerySubmitter;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.database.util.pool.ConnectionPool;
import by.tr.web.dao.database.util.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.question.QuestionDao;
import by.tr.web.dao.question.impl.MySQLQuestionDaoImpl;
import by.tr.web.dao.text.CommonTextDao;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;

public class MySQLCommonTextDaoImpl implements CommonTextDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLCommonTextDaoImpl.class.getName());

	@Override
	public Question evaluateText(int userId, int textId, int mark, TextType textType) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();

			MarkQuerySubmitter markSubmitter = new MarkQuerySubmitter();
			markSubmitter.addedMark(conn, textId, userId, mark, textType);
			connPool.closeConnection(conn);

			return getQuestion(conn, textId, textType);

		} catch (MySqlException ex) {
			logger.error("Can't execure query and insert a new mark to the " + textType.name().toLowerCase()
					+ " with id = " + textId + " added by user with id = " + userId);
			throw new DaoException("Failed to add a new mark", ex);
		} finally {
			if (!connPool.isConnectionClose(conn)) {
				connPool.closeConnection(conn);
			}
		}
	}

	@Override
	public Question deleteTextMark(int userId, int textId, TextType textType) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();

			MarkQuerySubmitter markSubmitter = new MarkQuerySubmitter();
			markSubmitter.deleteMark(conn, textId, userId, textType);
			connPool.closeConnection(conn);
			return getQuestion(conn, textId, textType);

		} catch (MySqlException ex) {
			logger.error("Can't execure query and delete a mark to the " + textType.name().toLowerCase() + " with id = "
					+ textId + " added by user with id = " + userId);
			throw new DaoException("Failed to delete a mark", ex);
		} finally {
			if (!connPool.isConnectionClose(conn)) {
				connPool.closeConnection(conn);
			}
		}
	}

	@Override
	public List<Question> showUserTexts(int userId, TextType textType) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();

			QuestionQuerySubmitter questionSubmitter = new QuestionQuerySubmitter();
			List<Question> questions = null;
			if (textType.equals(TextType.QUESTION)) {
				questions = questionSubmitter.selectUserQuestion(conn, userId);
			} else if (textType.equals(TextType.ANSWER)) {
				questions = questionSubmitter.selectQuestionsWithUserAnswer(conn, userId);
				AnswerQuerySubmitter answerSubmitter = new AnswerQuerySubmitter();
				Map<Integer, List<Answer>> answers = answerSubmitter.selectUserAnswers(conn, userId);
				joinQuestionsAndAnswers(questions, answers);
			}
			return questions;

		} catch (MySqlException ex) {
			logger.error("Can't execure query and show all " + textType.name().toLowerCase()
					+ "s of the user with id = " + userId);
			throw new DaoException("Failed to select user texts", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	private Question getQuestion(Connection conn, int textId, TextType textType) throws DaoException, MySqlException {

		QuestionDao questionDao = new MySQLQuestionDaoImpl();

		Question question = null;
		if (textType.equals(TextType.QUESTION)) {
			question = questionDao.findQuestionById(textId);
		} else if (textType.equals(TextType.ANSWER)) {
			AnswerQuerySubmitter answerSubmitter = new AnswerQuerySubmitter();
			int questionId = answerSubmitter.findQuestionIdForTheAnswer(conn, textId);
			question = questionDao.findQuestionById(questionId);
		}
		return question;
	}

	private void joinQuestionsAndAnswers(List<Question> questions, Map<Integer, List<Answer>> answers) {

		for (Question question : questions) {
			Integer questionId = question.getId();
			question.setAnswers(answers.get(questionId));
		}
	}

}
