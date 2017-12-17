package by.tr.web.service;

import by.tr.web.service.exception.ServiceException;
import by.tr.web.entity.User;

public interface UserService {

	User findUserByLogin(String login) throws ServiceException;

	boolean registrate(User user) throws ServiceException;

	User signIn(String login, String password) throws ServiceException;

}
