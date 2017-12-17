package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.entity.User;

public interface UserDao {

	boolean registerUser(User user) throws DaoException, FatalDaoException;
	
	User signIn(String login, String password) throws DaoException, FatalDaoException;

	User findUserByLogin(String login) throws DaoException, FatalDaoException;

}
