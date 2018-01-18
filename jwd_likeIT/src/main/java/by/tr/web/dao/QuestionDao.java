package by.tr.web.dao;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Question;

public interface QuestionDao {

	Question addQuestion(int authorID, String title, List<String> languages, List<String> tags, String text)
			throws DaoException;

}
