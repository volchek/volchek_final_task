package by.tr.web.dao.database.mysql.submitter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.mysql.query.AnswerQuery;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.entity.text.Answer;

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
			throw new MySqlException("Can't execute select query", ex);
		}
	}

	public Map<Integer, List<Answer>> selectUserAnswers(Connection conn, int userId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(AnswerQuery.SELECT_USER_ANSWERS)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			Map<Integer, List<Answer>> answers = new HashMap<Integer, List<Answer>>();
			while (rs.next()) {
				Answer answer = new Answer();
				Integer questionId = rs.getInt(1);
				answer.setId(rs.getInt(2));
				answer.setText(rs.getString(3));
				answer.setCreationDate(rs.getDate(4));
				answer.setAverageMark(extractAverageMark(rs, 5));
				answer.setMarkCount(rs.getInt(6));

				if (!answers.containsKey(questionId)) {
					List<Answer> answerList = new ArrayList<Answer>();
					answerList.add(answer);
					answers.put(questionId, answerList);
				} else {
					answers.get(questionId).add(answer);
				}
			}
			return answers;
		} catch (SQLException ex) {
			logger.error("Can't select answers of the user with id = " + userId);
			throw new MySqlException("Can't execute select query", ex);
		}
	}

	public List<Answer> selectAnswersToTheQuestion(Connection conn, int questionId) throws MySqlException {

		return selectAnswersByTextId(conn, questionId, AnswerQuery.SELECT_ANSWERS_TO_THE_QUESTION);
	}

	public List<Answer> selectAnswerById(Connection conn, int textId) throws MySqlException {

		return selectAnswersByTextId(conn, textId, AnswerQuery.SELECT_ANSWER_BY_ID);
	}

	public int findQuestionIdForTheAnswer(Connection conn, int answerId) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(AnswerQuery.SELECT_QUESTION_ID_FOR_THE_ANSWER)) {

			ps.setInt(1, answerId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new MySqlException(
						"There is not an answer with id = " + answerId + " or there is an incorrect it's question id");
			}
		} catch (SQLException ex) {
			logger.error("Can't select questionId for the answer with id = " + answerId);
			throw new MySqlException("Failed to execute a select query", ex);
		}

	}

	private List<Answer> selectAnswersByTextId(Connection conn, int textId, String queryTemplate)
			throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(queryTemplate)) {
			ps.setInt(1, textId);
			ResultSet rs = ps.executeQuery();
			List<Answer> answer = createAnswers(rs);
			return answer;
		} catch (SQLException ex) {
			logger.error("Can't select answers to the text with id = " + textId);
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
				answer.setAverageMark(extractAverageMark(rs, 6));
				answerList.add(answer);
			}
			return answerList;
		} catch (SQLException ex) {
			logger.error("Can't create a list of answers");
			throw new MySqlException("Failed to execute query to select an answer list", ex);
		}
	}

	private Double extractAverageMark(ResultSet rs, int columnNumber) throws SQLException {
		BigDecimal mark = rs.getBigDecimal(columnNumber);
		Double averageMark = (mark == null ? null : mark.doubleValue());
		return averageMark;
	}

}
