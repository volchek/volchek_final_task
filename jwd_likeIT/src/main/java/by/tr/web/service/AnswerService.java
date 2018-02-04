package by.tr.web.service;

import by.tr.web.entity.text.Text;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;

public interface AnswerService {

	Text addAnswer(int questionId, String text, int userId) throws ServiceException;

	Answer findAnswerById(int answerId) throws ServiceException;

	Question editAnswer(String answerId, int userId, String text) throws ServiceException;

	boolean deleteAnswer(int answerId, int userId) throws ServiceException;

	boolean checkIsAnswerCorrect(String answerId) throws ServiceException;

}
