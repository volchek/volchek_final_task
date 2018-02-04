package by.tr.web.service;

import by.tr.web.entity.user.User;
import by.tr.web.service.exception.common.ServiceException;

public interface UserService {

	User findUserByLogin(String login) throws ServiceException;

	boolean register(User user) throws ServiceException;

	User signIn(String login, String password) throws ServiceException;

	boolean updatePersonalInfo(User user, User modifiedUser) throws ServiceException;

	void banUser(int userId, boolean ban) throws ServiceException;

}
