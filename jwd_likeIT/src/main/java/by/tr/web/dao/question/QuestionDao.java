package by.tr.web.dao.question;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Question;
import by.tr.web.entity.Text;

public interface QuestionDao {

	Question addQuestion(int authorID, String title, List<String> languages, List<String> tags, String text)
			throws DaoException;
	
	List<Text> evaluateQuestion(int userId, int questionId, int mark) throws DaoException;

}
