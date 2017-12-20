package by.tr.web.service.factory;

import by.tr.web.service.InitializeService;
import by.tr.web.service.UserService;
import by.tr.web.service.impl.InitializeServiceImpl;
import by.tr.web.service.impl.UserServiceImpl;

public final class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();

	private final UserService userService = new UserServiceImpl();

	private final InitializeService initService = new InitializeServiceImpl();

	public UserService getUserService() {
		return userService;
	}

	public InitializeService getInitializeService() {
		return initService;
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	private ServiceFactory() {
	}

}
