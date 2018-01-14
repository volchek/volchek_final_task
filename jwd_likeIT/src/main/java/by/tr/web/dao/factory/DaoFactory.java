package by.tr.web.dao.factory;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.QuestionDao;
import by.tr.web.dao.TagDao;
import by.tr.web.dao.UserDao;
import by.tr.web.dao.impl.MySQLDatabaseDaoImpl;
import by.tr.web.dao.impl.MySQLLanguageDaoImpl;
import by.tr.web.dao.impl.MySQLUserDaoImpl;
import by.tr.web.dao.impl.MySQLQuestionDaoImpl;
import by.tr.web.dao.impl.MySQLTagDaoImpl;

public class DaoFactory {

	private static final DaoFactory instance = new DaoFactory();

	private static final DatabaseDao dbDao = new MySQLDatabaseDaoImpl();
	private static final UserDao userDao = new MySQLUserDaoImpl();
	private static final LanguageDao langDao = new MySQLLanguageDaoImpl();
	private static final TagDao tagDao = new MySQLTagDaoImpl();
	private static final QuestionDao questionDao = new MySQLQuestionDaoImpl();

	private DaoFactory() {
	
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public DatabaseDao getDatabaseDao() {
		return dbDao;
	}

	public LanguageDao getLanguageDao() {
		return langDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

}
