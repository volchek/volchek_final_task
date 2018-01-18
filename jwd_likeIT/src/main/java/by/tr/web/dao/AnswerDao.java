package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Text;

public interface AnswerDao {

	Text addAnswer(int questionId, String text, int userId) throws DaoException;

}
