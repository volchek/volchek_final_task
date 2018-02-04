package by.tr.web.dao.impl.mysql.query;

public final class UserQuery {

	public final static String CHECK_EXIST_LOGIN = 
			"SELECT * FROM Users WHERE login = ?;";

	public final static String INSERT_USER = 
			"INSERT INTO Users (surname, name, "
			+ " birthdayDate, status, avatar, email, " 
			+ "reserveEmail, registrationDate, login, password) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public final static String LOG_IN = 
			"SELECT * FROM Users " 
			+ "WHERE login = ? and password = ?;";

	public final static String FIND_USER_BY_LOGIN = 
			"SELECT userId, surname, name, login, password, "
			+ "registrationDate, birthdayDate, accessLevel, status, " 
			+ "avatar, email, reserveEmail, isBanned "
			+ "FROM Users WHERE login = ?;";

	public final static String UPDATE_PERSONAL_DATA = 
			"UPDATE users SET surname = ?, "
			+ "name = ?, birthdayDate = ?, status = ?, " 
			+ "avatar = ?, email = ?, reserveEmail = ? "
			+ "WHERE userId = ?;";

	public final static String SELECT_USER_RATING = 
			"SELECT AVG(m.mark) " 
			+ "FROM ( " + "SELECT t1.mark "
			+ "FROM questionmarks AS t1 " 
			+ "WHERE t1.userId = ? " 
			+ "UNION ALL " + "SELECT t2.mark "
			+ "FROM answermarks AS t2 " 
			+ "INNER JOIN likeit2.answers AS a " 
			+ "ON t2.answerId = a.answerId "
			+ "WHERE a.userId = ? " + ") AS m ;";

	public final static String UPDATE_TO_BAN = 
			"UPDATE users SET isBanned = ? "
			+ "WHERE userId = ?;";
	
	public final static String UPDATE_TO_UNBAN = 
			"UPDATE users SET isBanned = 0 "
			+ "WHERE userId = ?;";

	private UserQuery() {

	}

}
