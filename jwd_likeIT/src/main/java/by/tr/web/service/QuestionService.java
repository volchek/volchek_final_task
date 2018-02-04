package by.tr.web.service;

import java.util.List;

import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;

public interface QuestionService {

	Question addQuestion(int authorId, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException;

	Question editQuestion(int questionId, int authorId, String oldText, String newText) throws ServiceException;
	
	boolean deleteQuestion(int questionId, int authorId) throws ServiceException;

	Question findQuestionById(String questionId) throws ServiceException;

	List<Question> findQuestionByLanguage(List<String> languages) throws ServiceException;

	List<Question> findQuestionByTag(List<String> tags) throws ServiceException;

	List<Question> showLastQuestions() throws ServiceException;

	List<Question> showLastQuestionsForRegisteredUser(int userId) throws ServiceException;

}
