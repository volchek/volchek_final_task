package by.tr.web.service.impl;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.TagDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;
import by.tr.web.service.TagService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text.TagException;
import by.tr.web.service.validator.Validator;

public class TagServiceImpl implements TagService {

	private static final Logger logger = LogManager.getLogger(TagServiceImpl.class);

	@Override
	public boolean addTag(String tag) throws ServiceException {

		if (Validator.isEmpty(tag) || Validator.isTagExist(tag)) {
			logger.error("Can't add a new tag '" + tag + "'");
			throw new TagException("Failed to add a new tag");
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
