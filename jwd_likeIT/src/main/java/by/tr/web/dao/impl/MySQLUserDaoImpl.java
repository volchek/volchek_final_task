package by.tr.web.dao.impl;

import by.tr.web.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.DaoExceptionMessage;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.ConnectionUtil;
import by.tr.web.dao.impl.mysql_util.MySQLQueries;
import by.tr.web.dao.impl.mysql_util.QueriesUtils;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;

public class MySQLUserDaoImpl implements UserDao {

	@Override
	public boolean registerUser(User user) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			QueriesUtils query = new MySQLQueries();
			query.registerUser(conn, user);
			return true;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException(DaoExceptionMessage.DATABASE_FATAL_ERROR, ex);
		} catch (MySqlException | SQLException ex) {
			throw new DaoException(DaoExceptionMessage.REGISTRATION_ERROR, ex);
		} finally {
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public User signIn(String login, String password) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			QueriesUtils query = new MySQLQueries();
			User user = query.signIn(conn, login, password);
			if (user == null) {
				throw new DaoException(DaoExceptionMessage.SERCHING_ERROR);
			}
			return user;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException(DaoExceptionMessage.DATABASE_FATAL_ERROR, ex);
		} catch (MySqlException | SQLException ex) {
			throw new DaoException(DaoExceptionMessage.QUERY_ERROR, ex);
		} finally {
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public User findUserByLogin(String login) throws DaoException, FatalDaoException {

		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			QueriesUtils query = new MySQLQueries();
			User user = query.findUserByLogin(conn, login);

			if (user == null) {
				throw new DaoException(DaoExceptionMessage.SERCHING_ERROR);
			}
			return user;
		} catch (MySqlFatalException ex) {
			throw new FatalDaoException(DaoExceptionMessage.DATABASE_FATAL_ERROR, ex);
		} catch (MySqlException | SQLException ex) {
			throw new DaoException(DaoExceptionMessage.QUERY_ERROR, ex);
		} finally {
			ConnectionUtil.close(conn);
		}
	}

}
