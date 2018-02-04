package by.tr.web.dao;

import by.tr.web.dao.exception.DaoException;

public interface TagDao {

	void extractAllTagInfo() throws DaoException;

	void addTag(String tag) throws DaoException;

}
