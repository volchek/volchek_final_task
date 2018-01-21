package by.tr.web.dao.database.mysql.submitter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.mysql.query.AnswerQuery;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.entity.Answer;

public class AnswerQuerySubmitter {

	public static final Logger logger = LogManager.getLogger(AnswerQuerySubmitter.class.getName());

	public void insertAnswer(Connection conn, int questionId, int userId, String text) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(AnswerQuery.INSERT_ANSWER, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, questionId);
			ps.setString(2, text);
			ps.setInt(3, userId);
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.execute();

		} catch (SQLException ex) {
			logger.error("Can't create a prepared statement");
			throw new MySqlException("Can't create a prepared statement", ex);
		}
	}

	public List<Answer> selectAnswersToTheQuestion(Connection conn, int questionId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(AnswerQuery.SELECT_ANSWERS_TO_THE_QUESTION)) {
			ps.setInt(1, questionId);
			ResultSet rs = ps.executeQuery();
			List<Answer> answer = createAnswers(rs);
			return answer;
		} catch (SQLException ex) {
			logger.error("Can't select answers to the question with id = " + questionId);
			throw new MySqlException("Failed to execute a select query", ex);
		}
	}

	private List<Answer> createAnswers(ResultSet rs) throws MySqlException {
		List<Answer> answerList = new ArrayList<Answer>();
		try {
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setId(rs.getInt(1));
				answer.setQuestion(rs.getInt(2));
				answer.setText(rs.getString(3));
				answer.setCreationDate(rs.getDate(4));
				answer.setAuthorLogin(rs.getString(5));
				BigDecimal mark = rs.getBigDecimal(6);
				Double averageMark = (mark == null ? null : mark.doubleValue());
				answer.setAverageMark(averageMark);
				answerList.add(answer);
			}
			return answerList;
		} catch (SQLException ex) {
			logger.error("Can't create a list of answers");
			throw new MySqlException("Failed to execute query to select an answer list", ex);
		}
	}

}
