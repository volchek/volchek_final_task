package by.tr.web.dao.answer.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.answer.AnswerDao;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.database.util.pool.ConnectionPool;
import by.tr.web.dao.database.util.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.mysql.submitter.AnswerQuerySubmitter;
import by.tr.web.dao.mysql.submitter.QuestionQuerySubmitter;
import by.tr.web.entity.Answer;
import by.tr.web.entity.Question;
import by.tr.web.entity.Text;

public class MySQLAnswerDaoImpl implements AnswerDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private static final Logger logger = LogManager.getLogger(MySQLAnswerDaoImpl.class.getName());

	@Override
	public Text addAnswer(int questionId, String text, int userId) throws DaoException {

		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			AnswerQuerySubmitter answerQuery = new AnswerQuerySubmitter();
			answerQuery.insertAnswer(conn, questionId, userId, text);
			
			QuestionQuerySubmitter questionQuery = new QuestionQuerySubmitter();
			Question question = questionQuery.selectQuestionById(conn, questionId);
			List<Answer> answerList = answerQuery.selectAnswersToTheQuestion(conn, questionId);
			question.setAnswers(answerList);
			
			return question;
		} catch (MySqlException ex) {
			logger.error("Can't add a new answer to the question with id = " + questionId);
			throw new DaoException("Failed to add a new answer and insert it into the database", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

}
