package by.tr.web.service.user;

import by.tr.web.entity.User;
import by.tr.web.service.exception.common.ServiceException;

public interface UserService {

	User findUserByLogin(String login) throws ServiceException;

	boolean register(User user) throws ServiceException;

	User signIn(String login, String password) throws ServiceException;
	
	boolean updatePersonalInfo(User user, User modifiedUser) throws ServiceException;

}
