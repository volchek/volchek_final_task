package by.tr.web.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.database.pool.ConnectionPool;
import by.tr.web.dao.database.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.DaoLoginException;
import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.submitter.LanguageQuerySubmitter;
import by.tr.web.dao.impl.mysql.submitter.UserQuerySubmitter;
import by.tr.web.entity.user.User;

public class MySQLUserDaoImpl implements UserDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLUserDaoImpl.class.getName());

	@Override
	public boolean registerUser(User user) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			conn.setAutoCommit(false);
			if (!UserQuerySubmitter.registerUser(conn, user)) {
				throw new DaoLoginException("Such user exists in database");
			}
			conn.commit();
			return true;
		} catch (MySqlException | SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error("Failed to rollback transaction");
			}
			throw new DaoException(ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("Failed to set autocommit mode");
			}
			connPool.closeConnection(conn);
		}
	}

	@Override
	public User signIn(String login, String password) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			User user = UserQuerySubmitter.signIn(conn, login, password);
			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			return user;
		} catch (MySqlException ex) {
			throw new DaoException("Can't execute query", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public User findUserByLogin(String login) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			User user = UserQuerySubmitter.findUserByLogin(conn, login);

			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			UserQuerySubmitter.getUserRating(conn, user.getId(), user);
			return user;
		} catch (MySqlException ex) {
			throw new DaoException("Can't execute query", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public boolean updatePersonalInfo(User currentUser, User modifiedUser) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();

			int id = currentUser.getId();

			if (UserQuerySubmitter.updatePersonalInfo(conn, modifiedUser, id)) {
				updatePersonalInfoOfCurrentUser(currentUser, modifiedUser);
				return true;
			} else {
				throw new DaoLoginException("Can't update personal info for user with id " + id);
			}
		} catch (MySqlException ex) {
			throw new DaoException(ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	@Override
	public boolean updateUserLanguages(User currentUser, User modifiedUser) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			conn.setAutoCommit(false);

			int id = currentUser.getId();

			if (LanguageQuerySubmitter.updateUserLanguages(conn, currentUser, modifiedUser)) {
				updateLanguagesOfCurrentUser(currentUser, modifiedUser);
				conn.commit();
				return true;
			} else {
				throw new DaoLoginException("Can't update language list for user with id " + id);
			}
		} catch (MySqlException | SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error("Failed to rollback transaction");
			}
			throw new DaoException(ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("Failed to set autocommit mode");
			}
			connPool.closeConnection(conn);
		}
	}

	@Override
	public boolean updateUser(User currentUser, User modifiedUser) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}

			int id = currentUser.getId();

			if (UserQuerySubmitter.updateUser(conn, currentUser, modifiedUser)) {
				updatePersonalInfoOfCurrentUser(currentUser, modifiedUser);
				updateLanguagesOfCurrentUser(currentUser, modifiedUser);
				conn.commit();
				return true;
			} else {
				throw new DaoLoginException("Can't update user with id " + id);
			}
		} catch (MySqlException | SQLException ex) {
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
				}
			} catch (SQLException e) {
				logger.error("Failed to rollback transaction");
			}
			throw new DaoException("Can't update user", ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("Failed to set autocommit mode");
			}
			connPool.closeConnection(conn);
		}
	}

	@Override
	public void banUser(int userId, boolean ban) throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();

			UserQuerySubmitter.banUser(conn, userId, ban);
		} catch (MySqlException ex) {
			logger.error("Can't ban / unban user with id=" + userId);
			throw new DaoException("Failed to ban / unban user", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	private void updatePersonalInfoOfCurrentUser(User currentUser, User modifiedUser) {
		currentUser.setSurname(modifiedUser.getSurname());
		currentUser.setName(modifiedUser.getName());
		currentUser.setStatus(modifiedUser.getStatus());
		currentUser.setAvatar(modifiedUser.getAvatar());
		currentUser.setEmail(modifiedUser.getEmail());
		currentUser.setBirthday(modifiedUser.getBirthday());
	}

	private void updateLanguagesOfCurrentUser(User currentUser, User modifiedUser) {
		currentUser.getLanguages().clear();
		for (Map.Entry<String, Integer> lang : modifiedUser.getLanguages().entrySet()) {
			currentUser.addLanguage(lang.getKey(), lang.getValue());
		}
	}

}
