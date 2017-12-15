package by.tr.web.dao.impl;

import by.tr.web.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.impl.MySqlUtils.ConnectionUtils;
import by.tr.web.dao.impl.MySqlUtils.MySQLQueries;
import by.tr.web.dao.impl.MySqlUtils.QueriesUtils;


public class MySQLUserDaoImpl implements UserDao {	

	@Override
	public boolean registrateUser(User user) throws DaoException {

		Connection conn = null;
		try {	
			conn = ConnectionUtils.getConnection();
			QueriesUtils query = new MySQLQueries();
			query.registrateUser(conn, user);
			return true;
			
		}  catch(ClassNotFoundException ex){
			throw new DaoException("Class not found", ex);
		}
		catch(SQLException ex){
			throw new DaoException("Sql error: can't registrate such user", ex);
		}
		finally {
			ConnectionUtils.close(conn);
		}
	}
	

	@Override
	public User signIn(String login, String password) throws DaoException {

		Connection conn = null;
		try {	
			conn = ConnectionUtils.getConnection();
			QueriesUtils query = new MySQLQueries();
			User user = query.signIn(conn, login, password);
			if (user == null){
				throw new DaoException("User wasn't found");
			}
			return user;
			
		}  catch(ClassNotFoundException ex){
			throw new DaoException("Class not found", ex);
		}
		catch(SQLException ex){
			throw new DaoException("Sql error: can't find such user", ex);
		}
		finally {
			ConnectionUtils.close(conn);
		}
	}

	
	@Override
	public User findUserByLogin(String login) throws DaoException {
		
		Connection conn = null;
		try {	
			conn = ConnectionUtils.getConnection();
			QueriesUtils query = new MySQLQueries();
			User user = query.findUserByLogin(conn, login);

			if (user == null){
				throw new DaoException("NOT FOUND");
			}
			return user;
		}  catch(ClassNotFoundException ex){
			throw new DaoException("Class not found", ex);
		}
		catch(SQLException ex){
			throw new DaoException("SQL error", ex);
		}
		finally {
			ConnectionUtils.close(conn);
		}
	}
	
}
