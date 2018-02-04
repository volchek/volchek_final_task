package by.tr.web.service.validator;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.pool.ConnectionPool;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;
import by.tr.web.entity.user.User;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text.IdException;
import by.tr.web.service.exception.text.LanguageException;
import by.tr.web.service.exception.text.TagException;
import by.tr.web.service.exception.text.TextException;
import by.tr.web.service.exception.text.TitleException;
import by.tr.web.service.exception.user.DateException;
import by.tr.web.service.exception.user.EmailException;
import by.tr.web.service.exception.user.LoginException;
import by.tr.web.service.exception.user.NameException;
import by.tr.web.service.exception.user.PasswordException;

public final class Validator {

	private final static String namePattern = "\\D+";
	private final static String emailPattern = "[a-zA-Z0-9_\\-\\.]+@([\\S]+\\.)+[a-zA-Z]+";

	private final static String loginPattern = "[a-zA-Z][0-9a-zA-Z_]{3,}";
	private final static String passwordPattern = "(?=.{6,})(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z_]).*";

	private final static Date currentDate = Calendar.getInstance().getTime();
	private final static Date defaultMinDate = new GregorianCalendar(1920, Calendar.JANUARY, 01).getTime();
	private final static String dateFormat = "yyyy-MM-dd";

	private final static Logger logger = LogManager.getLogger(ConnectionPool.class.getName());

	private Validator() {

	}

	public static boolean validatePersonalData(User user) throws ServiceException {

		validateNameAndSurname(user.getSurname());
		validateNameAndSurname(user.getName());

		validateEmail(user.getEmail());
		validateDate(user.getBirthday());

		return true;
	}

	public static boolean validateUser(User user) throws ServiceException {

		validatePersonalData(user);

		validateLogin(user.getLogin());
		validatePassword(user.getPassword());

		return true;
	}

	public static boolean validateQuestion(String title, String text, List<String> languages, List<String> tags)
			throws ServiceException {

		if (isEmpty(title)) {
			logger.error("Title is empty");
			throw new TitleException("Title is empty");
		} else if (isEmpty(text)) {
			logger.error("Question text is empty");
			throw new TextException("Question text is empty");
		}

		validateLanguages(languages);
		validateTags(tags);

		return true;
	}

	public static boolean validateText(String text) throws TextException {
		if (isEmpty(text)) {
			logger.error("The question text is empty");
			throw new TextException("The question text is empty");
		}
		return true;
	}

	public static boolean validateNameAndSurname(String name) throws NameException {
		if (!validate(name, namePattern)) {
			logger.error("Surname / name (value: " + name + ") is empty or incorrect");
			throw new NameException("Surname / name is empty or incorrect");
		}
		return true;
	}

	public static boolean validateEmail(List<String> userEmails) throws EmailException {
		List<String> emails = userEmails;
		if (!emails.isEmpty()) {
			for (String currentEmail : emails) {
				validateEmail(currentEmail);
			}
		}
		return true;
	}

	public static boolean validateEmail(String email) throws EmailException {
		if (!validate(email, emailPattern)) {
			logger.error("Email (value: " + email + ") is empty or incorrect");
			throw new EmailException("Email is empty or incorrect");
		}
		return true;
	}

	public static boolean validateDate(Date date) throws DateException {
		if (date == null) {
			logger.error("Date is empty");
			throw new DateException("Date is empty");
		}

		if (date.before(defaultMinDate) || date.after(currentDate)) {
			logger.error("Input date (value: " + date + ") is incorrect");
			throw new DateException("Input date is incorrect");
		}

		return true;
	}

	public static boolean validateLogin(String login) throws LoginException {
		if (!validate(login, loginPattern)) {
			logger.error("Login (value: " + login + ") is empty or incorrect");
			throw new LoginException("Login is empty or incorrect");
		}
		return true;
	}

	public static boolean validatePassword(String password) throws PasswordException {
		if (!validate(password, passwordPattern)) {
			logger.error("Password (value: " + password + ") is empty or incorrect");
			throw new PasswordException("Password is empty or incorrect");
		}
		return true;
	}

	public static int validateId(String id) throws IdException {
		try {
			int correctId = Integer.parseInt(id);
			return correctId;
		} catch (NumberFormatException ex) {
			logger.error("Id " + id + " has an incorrect format");
			throw new IdException("Id is incorrect", ex);
		}
	}

	public static boolean isEmpty(String data) {
		if (data == null) {
			return true;
		}

		data = data.trim();
		if (data.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean personalDataEqual(User firstUser, User secondUser) {

		if (firstUser == null || secondUser == null) {
			return false;
		}

		if (!firstUser.getSurname().equals(secondUser.getSurname())) {
			return false;
		} else if (!firstUser.getName().equals(secondUser.getName())) {
			return false;
		} else if (!firstUser.getStatus().equals(secondUser.getStatus())) {
			return false;
		} else if (!firstUser.getEmail().equals(secondUser.getEmail())) {
			return false;
		} else if (firstUser.getAvatar() != null && secondUser.getAvatar() != null) {
			if (!firstUser.getAvatar().equals(secondUser.getAvatar())) {
				return false;
			}
		} else if (!(firstUser.getBirthday(dateFormat)).equals(secondUser.getBirthday(dateFormat))) {
			return false;
		}

		return true;
	}

	public static boolean userLanguagesEqual(User firstUser, User secondUser) {

		Map<String, Integer> firstUserLang = firstUser.getLanguages();
		Map<String, Integer> secondUserLang = secondUser.getLanguages();

		if (firstUserLang == null && secondUserLang == null) {
			return true;
		} else if (firstUserLang != null && secondUserLang != null) {
			return firstUserLang.equals(secondUserLang);
		}
		return false;
	}

	public static boolean validateLanguages(List<String> languages) throws LanguageException {

		if (languages == null) {
			throw new LanguageException("Language list is empty");
		}

		Iterator<String> it = languages.iterator();
		int countIncorrectLanguages = 0;
		while (it.hasNext()) {
			String currentLanguage = it.next();
			if (!validateOneLanguage(currentLanguage)) {
				countIncorrectLanguages++;
			}
		}

		if (countIncorrectLanguages == languages.size()) {
			throw new LanguageException("All languages are incorrect");
		}

		return true;
	}

	public static boolean validateTags(List<String> tags) throws TagException {

		if (tags == null) {
			throw new TagException("Tag list is empty");
		}

		int countIncorrectTags = 0;
		Iterator<String> it = tags.iterator();
		while (it.hasNext()) {
			String currentTag = it.next();
			if (!validateOneTag(currentTag)) {
				countIncorrectTags++;
			}
		}
		if (countIncorrectTags == tags.size()) {
			throw new TagException("All tags are incorrect or unknown");
		}

		return true;
	}

	public static boolean isLanguageExist(String possibleLanguage) {

		LanguageSetSingleton languageSetSingleton = LanguageSetSingleton.getInstance();
		LanguageSet languageSet = languageSetSingleton.getLanguageSet();
		Map<String, String> standartLanguages = languageSet.getCaseNormalizationMapping();

		if (standartLanguages.containsKey(possibleLanguage.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static boolean isTagExist(String possibleTag) {

		TagSetSingleton tagSetSingleton = TagSetSingleton.getInstance();
		TagSet tagSet = tagSetSingleton.getTagSet();
		Map<String, Integer> standartTags = tagSet.getTagToIdSet();

		if (standartTags.containsKey(possibleTag.toLowerCase())) {
			return true;
		}
		return false;
	}

	private static boolean validateOneLanguage(String possibleLanguage) throws LanguageException {

		if (isEmpty(possibleLanguage)) {
			return false;
		}

		if (isLanguageExist(possibleLanguage)) {
			return true;
		} else {
			throw new LanguageException("Language '" + possibleLanguage + "' has an incorrect name");
		}
	}

	private static boolean validateOneTag(String tag) throws TagException {

		if (isEmpty(tag)) {
			return false;
		}

		if (isTagExist(tag)) {
			return true;
		} else {
			throw new TagException("Tag '" + tag + "' has an incorrect name");
		}
	}

	private static boolean validate(String data, String pattern) {
		if (isEmpty(data)) {
			return false;
		}

		if (!data.matches(pattern)) {
			return false;
		}

		return true;
	}

}
