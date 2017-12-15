package by.tr.web.service.impl;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.ServiceException;


public class UserServiceImpl implements UserService {

	private static DaoFactory daoInstance = DaoFactory.getInstance();
	private static UserDao userDao = daoInstance.getUserDao();
	
	
	@Override
	public boolean registrate(User user) throws ServiceException{
		
		if (validateUser(user) == false){
			throw new ServiceException("Incorrect input data");
		}
		
		try {
			return userDao.registrateUser(user);
		} catch(DaoException ex){
			throw new ServiceException("Can't registrate such user", ex);
		}
	}
	
	@Override
	public User signIn(String login, String password) throws ServiceException {
		if (!validate(login) || !validate(password)){
			throw new ServiceException("One field is empty");
		}
		
		User user = null;
		try {
			user = userDao.signIn(login, password);
		} catch(DaoException ex){
			throw new ServiceException("User not found", ex);
		}
		return user;		
	}

	
	@Override
	public User findUserByLogin(String login) throws ServiceException {
		
		if (!validate(login)){
			throw new ServiceException("Login is empty");
		}
		
		User user = null;
		
		try {
			user = userDao.findUserByLogin(login);
		} catch(DaoException ex){
			throw new ServiceException("User not found", ex);
		}
		return user;
	}
	
	
	private static boolean validate(String login){
		login = login.trim();
		if(login.isEmpty()){
			return false;
		}
		return true;
	}
	
	private static boolean validateUser(User user){

		if (validate(user.getSurname()) == false){
			return false;
		}
		else if(validate(user.getName()) == false){
			return false;
		}
		else if(validate(user.getLogin()) == false){
			return false;
		}
		else if(validate(user.getPassword()) == false){
			return false;
		}

		return true;
	}
	

	
}
