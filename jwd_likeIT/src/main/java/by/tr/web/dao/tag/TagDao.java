package by.tr.web.dao.tag;

import by.tr.web.dao.exception.DaoException;

public interface TagDao {

	void extractAllTagInfo() throws DaoException;

	void addTag(String tag) throws DaoException;

}
