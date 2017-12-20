package by.tr.web.service.impl;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.service.InitializeService;
import by.tr.web.service.exception.FatalServiceException;

public class InitializeServiceImpl implements InitializeService {

	private static DaoFactory daoInstance = DaoFactory.getInstance();
	private static DatabaseDao dbInstance = daoInstance.getDatabaseDao();

	@Override
	public void initializeApplication() throws FatalServiceException {
		try {
			dbInstance.initConnectionPool();
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

	@Override
	public void stopApplication() {
		dbInstance.clearConnectionPool();
	}

}
