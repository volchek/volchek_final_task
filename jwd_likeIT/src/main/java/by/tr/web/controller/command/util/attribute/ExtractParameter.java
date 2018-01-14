package by.tr.web.controller.command.util.attribute;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.tr.web.entity.User;
import by.tr.web.entity.language.Language;

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

		if (request.getParameter(UserAttribute.AVATAR) != null) {
			String avatar = request.getParameter(UserAttribute.AVATAR).toString();
			user.setAvatar(avatar);
		}

	}

	public static List<String> extractParameterList(HttpServletRequest request, String parameterName) {
		String languageArray[] = request.getParameterValues(parameterName);
		List<String> languageList = null;
		if (languageArray.length != 0) {
			languageList = Arrays.asList(languageArray);
		}
		return languageList;
	}

	public static void extractLanguages(HttpServletRequest request, User user) {
		extractLanguage(request, user, LanguageAttribute.LANG_C, Language.C);
		extractLanguage(request, user, LanguageAttribute.LANG_CPP, Language.CPP);
		extractLanguage(request, user, LanguageAttribute.LANG_C_SHARP, Language.C_SHARP);
		extractLanguage(request, user, LanguageAttribute.LANG_JAVA, Language.JAVA);
		extractLanguage(request, user, LanguageAttribute.LANG_PYTHON, Language.PYTHON);
		extractLanguage(request, user, LanguageAttribute.LANG_SWIFT, Language.SWIFT);
		extractLanguage(request, user, LanguageAttribute.LANG_PERL, Language.PERL);
		extractLanguage(request, user, LanguageAttribute.LANG_PHP, Language.PHP);
		extractLanguage(request, user, LanguageAttribute.LANG_HTML, Language.HTML);
		extractLanguage(request, user, LanguageAttribute.LANG_CSS, Language.CSS);
		extractLanguage(request, user, LanguageAttribute.LANG_JAVASCRIPT, Language.JAVASCRIPT);
		extractLanguage(request, user, LanguageAttribute.LANG_SQL, Language.SQL);
	}

	private static boolean extractLanguage(HttpServletRequest request, User user, String langAttr,
			Language langObject) {
		if (request.getParameter(langAttr) == null) {
			return false;
		}
		String stringLevel = request.getParameter(langAttr).toString();
		if (stringLevel != null && !stringLevel.isEmpty()) {
			Integer intLevel = Integer.parseInt(stringLevel);
			user.addLanguage(langObject, intLevel);
		}
		return true;
	}

}
