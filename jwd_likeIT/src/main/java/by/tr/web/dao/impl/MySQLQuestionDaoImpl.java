package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.QuestionDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.MySQLQuestionQuery;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;
import by.tr.web.entity.Question;

public class MySQLQuestionDaoImpl implements QuestionDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLQuestionDaoImpl.class.getName());

	@Override
	public Question addQuestion(int id, String title, List<String> languages, List<String> tags, String text)
			throws DaoException, FatalDaoException {
		
		Connection conn = null;

		try {
			conn = connPool.getConnection();
			MySQLQuestionQuery query = new MySQLQuestionQuery();
			Question question = query.addQuestion(conn, id, title, text);

			return question;

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get a language list", ex);
		} finally {
			connPool.closeConnection(conn);
		}
		return null;
	}
}
