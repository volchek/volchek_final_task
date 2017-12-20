package by.tr.web.service.impl;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.service.InitializeService;

public class InitializeServiceImpl implements InitializeService {

	private static DaoFactory daoInstance = DaoFactory.getInstance();
	private static DatabaseDao dbInstance = daoInstance.getDatabaseDao();

	@Override
	public void initializeApplication() {
		dbInstance.initConnectionPool();
	}

	@Override
	public void stopApplication() {
		dbInstance.clearConnectionPool();
	}
	
}
