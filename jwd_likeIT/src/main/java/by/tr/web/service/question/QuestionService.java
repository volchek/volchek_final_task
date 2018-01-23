package by.tr.web.service.question;

import java.util.List;

import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;

public interface QuestionService {

	Question addQuestion(int authorId, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException;

	Question findQuestionById(String questionId) throws ServiceException;

	List<Question> showLastQuestions() throws ServiceException;

	List<Question> showLastQuestionsForRegisteredUser(int userId) throws ServiceException;

	List<Question> findQuestionByLanguage(List<String> languages) throws ServiceException;

	List<Question> findQuestionByTag(List<String> tags) throws ServiceException;
	
}
