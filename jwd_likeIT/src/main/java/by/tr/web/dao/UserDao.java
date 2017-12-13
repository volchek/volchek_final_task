package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.User;


public interface UserDao {

	void registrateUser(User user) throws DaoException;

	User logIn(String login, String password) throws DaoException;
	
	User findUserByLogin(String login) throws DaoException;

}
