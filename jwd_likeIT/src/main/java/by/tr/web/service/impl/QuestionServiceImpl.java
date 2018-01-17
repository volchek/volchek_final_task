package by.tr.web.service.impl;

import java.util.List;

import by.tr.web.dao.QuestionDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.Question;
import by.tr.web.entity.User;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.impl.util.Validator;

public class QuestionServiceImpl implements QuestionService {

	@Override
	public Question addQuestion(User author, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException, FatalServiceException {

		Validator.validateLanguages(languages);
		Validator.validateTags(tags);
		
		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			Question question = questionDao.addQuestion(author, title, languages, tags, text);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

}
