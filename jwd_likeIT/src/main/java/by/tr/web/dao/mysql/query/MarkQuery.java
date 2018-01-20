package by.tr.web.dao.mysql.query;

public final class MarkQuery {

	public static final String CHECK_QUESTION_MARK = 
			"SELECT * FROM QuestionMarks "
			+ "WHERE questionId = ? AND userId = ?;";

	public static final String CHECK_ANSWER_MARK = 
			"SELECT * FROM AnswerMarks "
			+ "WHERE answerId = ? AND userId = ?;";

	public static final String INSERT_QUESTION_MARK = 
			"INSERT INTO QuestionMarks (questionId, userId, mark, datetime) "
			+ "VALUES (?, ?, ?, ?);";
	
	public static final String INSERT_ANSWER_MARK = "INSERT INTO AnswerMarks "
			+ "(answerId, userId, mark, datetime) "
			+ "VALUES (?, ?, ?, ?);";
	
	public static final String UPDATE_QUESTION_MARK = 
			"UPDATE QuestionMarks SET mark = ?, datetime = ? "
			+ "WHERE questionId = ? AND userId = ?;";
	
	public static final String UPDATE_ANSWER_MARK = 
			"UPDATE AnswerMarks SET mark = ?, datetime = ? "
			+ "WHERE answerId = ? AND userId = ?;";
	
	public static final String FIND_AVERAGE_QUESTION_MARK = 
			"SELECT AVG(mark) FROM QuestionMarks "
			+ "WHERE questionId = ?";

	public static final String FIND_AVERAGE_ANSWER_MARK = 
			"SELECT AVG(mark) FROM AnswerMarks "
			+ "WHERE answerId = ?";
	
	public static final String DELETE_QUESTION_MARK = 
			"DELETE FROM QuestionMarks "
			+ "WHERE questionId = ? AND userId = ?;";
	
	public static final String DELETE_ANSWER_MARK = 
			"DELETE FROM AnswerMarks "
			+ "WHERE answerId = ? AND userId = ?;";

	private MarkQuery(){
		
	}
	
}
