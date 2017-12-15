package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.User;


public interface UserDao {

	boolean registrateUser(User user) throws DaoException;

	User signIn(String login, String password) throws DaoException;
	
	User findUserByLogin(String login) throws DaoException;

}
