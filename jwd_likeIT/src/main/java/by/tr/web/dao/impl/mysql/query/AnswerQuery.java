package by.tr.web.dao.impl.mysql.query;

public final class AnswerQuery {

	public static final String INSERT_ANSWER = 
			"INSERT INTO Answers "
			+ " (questionId, answerText, userId, creationDatetime) " 
			+ "VALUES (?, ?, ?, ?);";
	
	public static final String UPDATE_ANSWER =
			"UPDATE answers "
			+ "SET answerText = ? "
			+ "WHERE answerId = ?; ";
	
	public static final String DELETE_ANSWER = 
			"DELETE FROM answers "
			+ "WHERE answerId = ?;";

	public static final String SELECT_ANSWERS_TO_THE_QUESTION = 
			"SELECT a.answerId, a.questionId, a.answerText, "
			+ "a.creationDatetime, u.login, AVG(m.mark) "
			+ "FROM Answers AS a "
			+ "INNER JOIN Users AS u "
			+ "ON a.userId = u.userId "
			+ "LEFT JOIN answermarks AS m "
			+ "ON a.answerId = m.answerId "
			+ "GROUP BY a.answerId "
			+ "HAVING a.questionId = ?;";
	
	public static final String SELECT_COUNT_ANSWERS_TO_THE_QUESTION = 
			"SELECT COUNT(answerId) "
			+ "FROM answers "
			+ "WHERE questionId = ?;";
	
	public static final String SELECT_ANSWER_BY_ID = 
			"SELECT a.answerId, a.questionId, a.answerText, "
			+ "a.creationDatetime, u.login, AVG(m.mark) "
			+ "FROM Answers AS a "
			+ "INNER JOIN Users AS u "
			+ "ON a.userId = u.userId "
			+ "LEFT JOIN answermarks AS m "
			+ "ON a.answerId = m.answerId "
			+ "GROUP BY a.answerId "
			+ "HAVING a.answerId = ?;";
	
	public static final String SELECT_QUESTION_ID_FOR_THE_ANSWER = 
			"SELECT questionId FROM Answers "
			+ "WHERE answerId = ?;";
	
	public static final String SELECT_USER_ANSWERS = 
			"SELECT q.questionId, a.answerId, a.answerText, "
			+ "a.creationDatetime, AVG(m.mark), COUNT(m.answerId) "
			+ "FROM answers AS a "
			+ "LEFT JOIN questions AS q "
			+ "ON a.questionId = q.questionId "
			+ "LEFT JOIN likeit2.answermarks AS m "
			+ "ON a.answerId = m.answerId "
			+ "WHERE a.userId = ? "
			+ "GROUP BY a.answerId;";
		
	private AnswerQuery(){
		
	}
}
