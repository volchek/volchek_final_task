package by.tr.web.service.impl;

import java.util.List;

import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.service.LanguageService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;

public class LanguageServiceImpl implements LanguageService {

	@Override
	public List<String> getLanguageList() throws ServiceException, FatalServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		LanguageDao langDao = daoInstance.getLanguageDao();

		try {
			List<String> languages = langDao.getLanguageList();
			return languages;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

}
