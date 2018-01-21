package by.tr.web.service.answer;

import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Text;
import by.tr.web.service.exception.common.ServiceException;

public interface AnswerService {

	Text addAnswer(int questionId, String text, int userId) throws ServiceException;
	
	Answer findAnswerById(int answerId) throws ServiceException;

}
