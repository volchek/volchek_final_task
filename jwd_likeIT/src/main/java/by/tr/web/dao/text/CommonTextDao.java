package by.tr.web.dao.text;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;

public interface CommonTextDao {
	
	Question evaluateText(int userId, int textId, int mark, TextType textType) throws DaoException;

	Question deleteTextMark(int userId, int textId, TextType textType) throws DaoException;
	
	List<Question> showUserTexts(int userId, TextType textType) throws DaoException;

}
