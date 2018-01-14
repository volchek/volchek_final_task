package by.tr.web.service.impl;

import java.util.List;

import by.tr.web.dao.QuestionDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.Question;
import by.tr.web.entity.language.Language;
import by.tr.web.entity.tag.Tag;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.impl.util.Validator;

public class QuestionServiceImpl implements QuestionService {

	@Override
	public Question addQuestion(int id, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException, FatalServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		List<Tag> tagList = Validator.validateTags(tags);
		List<Language> langList = Validator.validateLanguages(languages);

			try {
			Question question = questionDao.addQuestion(id, title, langList, tagList, text);
			return question;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		} catch (FatalDaoException ex) {
			throw new FatalServiceException(ex);
		}
	}

}
