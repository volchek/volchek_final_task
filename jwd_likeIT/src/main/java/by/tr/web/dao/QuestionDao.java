package by.tr.web.dao;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.entity.Question;

public interface QuestionDao {

	Question addQuestion(int id, String title, List<String> languages, List<String> tags, String text)
			throws DaoException, FatalDaoException;

}
