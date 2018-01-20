package by.tr.web.dao.mysql.query;

public final class UserQuery {

	public final static String CHECK_EXIST_LOGIN = 
			"SELECT * FROM Users WHERE login = ?;";

	public final static String INSERT_USER = "INSERT INTO Users (surname, name, "
			+ " birthdayDate, status, avatar, email, " 
			+ "reserveEmail, registrationDate, login, password) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public final static String LOG_IN = 
			"SELECT * FROM Users "
			+ "WHERE login = ? and password = ?;";

	public final static String FIND_USER_BY_LOGIN = 
			"SELECT * FROM Users WHERE login = ?;";

	public final static String UPDATE_PERSONAL_DATA = 
			"UPDATE users SET surname = ?, "
			+ "name = ?, birthdayDate = ?, status = ?, " 
			+ "avatar = ?, email = ?, reserveEmail = ? "
			+ "WHERE userId = ?;";

	private UserQuery() {

	}

}
