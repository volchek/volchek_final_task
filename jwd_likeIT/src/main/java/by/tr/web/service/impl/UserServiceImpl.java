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

		if (!Validator.validateUser(user)) {
			// Add logging
		}

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

		if (!Validator.validateLogin(login) || !Validator.validatePassword(password)) {
			// add logging
		}

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
			// Add logging
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

}