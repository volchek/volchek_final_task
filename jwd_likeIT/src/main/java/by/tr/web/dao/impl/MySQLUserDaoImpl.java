package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.Map;

import by.tr.web.entity.User;
import by.tr.web.entity.language.Language;
import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.DaoLoginException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.MySQLLanguageQuery;
import by.tr.web.dao.impl.mysql_util.MySQLUserQuery;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;

public class MySQLUserDaoImpl implements UserDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	@Override
	public boolean registerUser(User user) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			MySQLUserQuery query = new MySQLUserQuery();
			if (!query.registerUser(conn, user)) {
				throw new DaoLoginException("Such user exists in database");
			}
			return true;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't get connection", ex);
		} catch (MySqlException ex) {
			throw new DaoException(ex);
		} finally {
			try {
				connPool.closeConnection(conn);
			} catch (MySqlException ex) {
				// Add logging
			}
		}
	}

	@Override
	public User signIn(String login, String password) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			MySQLUserQuery query = new MySQLUserQuery();
			User user = query.signIn(conn, login, password);
			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			return user;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't get connection", ex);
		} catch (MySqlException ex) {
			throw new DaoException("Can't execute query", ex);
		} finally {
			try {
				connPool.closeConnection(conn);
			} catch (MySqlException ex) {
				// Add logging
			}
		}
	}

	@Override
	public User findUserByLogin(String login) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			MySQLUserQuery query = new MySQLUserQuery();
			User user = query.findUserByLogin(conn, login);

			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			return user;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't get connection", ex);
		} catch (MySqlException ex) {
			throw new DaoException("Can't execute query", ex);
		} finally {
			try {
				connPool.closeConnection(conn);
			} catch (MySqlException ex) {
				// Add logging
			}
		}
	}

	@Override
	public boolean updatePersonalInfo(User currentUser, User modifiedUser) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			MySQLUserQuery query = new MySQLUserQuery();
			int id = currentUser.getId();

			if (query.updatePersonalData(conn, modifiedUser, id)) {
				updatePersonalDataOfCurrentUser(currentUser, modifiedUser);
				return true;
			} else {
				throw new DaoLoginException("Can't update personal info for user with id " + id);
			}
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't get connection", ex);
		} catch (MySqlException ex) {
			throw new DaoException(ex);
		} finally {
			try {
				connPool.closeConnection(conn);
			} catch (MySqlException ex) {
				// Add logging
			}
		}
	}

	@Override
	public boolean updateUserLanguages(User currentUser, User modifiedUser) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = connPool.getConnection();
			MySQLLanguageQuery query = new MySQLLanguageQuery();
			int id = currentUser.getId();

			if (query.updateUserLanguages(conn, currentUser, modifiedUser)) {
				updateLanguagesOfCurrentUser(currentUser, modifiedUser);
				return true;
			} else {
				throw new DaoLoginException("Can't update language list for user with id " + id);
			}
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't get connection", ex);
		} finally {
			try {
				connPool.closeConnection(conn);
			} catch (MySqlException ex) {
				// Add logging
			}
		}
	}

	@Override
	public boolean updateUser(User currentUser, User modifiedUser) throws DaoException, FatalDaoException {
		System.out.println("HERE");
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			MySQLUserQuery query = new MySQLUserQuery();
			int id = currentUser.getId();

			if (query.updateUser(conn, currentUser, modifiedUser)) {
				System.out.println("UPDATE USER");
				updatePersonalDataOfCurrentUser(currentUser, modifiedUser);
				updateLanguagesOfCurrentUser(currentUser, modifiedUser);
				return true;
			} else {
				throw new DaoLoginException("Can't update user with id " + id);
			}
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Can't get connection", ex);
		} catch (MySqlException ex) {
			// add logging
			throw new DaoException("Can't update user", ex);
		} finally {
			try {
				connPool.closeConnection(conn);
			} catch (MySqlException ex) {
				// Add logging
			}
		}
	}

	private void updatePersonalDataOfCurrentUser(User currentUser, User modifiedUser) {
		currentUser.setSurname(modifiedUser.getSurname());
		currentUser.setName(modifiedUser.getName());
		currentUser.setStatus(modifiedUser.getStatus());
		currentUser.setAvatar(modifiedUser.getAvatar());
		currentUser.setEmail(modifiedUser.getEmail());
		currentUser.setBirthday(modifiedUser.getBirthday());
		System.out.println("OK");
	}

	private void updateLanguagesOfCurrentUser(User currentUser, User modifiedUser) {
		// currentUser.setLanguages(modifiedUser.getLanguages()); - shallow copy
		currentUser.getLanguages().clear();
		for (Map.Entry<Language, Integer> lang : modifiedUser.getLanguages().entrySet()) {
			currentUser.addLanguage(lang.getKey(), lang.getValue());
		}
	}

}
