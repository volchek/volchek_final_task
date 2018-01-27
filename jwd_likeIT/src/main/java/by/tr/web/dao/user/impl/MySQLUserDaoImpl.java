package by.tr.web.dao.user.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.entity.User;
import by.tr.web.dao.database.mysql.submitter.LanguageQuerySubmitter;
import by.tr.web.dao.database.mysql.submitter.UserQuerySubmitter;
import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.database.util.pool.ConnectionPool;
import by.tr.web.dao.database.util.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.DaoLoginException;
import by.tr.web.dao.user.UserDao;

public class MySQLUserDaoImpl implements UserDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLUserDaoImpl.class.getName());

	@Override
	public boolean registerUser(User user) throws DaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			UserQuerySubmitter query = new UserQuerySubmitter();
			conn.setAutoCommit(false);
			if (!query.registerUser(conn, user)) {
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
			UserQuerySubmitter query = new UserQuerySubmitter();
			User user = query.signIn(conn, login, password);
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
			UserQuerySubmitter query = new UserQuerySubmitter();
			User user = query.findUserByLogin(conn, login);

			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			query.getUserRating(conn, user.getId(), user);
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
			UserQuerySubmitter query = new UserQuerySubmitter();
			int id = currentUser.getId();

			if (query.updatePersonalInfo(conn, modifiedUser, id)) {
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
			LanguageQuerySubmitter query = new LanguageQuerySubmitter();
			int id = currentUser.getId();

			if (query.updateUserLanguages(conn, currentUser, modifiedUser)) {
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
			UserQuerySubmitter query = new UserQuerySubmitter();
			int id = currentUser.getId();

			if (query.updateUser(conn, currentUser, modifiedUser)) {
				updatePersonalInfoOfCurrentUser(currentUser, modifiedUser);
				updateLanguagesOfCurrentUser(currentUser, modifiedUser);
				conn.commit();
				return true;
			} else {
				throw new DaoLoginException("Can't update user with id " + id);
			}
		} catch (MySqlException | SQLException ex) {
			try {
				if (!conn.getAutoCommit()){
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
