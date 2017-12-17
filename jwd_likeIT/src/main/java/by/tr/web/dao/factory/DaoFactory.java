package by.tr.web.dao.factory;

import by.tr.web.dao.UserDao;
import by.tr.web.dao.impl.MySQLUserDaoImpl;

public class DaoFactory {

	private static final DaoFactory instance = new DaoFactory();

	private final UserDao userDao = new MySQLUserDaoImpl();

	private DaoFactory() {
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public static DaoFactory getInstance() {
		return instance;
	}
}
