package by.tr.web.dao.impl;

import by.tr.web.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.DaoLoginException;
import by.tr.web.dao.exception.FatalDaoException;
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
			if (!query.registerUser(conn, user)){
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
			} catch (MySqlException ex) { //
				throw new FatalDaoException(ex);
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
			} catch (MySqlException ex) { //
				throw new FatalDaoException(ex);
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
				throw new FatalDaoException(ex);
			}
		}
	}

}
