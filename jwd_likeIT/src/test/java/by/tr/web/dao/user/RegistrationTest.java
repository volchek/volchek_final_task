package by.tr.web.dao.user;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.DaoLoginException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.dao.user.UserDao;
import by.tr.web.entity.User;
import by.tr.web.service.application.InitializeService;
import by.tr.web.service.exception.common.FatalServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class RegistrationTest {

	private static User user;
	private static DaoFactory daoInstance = DaoFactory.getInstance();
	private static UserDao userDao = daoInstance.getUserDao();
	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();

	@BeforeClass
	public static void createUser() throws FatalServiceException, DaoException {
		
		initializeService.initializeApplication();
		
		user = new User();
		user.setLogin("Zayatc");
		user.setPassword("abacabaV1");
		user.setName("Петр");
		user.setSurname("Иванов");
		user.addEmail("Ivanov@mail.ru");
		user.setStatus("student");
		user.setBirthday(13, Calendar.JANUARY, 1995);
		user.setAdmin(false);
		user.setAvatar(null);
		user.setBanned(false);
		user.addLanguage("C", 2);
		user.addLanguage("C++", 3);
		user.addLanguage("Python", 4);
		user.addLanguage("SQL", 1);
	}

	@AfterClass
	public static void closeConnection() {
		initializeService.stopApplication();
	}

	@Test(expected = DaoLoginException.class)
	public void shouldCheckExistedLogin() throws DaoException, FatalDaoException {
		userDao.registerUser(user);
	}

	@Test(expected = DaoLoginException.class)
	public void shouldCheckExistedLogin_2() throws DaoException, FatalDaoException {
		user.setLogin("Tzar");
		userDao.registerUser(user);
	}

	@Ignore("don't want insert many new users")
	@Test
	public void shouldInsertUser() throws DaoException, FatalDaoException {

		String login = "user";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		login += timestamp.getTime();
		user.setLogin(login);

		userDao.registerUser(user);

		User newUser = userDao.findUserByLogin(login);

		assertTrue(user.equals(newUser));
	}
	
}
