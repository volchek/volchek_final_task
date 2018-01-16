package by.tr.web.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.service.InitializeService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class ControllerCreationListener implements ServletContextListener {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			initializeService.initializeApplication();
		} catch (FatalServiceException | DaoException ex) {
			System.err.println("Stop application");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		initializeService.stopApplication();
	}

}
