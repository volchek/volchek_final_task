package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.entity.User;

public class MySQLUserQuery {

	private static final String CHECK_EXIST_LOGIN = "SELECT * FROM Users WHERE login = ?;";
	private static final String INSERT_USER_QUERY = "INSERT INTO Users (surname, name, "
			+ " birthdayDate, status, avatar, email, reserveEmail, registrationDate, login, password) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String LOG_IN_QUERY = "SELECT * FROM Users WHERE login = ? and password = ?;";
	private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM Users WHERE login = ?;";
	private static final String UPDATE_PERSONAL_DATA = "UPDATE users SET surname = ?, "
			+ "name = ?, birthdayDate = ?, status = ?, avatar = ?, email = ?, reserveEmail = ? " + "WHERE userId = ?;";

	private static final Logger logger = LogManager.getLogger();

	private MySQLLanguageQuery langQuery = new MySQLLanguageQuery();

	public boolean registerUser(Connection conn, User user) throws MySqlException {

		if (isExistedLogin(conn, user)) {
			return false;
		}

		PreparedStatement ps = null;
		try {
			langQuery.getLanguages().clear();
			langQuery.getAllLanguages(conn);

			conn.setAutoCommit(false);
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
		} catch (SQLException | MySqlException ex) {
			logger.error(ex.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e) {
				// Add logging
			}
			throw new MySqlException(ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// Add logging
			}
			closeStatement(ps);
		}
	}

	public User signIn(Connection conn, String login, String password) throws MySqlException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(LOG_IN_QUERY);
			ps.setString(1, login);
			ps.setString(2, password);
			return executeQuery(conn, ps);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			throw new MySqlException(ex);
		} finally {
			closeStatement(ps);
		}
	}

	public User findUserByLogin(Connection conn, String login) throws MySqlException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
			ps.setString(1, login);
			return executeQuery(conn, ps);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			throw new MySqlException(ex);
		} finally {
			closeStatement(ps);
		}
	}

	public boolean updatePersonalData(Connection conn, User modifiedUser, int userId) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(UPDATE_PERSONAL_DATA);
			ps = preparePersonalDataStatement(ps, modifiedUser);
			ps.setInt(8, userId);
			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			return false;
		} finally {
			closeStatement(ps);
		}
	}
	
	public boolean updateUser(Connection conn, User currentUser, User modifiedUser) throws MySqlException {

		PreparedStatement ps = null;
		try {
			updatePersonalData(conn, modifiedUser, currentUser.getId());
			langQuery.updateUserLanguages(conn, currentUser, modifiedUser);
			return true;
		}  finally {
			closeStatement(ps);
		}
	}

	private boolean isExistedLogin(Connection conn, User user) throws MySqlException {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(CHECK_EXIST_LOGIN);
			ps.setString(1, user.getLogin());
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				return true;
			}
			return false;
		} catch (SQLException ex) {
			// add logging
			throw new MySqlException("There is an error when user login is checked", ex);
		}
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

	private static PreparedStatement preparePersonalDataStatement(PreparedStatement ps, User user) throws SQLException {

		ps.setString(1, user.getSurname());
		ps.setString(2, user.getName());
		java.sql.Date sqlBirthday = new java.sql.Date(user.getBirthday().getTime());
		ps.setDate(3, sqlBirthday);
		ps.setString(4, user.getStatus());
		ps.setString(5, user.getAvatar());
		if (user.getEmail() != null){
			ps.setString(6, user.getEmail().get(0));
		}
		if (user.getEmail().size() > 1) {
			ps.setString(7, user.getEmail().get(1));
		} else {
			ps.setString(7, null);
		}

		return ps;

	}

	private static List<User> createUsers(ResultSet rs) throws SQLException {

		List<User> result = new ArrayList<User>();

		while (rs.next()) {
			String surname = rs.getString(DatabaseField.SURNAME);
			String name = rs.getString(DatabaseField.NAME);

			String login = rs.getString(DatabaseField.LOGIN);
			String password = rs.getString(DatabaseField.PASSWORD);

			Date birthday = rs.getDate(DatabaseField.BIRTHDAY_DATE);
			String avatar = rs.getString(DatabaseField.AVATAR);
			String email = rs.getString(DatabaseField.EMAIL);
			String reserveEmail = rs.getString(DatabaseField.RESERVE_EMAIL);

			String status = rs.getString(DatabaseField.STATUS);
			String accessLevel = rs.getString(DatabaseField.ACCESS);
			boolean isAdmin = (accessLevel.equals("admin") ? true : false);
			boolean isBanned = rs.getBoolean(DatabaseField.IS_BANNED);
			int id = rs.getInt(DatabaseField.USER_ID);

			User user = new User();

			user.setSurname(surname);
			user.setName(name);
			user.setLogin(login);
			user.setPassword(password);
			user.setBirthday(birthday);
			user.setAvatar(avatar);
			user.addEmail(email);
			user.addEmail(reserveEmail);
			user.setStatus(status);
			user.setAdmin(isAdmin);
			user.setBanned(isBanned);
			user.setId(id);

			result.add(user);
		}

		return result;
	}

	private User executeQuery(Connection conn, PreparedStatement ps) throws SQLException, MySqlException {

		ResultSet rs = null;
		try {
			rs = ps.executeQuery();

			User user = getFirstUser(rs);
			addLanguages(conn, user);
			return user;
		} finally {
			closeResultSet(rs);
			closeStatement(ps);
		}
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

	private void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can not close Result Set");
		}
	}

}
