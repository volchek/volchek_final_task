package by.tr.web.dao.question.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.database.util.pool.ConnectionPool;
import by.tr.web.dao.database.util.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.query.MySQLAnswerQuery;
import by.tr.web.dao.query.MySQLQuestionQuery;
import by.tr.web.dao.question.QuestionDao;
import by.tr.web.entity.Answer;
import by.tr.web.entity.Question;
import by.tr.web.entity.Text;

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
			MySQLQuestionQuery questionQuery = new MySQLQuestionQuery();
			Question question = questionQuery.addQuestion(conn, authorId, title, text, languages, tags);
			
			MySQLAnswerQuery answerQuery = new MySQLAnswerQuery();
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
	public List<Text> evaluateQuestion(int userId, int questionId, int mark) throws DaoException {
		
		// TODO Auto-generated method stub
		
		return null;
	}
}
