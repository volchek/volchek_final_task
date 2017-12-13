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
	
}
