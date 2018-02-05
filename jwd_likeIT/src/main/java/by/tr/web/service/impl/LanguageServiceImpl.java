package by.tr.web.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.service.LanguageService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text.LanguageException;
import by.tr.web.service.validator.Validator;

public class LanguageServiceImpl implements LanguageService {

	private static final Logger logger = LogManager.getLogger(LanguageServiceImpl.class);

	@Override
	public List<String> findFrequentLanguages() throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		LanguageDao languageDao = daoInstance.getLanguageDao();

		try {
			List<String> languages = languageDao.findFrequentLanguages();
			return languages;
		} catch (DaoException ex) {
			throw new ServiceException("Failed to get the language list", ex);
		}
	}

	@Override
	public boolean addLanguage(String language) throws ServiceException {

		if (Validator.isEmpty(language) || Validator.isLanguageExist(language)) {
			logger.error("Can't add a new language '" + language + "'");
			throw new LanguageException("Failed to add a new language");
		}

		DaoFactory daoInstance = DaoFactory.getInstance();
		LanguageDao languageDao = daoInstance.getLanguageDao();

		try {
			languageDao.addLanguage(language);
			return true;
		} catch (DaoException ex) {
			throw new ServiceException("Failed to add a new language", ex);
		}
	}

	@Override
	public Set<String> updateLanguageList() throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		LanguageDao languageDao = daoInstance.getLanguageDao();

		try {
			languageDao.extractAllLanguageInfo();
			LanguageSet langSet = LanguageSetSingleton.getInstance().getLanguageSet();
			return langSet.getLanguageSet();
		} catch (DaoException ex) {
			throw new ServiceException("Failed to update the language list", ex);
		}
	}

}
