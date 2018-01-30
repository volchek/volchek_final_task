package by.tr.web.service.answer.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.answer.AnswerDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.Text;
import by.tr.web.service.answer.AnswerService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text_exception.TextException;
import by.tr.web.service.validator.Validator;

public class AnswerServiceImpl implements AnswerService {

	private static final Logger logger = LogManager.getLogger(AnswerServiceImpl.class);

	@Override
	public Text addAnswer(int questionId, String text, int userId) throws ServiceException {

		if (Validator.isEmpty(text)) {
			logger.error("Answer text is empty");
			throw new TextException("Answer text is empty");
		}

		DaoFactory daoFactory = DaoFactory.getInstance();
		AnswerDao answerDao = daoFactory.getAnswerDao();

		try {
			Text result = answerDao.addAnswer(questionId, text, userId);
			return result;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Answer findAnswerById(int answerId) throws ServiceException {

		DaoFactory daoFactory = DaoFactory.getInstance();
		AnswerDao answerDao = daoFactory.getAnswerDao();

		try {
			Answer answer = answerDao.findAnswerById(answerId);
			return answer;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Question editAnswer(String answerId, int userId, String text) throws ServiceException {

		if (Validator.isEmpty(text)) {
			logger.error("Answer text is empty");
			throw new TextException("Answer text is empty");
		}
		
		Validator.validateId(answerId);
		int iAnswerId = Integer.parseInt(answerId);

		DaoFactory daoFactory = DaoFactory.getInstance();
		AnswerDao answerDao = daoFactory.getAnswerDao();
		
		try {
			Question question = answerDao.editAnswer(iAnswerId, userId, text);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public boolean deleteAnswer(int answerId, int userId) throws ServiceException {

		DaoFactory daoFactory = DaoFactory.getInstance();
		AnswerDao answerDao = daoFactory.getAnswerDao();

		try {
			return answerDao.deleteAnswer(answerId, userId);
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public boolean checkIsAnswerCorrect(String answerId) throws ServiceException {

		Validator.validateId(answerId);
		return true;

	}

}
