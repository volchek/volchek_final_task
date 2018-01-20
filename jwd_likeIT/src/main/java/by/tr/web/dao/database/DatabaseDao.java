package by.tr.web.dao.database;

import by.tr.web.dao.exception.FatalDaoException;

public interface DatabaseDao {

	void initConnectionPool() throws FatalDaoException;

	void clearConnectionPool();

}
