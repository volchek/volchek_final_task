package by.tr.web.service.impl;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.user.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.user.LoginException;
import by.tr.web.service.validator.Validator;

public class UserServiceImpl implements UserService {

	@Override
	public boolean register(User user) throws ServiceException {

		Validator.validateUser(user);

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		try {
			return userDao.registerUser(user);
		} catch (DaoException ex) {
			throw new ServiceException("Failed to add a new user", ex);
		}
	}

	@Override
	public User signIn(String login, String password) throws ServiceException {

		if (Validator.isEmpty(login) || Validator.isEmpty(password)){
			throw new LoginException("Input data is incorrect");
		}

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		try {
			return userDao.signIn(login, password);
		} catch (DaoException ex) {
			throw new ServiceException("Failed to authenticate a user", ex);
		}
	}

	@Override
	public User findUserByLogin(String login) throws ServiceException {

		Validator.validateLogin(login);

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		try {
			return userDao.findUserByLogin(login);
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find a user", ex);
		}
	}

	@Override
	public boolean updatePersonalInfo(User user, User modifiedUser) throws ServiceException {

		Validator.validatePersonalData(user);

		try {
			return updateUser(user, modifiedUser);
		} catch (DaoException ex) {
			throw new ServiceException("Failed to update user data", ex);
		}
	}

	@Override
	public void banUser(int userId, boolean ban) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		try {
			userDao.banUser(userId, ban);
		} catch (DaoException ex) {
			throw new ServiceException("Failed to ban / unban a user", ex);
		}
	}

	/**
	 * Update user. If old and new data is equal return false. If personal data
	 * is changed, call method to update only user personal data. If language
	 * list is changed, call method to update only information about the user
	 * languages. Else call the method to update the whole user object.
	 *
	 * @param firstUser is old information about the user
	 * @param secondUser is new information about the user
	 * @return true, if successful
	 * @throws DaoException is the DaoException
	 */
	private boolean updateUser(User firstUser, User secondUser) throws DaoException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		if (!Validator.personalDataEqual(firstUser, secondUser)) {
			if (!Validator.userLanguagesEqual(firstUser, secondUser)) {
				return userDao.updateUser(firstUser, secondUser);
			} else {
				return userDao.updatePersonalInfo(firstUser, secondUser);
			}
		} else if (!Validator.userLanguagesEqual(firstUser, secondUser)) {
			return userDao.updateUserLanguages(firstUser, secondUser);
		}
		return false;
	}

}