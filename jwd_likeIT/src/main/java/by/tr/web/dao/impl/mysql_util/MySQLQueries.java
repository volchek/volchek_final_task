package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlMessage;
import by.tr.web.entity.User;
import by.tr.web.entity.UserAttributes;

public class MySQLQueries implements QueriesUtils {

	private static final String INSERT_USER_QUERY = "INSERT INTO Users (surname, name, login, password, status) VALUES (?, ?, ?, ?, ?)";
	private static final String LOG_IN_QUERY = "SELECT * FROM Users WHERE login = ? and password = ?;";
	private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM Users WHERE login = ?;";

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void registerUser(Connection conn, User user) throws MySqlException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_USER_QUERY);
			ps.setString(1, user.getSurname());
			ps.setString(2, user.getName());
			ps.setString(3, user.getLogin());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getStatus());

			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			throw new MySqlException(ex);
		} finally {
			closeStatement(ps);
		}
	}

	@Override
	public User signIn(Connection conn, String login, String password) throws MySqlException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(LOG_IN_QUERY);
			ps.setString(1, login);
			ps.setString(2, password);
			return executeQuery(ps);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			throw new MySqlException(ex);
		} finally {
			closeStatement(ps);
		}
	}

	@Override
	public User findUserByLogin(Connection conn, String login) throws MySqlException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
			ps.setString(1, login);
			return executeQuery(ps);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			throw new MySqlException(ex);
		} finally {
			closeStatement(ps);
		}
	}

	private static List<User> createUsers(ResultSet rs) throws SQLException {

		List<User> result = new ArrayList<User>();

		while (rs.next()) {
			String surname = rs.getString(UserAttributes.SURNAME);
			String name = rs.getString(UserAttributes.NAME);
			String login = rs.getString(UserAttributes.LOGIN);
			String status = rs.getString(UserAttributes.STATUS);

			User user = new User();

			user.setSurname(surname);
			user.setName(name);
			user.setLogin(login);
			user.setStatus(status);
			result.add(user);
		}

		return result;
	}

	private User executeQuery(PreparedStatement ps) throws SQLException {

		ResultSet rs = null;
		try {
			rs = ps.executeQuery();

			User user = getFirstUser(rs);
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

	private void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			logger.warn(MySqlMessage.STATEMENT_ERROR);
		}
	}

	private void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			logger.warn(MySqlMessage.RESULT_SET_ERROR);
		}
	}

}
