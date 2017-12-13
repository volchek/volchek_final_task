package by.tr.web.service.factory;

import by.tr.web.service.UserService;
import by.tr.web.service.impl.UserServiceImpl;


public final class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();

	private final UserService userService = new UserServiceImpl();
	
	
	public UserService getUserService() {
		return userService;
	}

	public static ServiceFactory getInstance() {
		return instance;
	}	

	private ServiceFactory() {}

}
