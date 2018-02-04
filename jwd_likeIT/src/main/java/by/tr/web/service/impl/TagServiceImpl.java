package by.tr.web.service.impl;

import java.util.Set;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.tag.TagDao;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;
import by.tr.web.service.TagService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.validator.Validator;

public class TagServiceImpl implements TagService {

	@Override
	public boolean addTag(String tag) throws ServiceException {

		if (Validator.isEmpty(tag) || Validator.isTagExist(tag)) {
			return false;
		}

		DaoFactory daoInstance = DaoFactory.getInstance();
		TagDao tagDao = daoInstance.getTagDao();

		try {
			tagDao.addTag(tag);
			return true;
		} catch (DaoException ex) {
			throw new ServiceException("Failed to add a new tag", ex);
		}
	}

	@Override
	public Set<String> updateTagList() throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		TagDao tagDao = daoInstance.getTagDao();

		try {
			tagDao.extractAllTagInfo();
			TagSet tagSet = TagSetSingleton.getInstance().getTagSet();
			return tagSet.getTagSet();
		} catch (DaoException ex) {
			throw new ServiceException("Failed to update the tag list", ex);
		}
	}

}
