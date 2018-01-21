package by.tr.web.service.text.impl;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.text.CommonTextDao;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.text.CommonTextService;

public class CommonTextServiceImpl implements CommonTextService {

	@Override
	public Question evaluateText(int userId, int textId, int mark, TextType textType) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		CommonTextDao textDao = daoInstance.getCommonTextDao();

		try {
			Question question = textDao.evaluateText(userId, textId, mark, textType);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Question deleteTextMark(int userId, int textId, TextType textType) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		CommonTextDao textDao = daoInstance.getCommonTextDao();

		try {
			Question question = textDao.deleteTextMark(userId, textId, textType);
			return question;

		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}
	
}
