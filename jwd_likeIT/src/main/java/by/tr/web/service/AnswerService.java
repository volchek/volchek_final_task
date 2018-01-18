package by.tr.web.service;

import by.tr.web.entity.Text;
import by.tr.web.service.exception.ServiceException;

public interface AnswerService {

	Text addAnswer(int questionId, String text, int userId) throws ServiceException;

}
