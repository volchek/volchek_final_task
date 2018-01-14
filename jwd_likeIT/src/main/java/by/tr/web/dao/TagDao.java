package by.tr.web.dao;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;

public interface TagDao {

	List<String> getTagList() throws DaoException, FatalDaoException;
	
}
