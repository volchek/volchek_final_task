package by.tr.web.service.impl;

import java.util.List;

import by.tr.web.dao.TagDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.service.TagService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;

public class TagServiceImpl implements TagService {

	@Override
	public List<String> getTagList() throws ServiceException, FatalServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		TagDao questionDao = daoInstance.getTagDao();

		try {
			List<String> tags = questionDao.getTagList();
			return tags;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}
	
}
