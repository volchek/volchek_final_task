package by.tr.web.dao.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.entity.User;
import by.tr.web.entity.language.Language;
import by.tr.web.service.InitializeService;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class UpdateUserTest {

	private static User currentUser;
	private static User modifiedUser;
	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();
	private static UserService userService = serviceFactory.getUserService();

	@BeforeClass
	public static void createUser() throws FatalServiceException {
		initializeService.initializeApplication();

		currentUser = new User();
		currentUser.setId(7);
		currentUser.setSurname("Kacher");
		currentUser.setLogin("kacher_login");
		currentUser.setPassword("abacabaD1");
		currentUser.setName("Ivan");
		currentUser.setBirthday(10, Calendar.JULY, 1991);
		currentUser.setStatus("student");
		currentUser.setAdmin(false);
		currentUser.setAvatar(null);
		currentUser.setBanned(false);
		currentUser.addLanguage(Language.PYTHON, 2);

		modifiedUser = new User();
		modifiedUser.setSurname("Kacher");
		modifiedUser.setName("Ivan");
		modifiedUser.setBirthday(10, Calendar.JULY, 1991);
		modifiedUser.setStatus("student");
		modifiedUser.setAdmin(false);
		modifiedUser.setAvatar(null);
		modifiedUser.setBanned(false);
		modifiedUser.addLanguage(Language.PYTHON, 2);
	}

	@AfterClass
	public static void closeConnection() {
		initializeService.stopApplication();
	}

	@Test
	public void shouldDenyUpdateUser() throws DaoException, FatalDaoException {

		try {
			assertFalse(userService.updatePersonalInfo(currentUser, modifiedUser));
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getSurname().equals(modifiedUser.getSurname()));
		assertTrue(currentUser.getName().equals(modifiedUser.getName()));
		assertTrue(currentUser.getStatus().equals(modifiedUser.getStatus()));
		assertTrue(currentUser.getBirthday().equals(modifiedUser.getBirthday()));
		assertTrue(currentUser.getEmail().equals(modifiedUser.getEmail()));
	}

	@Test
	public void shouldUpdatePersonalInfo() throws DaoException, FatalDaoException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		StringBuilder email = new StringBuilder("user");
		email.append(timestamp.getTime());
		email.append("@tut.by");

		modifiedUser.addEmail(email.toString());
		modifiedUser.addEmail(email.insert(0, "new_").toString());

		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getSurname().equals(modifiedUser.getSurname()));
		assertTrue(currentUser.getName().equals(modifiedUser.getName()));
		assertTrue(currentUser.getStatus().equals(modifiedUser.getStatus()));
		assertTrue(currentUser.getBirthday().equals(modifiedUser.getBirthday()));
		assertTrue(currentUser.getEmail().equals(modifiedUser.getEmail()));
	}

	@Test
	public void shouldUpdateLanguages() throws DaoException, FatalDaoException {

		modifiedUser.addLanguage(Language.C, 1);
		modifiedUser.getLanguages().remove(Language.PYTHON);
		modifiedUser.addLanguage(Language.PYTHON, 4);

		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getLanguages().equals(modifiedUser.getLanguages()));

		modifiedUser.getLanguages().remove(Language.PYTHON);
		modifiedUser.addLanguage(Language.PYTHON, 2);
		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getLanguages().equals(modifiedUser.getLanguages()));

		modifiedUser.getLanguages().remove(Language.C);
		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getLanguages().equals(modifiedUser.getLanguages()));
	}

	@Test
	public void shouldUpdateUser() throws DaoException, FatalDaoException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		StringBuilder avatar = new StringBuilder("avatar");
		avatar.append(timestamp.getTime());
		avatar.append("@tut.by");
		modifiedUser.setAvatar(avatar.toString());
		modifiedUser.addLanguage(Language.C, 1);

		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getLanguages().equals(modifiedUser.getLanguages()));

		modifiedUser.getLanguages().remove(Language.C);
		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
		} catch (ServiceException | FatalServiceException e) {
			e.printStackTrace();
		}
		assertTrue(currentUser.getLanguages().equals(modifiedUser.getLanguages()));
	}

}
