package by.tr.web.service.impl;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.exception.FatalServiceException;

public class UserServiceImpl implements UserService {

	@Override
	public boolean register(User user) throws ServiceException, FatalServiceException {

		if (!validateUser(user)) {
			throw new ServiceException("Incorrect input data");
		}
		
		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		try {
			return userDao.registerUser(user);
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex){
			throw new FatalServiceException(ex);
		}		
	}

	@Override
	public User signIn(String login, String password) throws ServiceException, FatalServiceException {

		if (!validate(login) || !validate(password)) {
			throw new ServiceException("One field is empty");
		}

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		User user = null;
		try {
			user = userDao.signIn(login, password);
			return user;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex){
			throw new FatalServiceException(ex);
		}
	}

	@Override
	public User findUserByLogin(String login) throws ServiceException, FatalServiceException {

		if (!validate(login)) {
			throw new ServiceException("Login is empty");
		}

		DaoFactory daoInstance = DaoFactory.getInstance();
		UserDao userDao = daoInstance.getUserDao();

		User user = null;
		try {
			user = userDao.findUserByLogin(login);
			return user;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex){
			throw new FatalServiceException(ex);
		}

	}

	private static boolean validate(String login) {
		login = login.trim();
		if (login.isEmpty()) {
			return false;
		}
		return true;
	}

	private static boolean validateUser(User user) {

		if (validate(user.getSurname()) == false) {
			return false;
		} else if (validate(user.getName()) == false) {
			return false;
		} else if (validate(user.getLogin()) == false) {
			return false;
		} else if (validate(user.getPassword()) == false) {
			return false;
		}

		return true;
	}

}