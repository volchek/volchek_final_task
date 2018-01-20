package by.tr.web.dao.mysql_query;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.mysql_query.util.DatabaseField;
import by.tr.web.entity.User;

public class MySQLUserQuery {

	private final static String CHECK_EXIST_LOGIN = "SELECT * FROM Users WHERE login = ?;";
	private final static String INSERT_USER_QUERY = "INSERT INTO Users (surname, name, "
			+ " birthdayDate, status, avatar, email, reserveEmail, registrationDate, login, password) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String LOG_IN_QUERY = "SELECT * FROM Users WHERE login = ? and password = ?;";
	private final static String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM Users WHERE login = ?;";
	private final static String UPDATE_PERSONAL_DATA = "UPDATE users SET surname = ?, "
			+ "name = ?, birthdayDate = ?, status = ?, avatar = ?, email = ?, reserveEmail = ? " + "WHERE userId = ?;";

	private final static Logger logger = LogManager.getLogger(MySQLUserQuery.class.getName());

	private MySQLLanguageQuery langQuery = new MySQLLanguageQuery();

	
	
	public boolean registerUser(Connection conn, User user) throws MySqlException {

		if (isExistedLogin(conn, user)) {
			return false;
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = prepareUserInsertionStatement(conn, ps, user);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int userID = rs.getInt(1);
				langQuery.insertUserLanguages(conn, user.getLanguages(), userID);
				conn.commit();
			} else {
				conn.rollback();
			}
			return true;
		} catch (SQLException ex) {
			logger.error("Can't register user with login = " + user.getLogin());
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error("Can't rollback transaction");
			}
			throw new MySqlException("Failed to execute registration command", ex);
		} finally {
			closeStatement(ps);
		}
	}

	public User signIn(Connection conn, String login, String password) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(LOG_IN_QUERY)) {
			ps.setString(1, login);
			ps.setString(2, password);
			return executeQuery(conn, ps);
		} catch (SQLException ex) {
			logger.error("Failed to execute login command for user " + login);
			throw new MySqlException("Failed to execute login command", ex);
		}
	}

	public User findUserByLogin(Connection conn, String login) throws MySqlException {
		
		try (PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN_QUERY)) {
			ps.setString(1, login);
			return executeQuery(conn, ps);
		} catch (SQLException ex) {
			logger.error("Can't find user with login = " + login);
			throw new MySqlException("Failed to execute searching command",  ex);
		}
	}

	public boolean updatePersonalInfo(Connection conn, User modifiedUser, int userId) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(UPDATE_PERSONAL_DATA); 
			ps = preparePersonalDataStatement(ps, modifiedUser);
			ps.setInt(8, userId);
			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			logger.error("Can't update personal information for user with id = " + userId);
			throw new MySqlException("Failed to update personal info", ex);
		} finally {
			closeStatement(ps);
		}
	}
	
	public boolean updateUser(Connection conn, User currentUser, User modifiedUser) throws MySqlException {

		updatePersonalInfo(conn, modifiedUser, currentUser.getId());
		langQuery.updateUserLanguages(conn, currentUser, modifiedUser);
		
		return true;
	}

	private boolean isExistedLogin(Connection conn, User user) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(CHECK_EXIST_LOGIN)) {
			ps.setString(1, user.getLogin());
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				return true;
			}
			return false;
		} catch (SQLException ex) {
			logger.error("Failed to check login command for user " + user.getLogin());
			throw new MySqlException("Failed to check login command", ex);
		}
	}

	private static PreparedStatement preparePersonalDataStatement(PreparedStatement ps, User user) throws SQLException {

		ps.setString(1, user.getSurname());
		ps.setString(2, user.getName());
		java.sql.Date sqlBirthday = new java.sql.Date(user.getBirthday().getTime());
		ps.setDate(3, sqlBirthday);
		ps.setString(4, user.getStatus());
		ps.setString(5, user.getAvatar());
		if (user.getEmail() != null){
			ps.setString(6, user.getEmail().get(0));
			if (user.getEmail().size() > 1) {
				ps.setString(7, user.getEmail().get(1));
			} else {
				ps.setString(7, null);
			}
		}
		return ps;
	}
	
	private static PreparedStatement prepareUserInsertionStatement(Connection conn, PreparedStatement ps, User user)
			throws SQLException {

		ps = conn.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

		ps = preparePersonalDataStatement(ps, user);

		ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
		ps.setString(9, user.getLogin());
		ps.setString(10, user.getPassword());

		return ps;
	}

	private User executeQuery(Connection conn, PreparedStatement ps) throws MySqlException {

		try (ResultSet rs = ps.executeQuery()) {
			User user = getFirstUser(rs);
			if (user == null){
				throw new MySqlException("User not found in database");
			}
			addLanguages(conn, user);
			return user;
		} catch (SQLException ex) {
			logger.error("Failed to execute query");
			throw new MySqlException("Failed to execute query", ex);
		}
	}

	private static List<User> createUsers(ResultSet rs) throws SQLException {

		List<User> result = new ArrayList<User>();

		while (rs.next()) {

			User user = new User();

			user.setSurname(rs.getString(DatabaseField.SURNAME));
			user.setName(rs.getString(DatabaseField.NAME));
			user.setLogin(rs.getString(DatabaseField.LOGIN));
			user.setPassword(rs.getString(DatabaseField.PASSWORD));
			user.setBirthday(rs.getDate(DatabaseField.BIRTHDAY_DATE));
			user.setAvatar(rs.getString(DatabaseField.AVATAR));
			user.addEmail(rs.getString(DatabaseField.EMAIL));
			user.addEmail(rs.getString(DatabaseField.RESERVE_EMAIL));
			user.setStatus(rs.getString(DatabaseField.STATUS));
			user.setBanned(rs.getBoolean(DatabaseField.IS_BANNED));
			user.setId(rs.getInt(DatabaseField.USER_ID));

			String accessLevel = rs.getString(DatabaseField.ACCESS);
			boolean isAdmin = (accessLevel.equals("admin") ? true : false);
			user.setAdmin(isAdmin);

			result.add(user);
		}
		
		return result;
	}


	private User getFirstUser(ResultSet rs) throws SQLException {

		List<User> result = createUsers(rs);

		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

	private void addLanguages(Connection conn, User user) throws SQLException, MySqlException {
		langQuery.getUserLanguages(conn, user);
	}

	private void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can not close Statement");
		}
	}
	
}
