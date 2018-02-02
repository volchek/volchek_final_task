package by.tr.web.dao.database.mysql.query;

public final class LanguageQuery {

	public static final String SELECT_ALL_LANGUAGES = 
			"SELECT * FROM Languages";
	
	public static final String SELECT_LANGUAGE_NAMES = 
			"SELECT language FROM Languages";
	
	public static final String SELECT_ALL_LANGUAGES_FOR_USER = 
			"SELECT lang.languageId, " + "lang.language, us.level "
			+ "FROM likeit2.users2languages AS us " 
			+ "INNER JOIN likeit2.languages AS lang "
			+ "ON lang.languageId = us.languageId " 
			+ "WHERE us.userId = ?;";
	
	public final static String SELECT_LANGUAGE_FREQUENCY =
			"SELECT l.language, COUNT(ql.languageId) AS count "
			+ "FROM questions AS q "
			+ "INNER JOIN questions2languages AS ql "
			+ "ON q.questionId = ql.questionId "
			+ "INNER JOIN languages AS l "
			+ "ON ql.languageId = l.languageId "
			+ "GROUP BY ql.languageId "
			+ "ORDER BY count DESC;";
	
	public static final String INSERT_LANGUAGE =
			"INSERT INTO languages "
			+ "(language) VALUES (?);";
	
	public static final String INSERT_USER_LANGUAGE = 
			"INSERT INTO users2languages " 
			+ "(userId, languageId, level) "
			+ "VALUES (?, ?, ?);";
	
	public static final String UPDATE_USER_LANGUAGE = 
			"UPDATE users2languages "
			+ "SET level = ? "
			+ "WHERE userId = ? AND languageId = ?;";
	
	public static final String DELETE_USER_LANGUAGE = 
			"DELETE FROM users2languages"
			+ "	WHERE userId = ? AND languageId = ?;";

	private LanguageQuery() {

	}

}
