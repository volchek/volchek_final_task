package by.tr.web.dao.impl.mysql.query;

public final class QuestionQuery {
	
	public final static String INSERT_QUESTION = 
			"INSERT INTO Questions (title, text, userId, creationDatetime) "
			+ "VALUES (?, ?, ?, ?)";

	public final static String INSERT_QUESTION_LANGUAGES = 
			"INSERT INTO Questions2Languages (questionId, languageId) "
			+ "VALUES (?, ?)";

	public final static String INSERT_QUESTION_TAGS = 
			"INSERT INTO Questions2Keywords (questionId, keywordId) "
			+ "VALUES (?, ?)";
	
	public final static String UPDATE_QUESTION_TEXT = 
			"UPDATE questions "
			+ "SET text = ? "
			+ "WHERE questionId = ? "
			+ "AND userId = ?;";

	public final static String DELETE_QUESTION =
			"DELETE FROM questions "
			+ "WHERE questionId = ?;";
	
	public final static String SELECT_QUESTIONS = 
			"SELECT q.questionId, q.title, q.text, q.creationDatetime,"
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), "
			+ "u.login, AVG(m.mark) "
			+ "FROM questions AS q "
			+ "LEFT JOIN questions2keywords AS qk "
			+ "ON q.questionId = qk.questionId "
			+ "INNER JOIN keywords AS k "
			+ "ON k.keywordId = qk.keywordId "
			+ "LEFT JOIN questions2languages AS ql "
			+ "ON q.questionId = ql.questionId "
			+ "INNER JOIN languages AS l "
			+ "ON ql.languageId = l.languageId "
			+ "INNER JOIN users AS u "
			+ "ON q.userId = u.userId "
			+ "LEFT JOIN questionmarks AS m "
			+ "ON q.questionId = m.questionId ";
	
	public final static String SELECT_QUESTION_BY_ID = 
			SELECT_QUESTIONS
			+ "GROUP BY q.questionId "
			+ "HAVING q.questionId = ? ;";

	public final static String SELECT_USER_QUESTIONS =
			SELECT_QUESTIONS
			+ "WHERE u.userId = ? "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC;";

	public final static String SELECT_QUESTIONS_BY_LANGUAGES = 
			SELECT_QUESTIONS
			+ "WHERE q.questionId IN "
			+ "(SELECT ql.questionId FROM questions2languages AS ql "
			+ "WHERE ql.languageId IN (?, ?, ?)) "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC;";
	
	public final static String SELECT_QUESTIONS_BY_LANGUAGE = 
			SELECT_QUESTIONS
			+ "WHERE q.questionId IN "
			+ "(SELECT ql.questionId FROM questions2languages AS ql "
			+ "WHERE ql.languageId = ?) "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC;";
	
	public final static String SELECT_QUESTIONS_BY_TAGS = 
			SELECT_QUESTIONS
			+ "WHERE q.questionId IN "
			+ "(SELECT qk.questionId FROM questions2keywords AS qk "
			+ "WHERE qk.keywordId IN (?, ?, ?)) "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC;";
	
	public final static String SELECT_LAST_QUESTIONS = 
			SELECT_QUESTIONS
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC "
			+ "LIMIT ?;";
	
	public final static String SELECT_LAST_QUESTIONS_FOR_USER =
			SELECT_QUESTIONS
			+ "WHERE q.questionId IN "
			+ "(SELECT ql.questionId FROM likeit2.questions2languages AS ql "
			+ "WHERE ql.languageId IN "
			+ "(SELECT ul.languageId "
			+ "FROM likeit2.users2languages AS ul "
			+ "WHERE ul.userId = ?)) "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC ;";
	
	public final static String SELECT_QUESTIONS_WITH_USER_ANSWERS =
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, "
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), "
			+ "u.login, AVG(m.mark), a.answerId "
			+ "FROM questions AS q "
			+ "LEFT JOIN questions2keywords AS qk "
			+ "ON q.questionId = qk.questionId "
			+ "LEFT JOIN keywords AS k "
			+ "ON k.keywordId = qk.keywordId "
			+ "LEFT JOIN questions2languages AS ql "
			+ "ON q.questionId = ql.questionId "
			+ "LEFT JOIN languages AS l "
			+ "ON ql.languageId = l.languageId "
			+ "INNER JOIN users AS u "
			+ "ON q.userId = u.userId "
			+ "LEFT JOIN questionmarks AS m "
			+ "ON q.questionId = m.questionId "
			+ "LEFT JOIN answers AS a "
			+ "ON q.questionId = a.questionId "
			+ "WHERE q.questionId "
			+ "IN (SELECT DISTINCT a.questionId "
			+ "FROM Answers AS a "
			+ "WHERE a.userId = ?) "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC;";
	
	private QuestionQuery() {
		
	}

}
