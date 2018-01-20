package by.tr.web.dao.mysql.query;

public final class AnswerQuery {

	public static final String INSERT_ANSWER = "INSERT INTO Answers "
			+ " (questionId, answerText, userId, creationDatetime) " 
			+ "VALUES (?, ?, ?, ?);";

	public static final String SELECT_ANSWERS_TO_THE_QUESTION = 
			"SELECT a.answerId, a.questionId, a.answerText, a.creationDatetime, u.login "
			+ "FROM Answers AS a "
			+ "INNER JOIN Users AS u "
			+ "ON a.userId = u.userId "
			+ "WHERE questionId = ?;";
		
	private AnswerQuery(){
		
	}
}
