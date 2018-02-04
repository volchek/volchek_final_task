package by.tr.web.service.impl;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.question.QuestionDao;
import by.tr.web.entity.text.Question;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.common.ServiceException;
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
			throw new ServiceException("Failed to add a new question", ex);
		}
	}

	@Override
	public Question editQuestion(int questionId, int authorId, String oldText, String newText) throws ServiceException {

		Validator.validateText(newText);

		DaoFactory daoFactory = DaoFactory.getInstance();
		QuestionDao questionDao = daoFactory.getQuestionDao();

		try {
			Question question = questionDao.editQuestion(questionId, authorId, oldText, newText);
			return question;
			
		} catch (DaoException ex) {
			throw new ServiceException("Failed to edit a question", ex);
		}
	}

	@Override
	public boolean deleteQuestion(int questionId, int authorId) throws ServiceException {

		DaoFactory daoFactory = DaoFactory.getInstance();
		QuestionDao questionDao = daoFactory.getQuestionDao();

		try {
			return questionDao.deleteQuestion(questionId, authorId);
		} catch (DaoException ex) {
			throw new ServiceException("Failed to delete a question", ex);
		}
	}

	@Override
	public Question findQuestionById(String questionId) throws ServiceException {

		int id = Validator.validateId(questionId);

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			Question result = questionDao.findQuestionById(id);
			return result;
			
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find a question", ex);
		}
	}

	@Override
	public List<Question> showLastQuestions() throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			List<Question> result = questionDao.showLastQuestions();
			return result;
			
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find the question list", ex);
		}
	}

	@Override
	public List<Question> showLastQuestionsForRegisteredUser(int userId) throws ServiceException {

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			List<Question> result = questionDao.showLastQuestionsForRegisteredUser(userId);
			return result;
			
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find the question list", ex);
		}
	}

	@Override
	public List<Question> findQuestionByLanguage(List<String> languages) throws ServiceException {

		Validator.validateLanguages(languages);

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			List<Question> result = questionDao.findQuestionByLanguage(languages);
			return result;
			
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find the questions using a language list", ex);
		}
	}

	@Override
	public List<Question> findQuestionByTag(List<String> tags) throws ServiceException {

		Validator.validateTags(tags);

		DaoFactory daoInstance = DaoFactory.getInstance();
		QuestionDao questionDao = daoInstance.getQuestionDao();

		try {
			List<Question> result = questionDao.findQuestionByTag(tags);
			return result;
			
		} catch (DaoException ex) {
			throw new ServiceException("Failed to find the questions using a tag list", ex);
		}
	}

}
