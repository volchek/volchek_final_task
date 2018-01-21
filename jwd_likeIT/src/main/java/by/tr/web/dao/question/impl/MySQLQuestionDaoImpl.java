package by.tr.web.dao.question.impl;

import java.sql.Connection;
import java.util.List;

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
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;

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
			QuestionQuerySubmitter questionQuery = new QuestionQuerySubmitter();
			Question question = questionQuery.addQuestion(conn, authorId, title, text, languages, tags);

			AnswerQuerySubmitter answerQuery = new AnswerQuerySubmitter();
			List<Answer> answers = answerQuery.selectAnswersToTheQuestion(conn, question.getId());
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
	public Question findQuestionById(int questionId) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			Question question = selectQuestionById(conn, questionId);
			return question;

		} catch (MySqlException ex) {
			logger.error("Can't execure query and select a question with id = " + questionId);
			throw new DaoException("Failed to select a question", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	public Question selectQuestionById(Connection conn, int questionId) throws MySqlException {

		QuestionQuerySubmitter questionSubmitter = new QuestionQuerySubmitter();
		Question question = questionSubmitter.selectQuestionById(conn, questionId);

		MarkQuerySubmitter markSubmitter = new MarkQuerySubmitter();

		Double averageMark = markSubmitter.getAverageMark(conn, questionId, TextType.QUESTION);
		question.setAverageMark(averageMark);

		AnswerQuerySubmitter answerSubmitter = new AnswerQuerySubmitter();
		List<Answer> answers = answerSubmitter.selectAnswersToTheQuestion(conn, questionId);
		question.setAnswers(answers);

		return question;

	}

}
