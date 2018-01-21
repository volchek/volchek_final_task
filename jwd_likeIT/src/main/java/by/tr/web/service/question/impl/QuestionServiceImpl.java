package by.tr.web.service.question.impl;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.question.QuestionDao;
import by.tr.web.entity.Question;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.question.QuestionService;
import by.tr.web.service.validator.Validator;

public class QuestionServiceImpl implements QuestionService {

	@Override
	public Question addQuestion(int authorId, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException {

		Validator.validateQuestion(title, text, languages, tags);

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			Question question = questionDao.addQuestion(authorId, title, languages, tags, text);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Question evaluateQuestion(int userId, int questionId, int mark) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			Question textList = questionDao.evaluateQuestion(userId, questionId, mark);
			return textList;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Question deleteQuestionMark(int userId, int questionId) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			Question result = questionDao.deleteQuestionMark(userId, questionId);
			return result;

		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Question findQuestionById(int questionId) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			Question result = questionDao.findQuestionById(questionId);
			return result;

		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

}
