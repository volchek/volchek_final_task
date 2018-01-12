package by.tr.web.service.impl;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.impl.util.Validator;
import by.tr.web.service.exception.FatalServiceException;

public class UserServiceImpl implements UserService {

	@Override
	public boolean register(User user) throws ServiceException, FatalServiceException {

		Validator.validateUser(user);

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		try {
			return userDao.registerUser(user);
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

	@Override
	public User signIn(String login, String password) throws ServiceException, FatalServiceException {

		Validator.isEmpty(login);
		Validator.isEmpty(password);

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		User user = null;
		try {
			user = userDao.signIn(login, password);
			return user;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

	@Override
	public User findUserByLogin(String login) throws ServiceException, FatalServiceException {

		if (!Validator.validateLogin(login)) {
			return null;
		}

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		User user = null;
		try {
			user = userDao.findUserByLogin(login);
			return user;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

	@Override
	public boolean updatePersonalInfo(User user, User modifiedUser) throws ServiceException, FatalServiceException {

		Validator.validatePersonalData(user);
		
		try {
			return updateUser(user, modifiedUser);
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

	private boolean updateUser(User firstUser, User secondUser) throws DaoException, FatalDaoException {

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