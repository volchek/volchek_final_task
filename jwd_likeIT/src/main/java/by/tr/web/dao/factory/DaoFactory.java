package by.tr.web.dao.factory;

import by.tr.web.dao.answer.AnswerDao;
import by.tr.web.dao.answer.impl.MySQLAnswerDaoImpl;
import by.tr.web.dao.database.DatabaseDao;
import by.tr.web.dao.database.impl.MySQLDatabaseDaoImpl;
import by.tr.web.dao.language.LanguageDao;
import by.tr.web.dao.language.impl.MySQLLanguageDaoImpl;
import by.tr.web.dao.user.UserDao;
import by.tr.web.dao.user.impl.MySQLUserDaoImpl;
import by.tr.web.dao.question.QuestionDao;
import by.tr.web.dao.question.impl.MySQLQuestionDaoImpl;
import by.tr.web.dao.tag.TagDao;
import by.tr.web.dao.tag.impl.MySQLTagDaoImpl;
import by.tr.web.dao.text.CommonTextDao;
import by.tr.web.dao.text.impl.MySQLCommonTextDaoImpl;

public class DaoFactory {

	private static final DaoFactory instance = new DaoFactory();

	private static final DatabaseDao dbDao = new MySQLDatabaseDaoImpl();
	private static final UserDao userDao = new MySQLUserDaoImpl();
	private static final LanguageDao langDao = new MySQLLanguageDaoImpl();
	private static final TagDao tagDao = new MySQLTagDaoImpl();
	private static final QuestionDao questionDao = new MySQLQuestionDaoImpl();
	private static final AnswerDao answerDao = new MySQLAnswerDaoImpl();
	private static final CommonTextDao textDao = new MySQLCommonTextDaoImpl();

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

	public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public CommonTextDao getCommonTextDao() {
		return textDao;
	}

}
