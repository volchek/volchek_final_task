package by.tr.web.service.validator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import by.tr.web.dao.DatabaseDao;
import by.tr.web.dao.LanguageDao;
import by.tr.web.dao.TagDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.factory.DaoFactory;
import by.tr.web.entity.user.User;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text.LanguageException;
import by.tr.web.service.exception.text.TagException;
import by.tr.web.service.exception.text.TitleException;
import by.tr.web.service.exception.user.DateException;
import by.tr.web.service.exception.user.EmailException;
import by.tr.web.service.exception.user.LoginException;
import by.tr.web.service.exception.user.NameException;
import by.tr.web.service.exception.user.PasswordException;
import by.tr.web.service.validator.Validator;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class ValidatorTest {

	private static DaoFactory daoInstance = DaoFactory.getInstance();
	private static DatabaseDao dbDao = daoInstance.getDatabaseDao();
	private static TagDao tagDao = daoInstance.getTagDao();
	private static LanguageDao langDao = daoInstance.getLanguageDao();

	@BeforeClass
	public static void startApplication() throws FatalDaoException, DaoException {
		dbDao.initConnectionPool();
		tagDao.extractAllTagInfo();
		langDao.extractAllLanguageInfo();
	}

	@AfterClass
	public static void stopDatabase() throws FatalDaoException {
		dbDao.clearConnectionPool();
	}

	/* Check name and surname */

	@Test(expected = NameException.class)
	public void shouldCheckEmptyName() throws NameException {
		String login = "";
		Validator.validateNameAndSurname(login);
	}

	@Test(expected = NameException.class)
	public void shouldCheckNameFromTabsAndSpaces() throws NameException {
		String name = "		     ";
		Validator.validateNameAndSurname(name);
	}

	@Test
	public void shouldCheckShortName() throws NameException {
		String name = "a";
		assertTrue(Validator.validateNameAndSurname(name));
	}

	@Test(expected = NameException.class)
	public void shouldCheckNumericName() throws NameException {
		String name = "user1";
		Validator.validateNameAndSurname(name);
	}

	@Test
	public void shouldCheckCorrectName() throws NameException {
		List<String> names = new LinkedList<String>();
		names.add("Ivanov");
		names.add("Иванов");
		names.add("Эрих Мария");
		names.add("Alex");
		for (String name : names) {
			assertTrue(Validator.validateNameAndSurname(name));
		}
	}

	/* Check login */

	@Test(expected = LoginException.class)
	public void shouldCheckEmptyLogin() throws LoginException {
		String login = "";
		Validator.validateLogin(login);
	}

	@Test(expected = LoginException.class)
	public void shouldCheckLoginFromSpace() throws LoginException {
		String login = "     ";
		Validator.validateLogin(login);
	}

	@Test(expected = LoginException.class)
	public void shouldCheckShortLogin() throws LoginException {
		String login = "aaa";
		Validator.validateLogin(login);
	}

	@Test(expected = LoginException.class)
	public void shouldCheckNumericLogin() throws LoginException {
		String login = "123123";
		Validator.validateLogin(login);
	}

	@Test
	public void shouldCheckCorrectLogin() throws LoginException {
		List<String> logins = new LinkedList<String>();
		logins.add("user1");
		logins.add("user_123");
		logins.add("user_Avva");
		logins.add("Alex");
		logins.add("Zayatc");
		logins.add("newUser__1");
		for (String login : logins) {
			assertTrue(Validator.validateLogin(login));
		}
	}

	/* Check password */

	@Test(expected = PasswordException.class)
	public void shouldCheckEmptyPassword() throws PasswordException {
		String password = "";
		Validator.validatePassword(password);
	}

	@Test(expected = PasswordException.class)
	public void shouldCheckShortPassword() throws PasswordException {
		String password = "qQ1";
		Validator.validatePassword(password);
	}

	@Test(expected = PasswordException.class)
	public void shouldCheckPasswordWithoutUpperCaseLetter() throws PasswordException {
		String password = "abacaba1";
		Validator.validatePassword(password);
	}

	@Test(expected = PasswordException.class)
	public void shouldCheckPasswordWithoutNumbers() throws PasswordException {
		String password = "Abacaba";
		Validator.validatePassword(password);
	}

	@Test(expected = PasswordException.class)
	public void shouldCheckPasswordWithCyrrilicSymbols() throws PasswordException {
		String password = "Фываолдж1";
		Validator.validatePassword(password);
	}

	@Test
	public void shouldCheckCorrectPassword() throws PasswordException {
		List<String> passwords = new LinkedList<String>();
		passwords.add("Abacaba1");
		passwords.add("ABACABa1");
		passwords.add("vvbbA3");
		passwords.add("AbacabA_1");
		passwords.add("Ab12345");
		passwords.add("newUser__1");
		for (String password : passwords) {
			assertTrue(Validator.validatePassword(password));
		}
	}

	/* Check email */

	@Test(expected = EmailException.class)
	public void shouldCheckEmptyEmail() throws EmailException {
		String email = "";
		Validator.validateEmail(email);
	}

	@Test(expected = EmailException.class)
	public void shouldCheckCyrrilicEmail() throws EmailException {
		String email = "пользователь@tut.by";
		Validator.validateEmail(email);
	}

	@Test(expected = EmailException.class)
	public void shouldCheckEmailWithoutAt() throws EmailException {
		String email = "user.tut.by";
		Validator.validateEmail(email);
	}

	@Test(expected = EmailException.class)
	public void shouldCheckEmailWithoutLastPart() throws EmailException {
		String email = "user@tut";
		Validator.validateEmail(email);
	}

	@Test
	public void shouldCheckCorrectEmail() throws EmailException {
		List<String> emails = new LinkedList<String>();
		emails.add("user@tut.by");
		emails.add("user_11@tut.by");
		emails.add("user@minsk.bsu.by");
		emails.add("user123@bsu.by");
		emails.add("a.b.user@mail.ru");
		for (String email : emails) {
			assertTrue(Validator.validateEmail(email));
		}

	}

	/* Check date */

	@Test(expected = DateException.class)
	public void shouldCheckEmptyDate() throws DateException {
		Date date = null;
		Validator.validateDate(date);
	}

	@Test(expected = DateException.class)
	public void shouldCheckBeforeDate() throws DateException {
		Date date = new GregorianCalendar(1900, Calendar.JANUARY, 01).getTime();
		Validator.validateDate(date);
	}

	@Test(expected = DateException.class)
	public void shouldCheckAfterDate() throws DateException {
		Date date = new GregorianCalendar(2020, Calendar.JANUARY, 01).getTime();
		Validator.validateDate(date);
	}

	@Test
	public void shouldCheckCorrectDate() throws DateException {
		Date date = new GregorianCalendar(2000, Calendar.SEPTEMBER, 9).getTime();
		Validator.validateDate(date);
	}

	/* Check user */

	@Test(expected = ServiceException.class)
	public void shouldCheckEmptyUser() throws ServiceException {
		User user = new User();
		Validator.validateUser(user);
	}

	@Test(expected = ServiceException.class)
	public void shouldCheckIncorrectUser() throws ServiceException {
		User user = new User();
		user.setSurname("Ivanov");
		user.setName("Ivan");
		user.addEmail("Ivanov.email.org");
		user.setLogin("1A");
		user.setPassword("ivanov_ivan");
		assertTrue(Validator.validateUser(user));
	}

	@Test
	public void shouldCheckCorrectUser() throws ServiceException {
		User user = new User();
		user.setSurname("Ivanov");
		user.setName("Ivan");
		user.addEmail("Ivanov@email.org");
		user.setLogin("Ivan");
		user.setPassword("Ivan1234");
		user.setBirthday("1985/01/10", "yyyy/MM/dd");
		assertTrue(Validator.validateUser(user));
	}

	/* Check user parts */

	@Test
	public void shouldCheckPersonalDataForUsers() {
		User firstUser = new User();
		firstUser.setSurname("Ivanov");
		firstUser.setName("Ivan");
		firstUser.setStatus("student");
		firstUser.addEmail("Ivanov@email.org");
		firstUser.setBirthday("1985/01/10", "yyyy/MM/dd");

		User secondUser = new User();
		secondUser.setSurname("Ivanov");
		secondUser.setName("Ivan");
		secondUser.setStatus("student");
		secondUser.addEmail("Ivanov@email.org");
		secondUser.setBirthday("1985/01/10", "yyyy/MM/dd");

		assertTrue(Validator.personalDataEqual(firstUser, secondUser));

		firstUser.setSurname("Petrov");
		assertFalse(Validator.personalDataEqual(firstUser, secondUser));

		secondUser.setSurname("Petrov");
		firstUser.setName("Petr");
		assertFalse(Validator.personalDataEqual(firstUser, secondUser));

		secondUser.setName("Petr");
		firstUser.setStatus("guru");
		assertFalse(Validator.personalDataEqual(firstUser, secondUser));

		secondUser.setStatus("guru");
		firstUser.addEmail("guru@tut.by");
		assertFalse(Validator.personalDataEqual(firstUser, secondUser));

		secondUser.addEmail("guru@tut.by");
		firstUser.setBirthday("1967/01/10", "yyyy/MM/dd");
		assertFalse(Validator.personalDataEqual(firstUser, secondUser));

		secondUser.setBirthday("1967/01/10", "yyyy/MM/dd");
		assertTrue(Validator.personalDataEqual(firstUser, secondUser));
	}

	@Test
	public void shouldCheckLanguagesForUsers() {
		User firstUser = new User();
		User secondUser = new User();
		assertTrue(Validator.userLanguagesEqual(firstUser, secondUser));

		firstUser.addLanguage("C", 1);
		assertFalse(Validator.userLanguagesEqual(firstUser, secondUser));

		secondUser.addLanguage("C", 1);
		assertTrue(Validator.userLanguagesEqual(firstUser, secondUser));

		firstUser.addLanguage("C++", 2);
		assertFalse(Validator.userLanguagesEqual(firstUser, secondUser));

		secondUser.addLanguage("C++", 4);
		assertFalse(Validator.userLanguagesEqual(firstUser, secondUser));

		firstUser.addLanguage("Java", 4);
		assertFalse(Validator.userLanguagesEqual(firstUser, secondUser));

		secondUser.addLanguage("Java", 4);
		assertFalse(Validator.userLanguagesEqual(firstUser, secondUser));
	}
	
	/* Check question title and text */
	
	@Test(expected = TitleException.class)
	public void shouldCheckEmptyQuestionTitle() throws ServiceException{
		List<String> langs = null;
		List<String> tags = null;
		Validator.validateQuestion("  ", "abacaba", langs, tags);
	}

	/* Check languages */

	@Test(expected = LanguageException.class)
	public void shouldCheckEmptyLanguageList() throws LanguageException {
		List<String> langs = new ArrayList<String>();
		Validator.validateLanguages(langs);
	}

	@Test(expected = LanguageException.class)
	public void shouldCheckLanguageListOfEmptyStrings() throws LanguageException {
		List<String> langs = new ArrayList<String>();
		langs.add("");
		langs.add("   ");
		langs.add("        ");
		Validator.validateLanguages(langs);
	}

	@Test
	public void shouldCheckCorrectLanguages() throws LanguageException {
		List<String> langs = new ArrayList<String>();
		langs.add("C#");
		langs.add("Java");
		langs.add("Python");
		langs.add("JavaScript");
		assertTrue(Validator.validateLanguages(langs));
	}

	@Test
	public void shouldCheckCorrectLowerCaseLanguages() throws LanguageException {
		List<String> langs = new ArrayList<String>();
		langs.add("c#");
		langs.add("java");
		langs.add("python");
		langs.add("javascript");
		langs.add("latex");
		assertTrue(Validator.validateLanguages(langs));
	}

	@Test
	public void shouldCheckMixedLanguageList() throws LanguageException {
		List<String> langs = new ArrayList<String>();
		langs.add("c");
		langs.add(" ");
		langs.add("java");
		assertTrue(Validator.validateLanguages(langs));
	}

	@Test(expected = LanguageException.class)
	public void shouldCheckIncorrectLanguageName() throws LanguageException {
		List<String> langs = new ArrayList<String>();
		langs.add("c");
		langs.add("javac");
		assertTrue(Validator.validateLanguages(langs));
	}

	/* Check tags */

	@Test(expected = TagException.class)
	public void shouldCheckEmptyTagList() throws TagException {
		List<String> tags = new ArrayList<String>();
		Validator.validateTags(tags);
	}

	@Test(expected = TagException.class)
	public void shouldCheckTagListOfEmptyStrings() throws TagException {
		List<String> tags = new ArrayList<String>();
		tags.add("");
		tags.add("   ");
		tags.add("        ");
		Validator.validateTags(tags);
	}

	@Test
	public void shouldCheckCorrectTags() throws TagException, DaoException, FatalDaoException {

		List<String> tags = new ArrayList<String>();
		tags.add("read");
		tags.add("recursion");
		tags.add("class");
		tags.add("function");

		assertTrue(Validator.validateTags(tags));
	}

	@Test
	public void shouldCheckTagsInIncorrectCase() throws TagException, DaoException, FatalDaoException {

		List<String> tags = new ArrayList<String>();
		tags.add("READ");
		tags.add("tcp");
		tags.add("Class");
		tags.add("JDbc");

		assertTrue(Validator.validateTags(tags));
	}

	@Test(expected = TagException.class)
	public void shouldCheckIncorrectTagList() throws TagException {

		List<String> tags = new ArrayList<String>();
		tags.add("function");
		tags.add("classes");
		tags.add("inharitance");
		tags.add("odbc");

		Validator.validateTags(tags);
	}

}
