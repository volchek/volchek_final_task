package by.tr.web.dao.impl.mysql.submitter;

import java.math.BigDecimal;
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

import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.query.UserQuery;
import by.tr.web.dao.impl.mysql.util.DatabaseField;
import by.tr.web.entity.user.User;

public final class UserQuerySubmitter {

	private final static Logger logger = LogManager.getLogger(UserQuerySubmitter.class.getName());

	public static boolean registerUser(Connection conn, User user) throws MySqlException {

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
				LanguageQuerySubmitter.insertUserLanguages(conn, user.getLanguages(), userID);
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

	public static User signIn(Connection conn, String login, String password) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(UserQuery.LOG_IN)) {
			ps.setString(1, login);
			ps.setString(2, password);
			return executeQuery(conn, ps);
		} catch (SQLException ex) {
			logger.error("Failed to execute login command for user " + login);
			throw new MySqlException("Failed to execute login command", ex);
		}
	}

	public static User findUserByLogin(Connection conn, String login) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(UserQuery.FIND_USER_BY_LOGIN)) {
			ps.setString(1, login);
			return executeQuery(conn, ps);
		} catch (SQLException ex) {
			logger.error("Can't find user with login = " + login);
			throw new MySqlException("Failed to execute searching command", ex);
		}
	}

	public static void getUserRating(Connection conn, int userId, User user) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(UserQuery.SELECT_USER_RATING)) {
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();

			Double rating = extractRating(rs);
			user.setRating(rating);
		} catch (SQLException ex) {
			logger.error("Can't calculate rating for the user with id=" + userId);
			throw new MySqlException("Failed to execute searching command", ex);
		}
	}

	public static boolean updatePersonalInfo(Connection conn, User modifiedUser, int userId) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(UserQuery.UPDATE_PERSONAL_DATA);
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

	public static boolean updateUser(Connection conn, User currentUser, User modifiedUser) throws MySqlException {

		updatePersonalInfo(conn, modifiedUser, currentUser.getId());
		LanguageQuerySubmitter.updateUserLanguages(conn, currentUser, modifiedUser);

		return true;
	}

	public static void banUser(Connection conn, int userId, boolean ban) throws MySqlException {
		try (PreparedStatement ps = conn.prepareStatement(UserQuery.UPDATE_TO_BAN)) {

			int isBan = (ban ? 1 : 0);
			ps.setInt(1, isBan);
			ps.setInt(2, userId);

			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Failed to ban / unban user with id=" + userId);
			throw new MySqlException("Failed to ban / unban user", ex);
		}
	}

	private static boolean isExistedLogin(Connection conn, User user) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(UserQuery.CHECK_EXIST_LOGIN)) {
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
		if (user.getEmail() != null) {
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

		ps = conn.prepareStatement(UserQuery.INSERT_USER, Statement.RETURN_GENERATED_KEYS);

		ps = preparePersonalDataStatement(ps, user);

		ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
		ps.setString(9, user.getLogin());
		ps.setString(10, user.getPassword());

		return ps;
	}

	private static User executeQuery(Connection conn, PreparedStatement ps) throws MySqlException {

		try (ResultSet rs = ps.executeQuery()) {
			User user = getFirstUser(rs);
			if (user == null) {
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
			user.setRegistrationDate(rs.getDate(DatabaseField.REGISTRATION_DATE));

			String accessLevel = rs.getString(DatabaseField.ACCESS);
			boolean isAdmin = (accessLevel.equals("admin") ? true : false);
			user.setAdmin(isAdmin);

			result.add(user);
		}

		return result;
	}

	private static Double extractRating(ResultSet rs) throws SQLException {
		if (rs.next()) {
			BigDecimal rating = rs.getBigDecimal(1);
			return rating == null ? null : rating.doubleValue();
		}
		return null;
	}

	private static User getFirstUser(ResultSet rs) throws SQLException {

		List<User> result = createUsers(rs);

		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

	private static void addLanguages(Connection conn, User user) throws SQLException, MySqlException {
		LanguageQuerySubmitter.getUserLanguages(conn, user);
	}

	private static void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			logger.warn("Can not close Statement");
		}
	}

	private UserQuerySubmitter() {

	}

}
