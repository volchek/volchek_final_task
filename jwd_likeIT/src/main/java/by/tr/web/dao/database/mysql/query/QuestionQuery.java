package by.tr.web.dao.database.mysql.query;

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

	public final static String SELECT_QUESTION_BY_ID = 
			"SELECT q.questionId, q.title, q.text, q.creationDatetime,"
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), u.login  "
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
			+ "WHERE q.questionId = ?;";

	public final static String SELECT_USER_QUESTIONS =
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, "
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
			+ "ON q.questionId = m.questionId "
			+ "WHERE u.userId = ? "
			+ "GROUP BY q.questionId;";

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
			+ "GROUP BY q.questionId;";
	
	public final static String SELECT_QUESTIONS_BY_LANGUAGE = 
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, "
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), "
			+ "u.login, AVG(m.mark) "
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
			+ "WHERE q.questionId IN "
			+ "(SELECT ql.questionId FROM questions2languages AS ql "
			+ "WHERE ql.languageId IN (?, ?, ?)) "
			+ "GROUP BY q.questionId;";
	
	public final static String SELECT_QUESTIONS_BY_TAG = 
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, "
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), "
			+ "u.login, AVG(m.mark) "
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
			+ "WHERE q.questionId IN "
			+ "(SELECT qk.questionId FROM questions2keywords AS qk "
			+ "WHERE qk.keywordId IN (?, ?, ?)) "
			+ "GROUP BY q.questionId;";
	
	public final static String SELECT_LAST_QUESTIONS = 
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, "
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), "
			+ "u.login, AVG(m.mark) "
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
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC "
			+ "LIMIT ?;";
	
	public final static String SELECT_LAST_QUESTIONS_FOR_USER =
			"SELECT q.questionId, q.title, q.text, q.creationDatetime, " 
			+ "GROUP_CONCAT(DISTINCT l.language ORDER BY l.language ASC SEPARATOR','), "
			+ "GROUP_CONCAT(DISTINCT k.keyword ORDER BY k.keyword ASC SEPARATOR ','), "
			+ "u.login, AVG(m.mark) "
			+ "FROM likeit2.questions AS q " 
			+ "LEFT JOIN questions2keywords AS qk "
			+ "ON q.questionId = qk.questionId "
			+ "LEFT JOIN likeit2.keywords AS k "
			+ "ON k.keywordId = qk.keywordId "
			+ "LEFT JOIN likeit2.questions2languages AS ql "
			+ "ON q.questionId = ql.questionId "
			+ "LEFT JOIN likeit2.languages AS l "
			+ "ON ql.languageId = l.languageId "
			+ "LEFT JOIN likeit2.users AS u "
			+ "ON q.userId = u.userId "
			+ "LEFT JOIN likeit2.questionmarks AS m "
			+ "ON q.questionId = m.questionId "
			+ "WHERE q.questionId IN "
			+ "(SELECT ql.questionId FROM likeit2.questions2languages AS ql "
			+ "WHERE ql.languageId IN "
			+ "(SELECT ul.languageId "
			+ "	FROM likeit2.users2languages AS ul "
			+ "	WHERE ul.userId = 1)) "
			+ "GROUP BY q.questionId "
			+ "ORDER BY q.creationDatetime DESC ;";

	private QuestionQuery() {
		
	}

}
