package by.tr.web.service.application.impl;

import by.tr.web.dao.database.DatabaseDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.language.LanguageDao;
import by.tr.web.dao.tag.TagDao;
import by.tr.web.service.application.InitializeService;
import by.tr.web.service.exception.common.FatalServiceException;

public class InitializeServiceImpl implements InitializeService {

	private static DaoFactory daoInstance = DaoFactory.getInstance();
	private static DatabaseDao dbInstance = daoInstance.getDatabaseDao();
	private static TagDao tagInstance = daoInstance.getTagDao();
	private static LanguageDao languageDao = daoInstance.getLanguageDao();

	@Override
	public void initializeApplication() throws FatalServiceException, DaoException {
		try {
			dbInstance.initConnectionPool();
			tagInstance.extractAllTagInfo();
			languageDao.extractAllLanguageInfo();
			
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

	@Override
	public void stopApplication() {
		dbInstance.clearConnectionPool();
	}

}
