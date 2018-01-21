package by.tr.web.service.question;

import java.util.List;

import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;

public interface QuestionService {

	Question addQuestion(int authorId, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException;

	Question findQuestionById(int questionId) throws ServiceException;
	
}
