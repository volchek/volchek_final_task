package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;

public interface TagDao {

	void extractAllTagInfo() throws DaoException, FatalDaoException;

}
