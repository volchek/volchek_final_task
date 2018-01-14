package by.tr.web.dao;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;

public interface LanguageDao {

	List<String> getLanguageList() throws DaoException, FatalDaoException;
}
