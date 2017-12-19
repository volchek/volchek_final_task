package by.tr.web.dao.impl;

import by.tr.web.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.ConnectionUtil;
import by.tr.web.dao.impl.mysql_util.MySQLQuery;
import by.tr.web.dao.impl.mysql_util.QueriesUtil;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;

public class MySQLUserDaoImpl implements UserDao {

	
	@Override
	public boolean registerUser(User user) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			QueriesUtil query = new MySQLQuery();
			query.registerUser(conn, user);
			return true;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Database connection is failed", ex);
		} catch (MySqlException | SQLException ex) {
			throw new DaoException("Can't register such user", ex);
		} finally {
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public User signIn(String login, String password) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			QueriesUtil query = new MySQLQuery();
			User user = query.signIn(conn, login, password);
			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			return user;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Database connection is failed", ex);
		} catch (MySqlException | SQLException ex) {
			throw new DaoException("Can't execute query", ex);
		} finally {
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public User findUserByLogin(String login) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			QueriesUtil query = new MySQLQuery();
			User user = query.findUserByLogin(conn, login);

			if (user == null) {
				throw new DaoException("User wasn't found");
			}
			return user;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException("Database connection is failed", ex);
		} catch (MySqlException | SQLException ex) {
			throw new DaoException("Can't execute query", ex);
		} finally {
			ConnectionUtil.close(conn);
		}
	}

}
