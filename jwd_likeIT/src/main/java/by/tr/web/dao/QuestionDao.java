package by.tr.web.dao;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.entity.Question;
import by.tr.web.entity.language.Language;
import by.tr.web.entity.tag.Tag;

public interface QuestionDao {

	Question addQuestion(int id, String title, List<Language> languages, List<Tag> tags, String text)
			throws DaoException, FatalDaoException;

}
