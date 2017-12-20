package by.tr.web.dao.factory;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.UserDao;
import by.tr.web.dao.impl.DatabaseDaoImpl;
import by.tr.web.dao.impl.MySQLUserDaoImpl;

public class DaoFactory {

	private static final DaoFactory instance = new DaoFactory();

	private static final UserDao userDao = new MySQLUserDaoImpl();

	private static final DatabaseDao dbDao = new DatabaseDaoImpl();

	private DaoFactory() {
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public DatabaseDao getDatabaseDao() {
		return dbDao;
	}

	public static DaoFactory getInstance() {
		return instance;
	}
}
