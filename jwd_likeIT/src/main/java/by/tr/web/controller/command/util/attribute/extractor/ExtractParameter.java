package by.tr.web.controller.command.util.attribute.extractor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.tr.web.controller.command.util.attribute.LanguageAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.user.User;

public class ExtractParameter {

	private static final String datePattern = "yyyy-MM-dd";

	public static void extractAllUserParameters(HttpServletRequest request, User user) {

		extractPersonalParameters(request, user);
		extractAuthorizationParameters(request, user);
		extractLanguages(request, user);
	}

	public static void extractAuthorizationParameters(HttpServletRequest request, User user) {

		String login = request.getParameter(UserAttribute.LOGIN).toString();
		String password = request.getParameter(UserAttribute.PASSWORD).toString();

		user.setLogin(login);
		user.setPassword(password);

	}

	public static void extractPersonalParameters(HttpServletRequest request, User user) {

		String surname = request.getParameter(UserAttribute.SURNAME).toString();
		String name = request.getParameter(UserAttribute.NAME).toString();
		String status = request.getParameter(UserAttribute.STATUS).toString();
		String email = request.getParameter(UserAttribute.EMAIL).toString();
		String birthday = request.getParameter(UserAttribute.DATE).toString();

		user.setSurname(surname);
		user.setName(name);
		user.setStatus(status);
		user.addEmail(email);
		user.setBirthday(birthday, datePattern);

		if (request.getParameter(UserAttribute.RESERVE_EMAIL) != null) {
			String reserveEmail = request.getParameter(UserAttribute.RESERVE_EMAIL).toString();
			user.addEmail(reserveEmail);
		}
	}

	public static List<String> extractParameterList(HttpServletRequest request, String parameterName) {
		String keywordArray[] = request.getParameterValues(parameterName);

		List<String> keywordList = null;
		if (keywordArray.length != 0) {
			keywordList = Arrays.asList(keywordArray);
		}
		return keywordList;
	}

	public static void extractLanguages(HttpServletRequest request, User user) {
		extractLanguage(request, user, LanguageAttribute.LANG_C);
		extractLanguage(request, user, LanguageAttribute.LANG_CPP);
		extractLanguage(request, user, LanguageAttribute.LANG_C_SHARP);
		extractLanguage(request, user, LanguageAttribute.LANG_JAVA);
		extractLanguage(request, user, LanguageAttribute.LANG_PYTHON);
		extractLanguage(request, user, LanguageAttribute.LANG_SWIFT);
		extractLanguage(request, user, LanguageAttribute.LANG_PERL);
		extractLanguage(request, user, LanguageAttribute.LANG_PHP);
		extractLanguage(request, user, LanguageAttribute.LANG_HTML);
		extractLanguage(request, user, LanguageAttribute.LANG_CSS);
		extractLanguage(request, user, LanguageAttribute.LANG_JAVASCRIPT);
		extractLanguage(request, user, LanguageAttribute.LANG_SQL);
	}

	private static boolean extractLanguage(HttpServletRequest request, User user, String language) {
		if (request.getParameter(language) == null) {
			return false;
		}

		String stringLevel = request.getParameter(language).toString();
		if (stringLevel != null && !stringLevel.isEmpty()) {
			Integer intLevel = Integer.parseInt(stringLevel);

			LanguageSetSingleton languageSingleton = LanguageSetSingleton.getInstance();
			LanguageSet languageSet = languageSingleton.getLanguageSet();
			String normalizeLanguage = languageSet.getLanguageStandartName(language);

			user.addLanguage(normalizeLanguage, intLevel);
		}
		return true;
	}

}
