package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.user.User;

public interface UserDao {

	boolean registerUser(User user) throws DaoException;

	User signIn(String login, String password) throws DaoException;

	boolean updatePersonalInfo(User currentUser, User modifiedUser) throws DaoException;

	boolean updateUserLanguages(User currentUser, User modifiedUser) throws DaoException;

	boolean updateUser(User currentUser, User modifiedUser) throws DaoException;

	User findUserByLogin(String login) throws DaoException;
	
	void banUser(int userId, boolean ban) throws DaoException;
	
}
