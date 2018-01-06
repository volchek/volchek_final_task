package by.tr.web.service.impl.util;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import by.tr.web.entity.User;
import by.tr.web.service.exception.NameException;
import by.tr.web.service.exception.LoginException;
import by.tr.web.service.exception.PasswordException;
import by.tr.web.service.exception.DateException;
import by.tr.web.service.exception.EmailException;
import by.tr.web.service.exception.ServiceException;

public class Validator {

	private static final String namePattern = "\\D+";
	private static final String loginPattern = "[a-zA-Z][0-9a-zA-Z_]{3,}";
	private static final String passwordPattern = "(?=.{6,})(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z_]).*";
	private static final String emailPattern = "[a-zA-Z0-9_\\-\\.]+@([\\S]+\\.)+[a-zA-Z]+";
	private static final Date defaultMinDate = new GregorianCalendar(1920, Calendar.JANUARY, 01).getTime();
	private static final Date currentDate = Calendar.getInstance().getTime();

	public static boolean validateUser(User user) throws ServiceException {

		validateNameAndSurname(user.getSurname());
		validateNameAndSurname(user.getName());

		validateLogin(user.getLogin());
		validatePassword(user.getPassword());

		validateEmail(user.getEmail());
		validateDate(user.getBirthday());

		return true;
	}

	public static boolean validateNameAndSurname(String name) throws NameException {
		if (!validate(name, namePattern)) {
			// Add logging
			throw new NameException("Surname / name is empty or incorrect");
		}
		return true;
	}

	public static boolean validateLogin(String login) throws LoginException {
		if (!validate(login, loginPattern)) {
			// Add logging
			throw new LoginException("Login is empty or incorrect");
		}
		return true;
	}

	public static boolean validatePassword(String password) throws PasswordException {
		if (!validate(password, passwordPattern)) {
			// Add logging
			throw new PasswordException("Password is empty or incorrect");
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
			// Add logging
			throw new EmailException("Email is empty or incorrect");
		}
		return true;
	}

	public static boolean validateDate(Date date) throws DateException {
		if (date == null) {
			throw new DateException("Date is empty");
		}

		if (date.before(defaultMinDate) || date.after(currentDate)) {
			throw new DateException("Input date is incorrect");
		}

		return true;
	}

	private static boolean isEmpty(String data) {
		if (data == null) {
			return true;
		}

		data = data.trim();
		if (data.isEmpty()) {
			return true;
		}
		return false;
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
