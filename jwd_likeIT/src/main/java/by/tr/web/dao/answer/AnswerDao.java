package by.tr.web.dao.answer;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Text;

public interface AnswerDao {

	Text addAnswer(int questionId, String text, int userId) throws DaoException;
	
	List<Text> evaluateAnswer(int userId, int answerId, int mark) throws DaoException;

}
