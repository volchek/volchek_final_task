package by.tr.web.service.impl;

import java.util.List;

import by.tr.web.service.CommonTextService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.text.CommonTextDao;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;

public class CommonTextServiceImpl implements CommonTextService {

	@Override
	public Question evaluateText(int userId, int textId, int mark, TextType textType) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		CommonTextDao textDao = daoInstance.getCommonTextDao();

		try {
			Question question = textDao.evaluateText(userId, textId, mark, textType);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException("Failed to evaluate user text", ex);
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
			throw new ServiceException("Failed to delete user mark", ex);
		}
	}

	@Override
	public List<Question> showUserTexts(int userId, TextType textType) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		CommonTextDao textDao = daoInstance.getCommonTextDao();

		try {
			List<Question> question = textDao.showUserTexts(userId, textType);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find user texts", ex);
		}
	}

}
