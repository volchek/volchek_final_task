package by.tr.web.service.answer;

import java.util.List;

import by.tr.web.entity.Text;
import by.tr.web.service.exception.common.ServiceException;

public interface AnswerService {

	Text addAnswer(int questionId, String text, int userId) throws ServiceException;

	List<Text> evaluateAnswer(int userId, int answerId, int mark) throws ServiceException;

}
