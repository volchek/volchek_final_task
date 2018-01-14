package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.entity.Question;

public class MySQLQuestionQuery {

	private final static String INSERT_QUESTION_QUERY = "INSERT INTO Questions (title, text, userId, creationDatetime) "
			+ "VALUES (?, ?, ?, ?)";

	private final static Logger logger = LogManager.getLogger(MySQLQuestionQuery.class.getName());

	public Question addQuestion(Connection conn, int userId, String title, String text) throws MySqlException {

		PreparedStatement ps = null;

		try {
			ps = prepareQuestionInsertionStatement(conn, ps, userId, title, text);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int questionId = rs.getInt(1);
				System.out.println(questionId);
				// insert languages, insert tags
				// langQuery.insertUserLanguages(conn, user.getLanguages(),
				// userID);
				conn.commit();
				Question question = createQuestion(rs);
				return question;
			} else {
				conn.rollback();
			}
			return null;
		} catch (SQLException ex) {
			logger.error("Can't insert question of user with id = " + userId);
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error("Can't rollback transaction");
			}
			throw new MySqlException("Failed to execute command and insert a new question", ex);
		} finally {
			closeStatement(ps);
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

	private Question createQuestion(ResultSet rs) {
		return null;
	}
	
}
