package by.tr.web.dao.database.mysql.query;

public final class AnswerQuery {

	public static final String INSERT_ANSWER = "INSERT INTO Answers "
			+ " (questionId, answerText, userId, creationDatetime) " 
			+ "VALUES (?, ?, ?, ?);";

	public static final String SELECT_ANSWERS_TO_THE_QUESTION = 
			"SELECT a.answerId, a.questionId, a.answerText, a.creationDatetime, u.login, AVG(m.mark) "
			+ "FROM Answers AS a "
			+ "INNER JOIN Users AS u "
			+ "ON a.userId = u.userId "
			+ "LEFT JOIN answermarks AS m "
			+ "ON a.answerId = m.answerId "
			+ "GROUP BY a.answerId "
			+ "HAVING a.questionId = ?;";
		
	private AnswerQuery(){
		
	}
}
