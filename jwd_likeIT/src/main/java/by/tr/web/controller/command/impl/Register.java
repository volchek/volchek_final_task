package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.LanguageAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.entity.language.Language;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.LoginException;
import by.tr.web.service.exception.PasswordException;
import by.tr.web.service.exception.NameException;
import by.tr.web.service.exception.DateException;
import by.tr.web.service.exception.EmailException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class Register implements ControllerCommand {

	private static final String datePattern = "yyyy-MM-dd";
	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = new User();
		extractParameters(request, user);

		System.out.println(user.toString());
		RequestDispatcher d = null;

		try {
			boolean result = userService.register(user);
			if (result == true) {
				response.sendRedirect(PagePath.ENTRY_PAGE);
			}

		} catch (LoginException | DateException | PasswordException | NameException | EmailException ex ){ 
			d = request.getRequestDispatcher(PagePath.REGISTER_PAGE);
			d.forward(request, response);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		} catch (FatalServiceException ex) {
			d = request.getRequestDispatcher(PagePath.DATABASE_ERROR_PAGE);
			d.forward(request, response);
		}
	}

	private void extractParameters(HttpServletRequest request, User user) {
		extractPersonalParameters(request, user);
		extractLanguages(request, user);
	}

	private void extractPersonalParameters(HttpServletRequest request, User user) {

		String surname = request.getParameter(UserAttribute.SURNAME).toString();
		String name = request.getParameter(UserAttribute.NAME).toString();
		String status = request.getParameter(UserAttribute.STATUS).toString();
		String login = request.getParameter(UserAttribute.LOGIN).toString();
		String password = request.getParameter(UserAttribute.PASSWORD).toString();
		String email = request.getParameter(UserAttribute.EMAIL).toString();
		String avatar = request.getParameter(UserAttribute.AVATAR).toString();
		String birthday = request.getParameter(UserAttribute.DATE).toString();

		user.setSurname(surname);
		user.setName(name);
		user.setStatus(status);
		user.setLogin(login);
		user.setPassword(password);
		user.addEmail(email);
		user.setAvatar(avatar);
		user.setBirthday(birthday, datePattern);

		if (request.getParameter(UserAttribute.RESERVE_EMAIL) != null) {
			String reserveEmail = request.getParameter(UserAttribute.RESERVE_EMAIL).toString();
			user.addEmail(reserveEmail);
		}
	}

	private void extractLanguages(HttpServletRequest request, User user) {
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

	private boolean extractLanguage(HttpServletRequest request, User user, String langAttr, Language langObject) {
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
