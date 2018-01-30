package by.tr.web.dao.answer;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.Text;

public interface AnswerDao {

	Text addAnswer(int questionId, String text, int userId) throws DaoException;

	Answer findAnswerById(int answerId) throws DaoException;

	int findQuestionIdForTheAnswer(int answerId) throws DaoException;

	Question editAnswer(int answerId, int userId, String text) throws DaoException;

	boolean deleteAnswer(int answerId, int userId) throws DaoException;
}
