package by.tr.web.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.controller.command.util.json.JsonConverter;
import by.tr.web.entity.User;
import by.tr.web.service.LanguageService;
import by.tr.web.service.TagService;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;

public class SignIn implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();
	private static LanguageService langService = serviceFactory.getLanguageService();
	private static TagService tagService = serviceFactory.getTagService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(UserAttribute.LOGIN);
		String password = request.getParameter(UserAttribute.PASSWORD);

		RequestDispatcher d = null;

		try {
			User user = userService.signIn(login, password);
			String jsonLanguages = null;
			String jsonTags = null;
			if (user != null) {
				List<String> languages = langService.getLanguageList();
				jsonLanguages = JsonConverter.getJson(languages);
				List<String> tags = tagService.getTagList();
				jsonTags = JsonConverter.getJson(tags);
			}
			createSession(request, user, jsonLanguages, jsonTags);
			response.sendRedirect(PagePath.AFTER_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		} catch (FatalServiceException ex) {
			d = request.getRequestDispatcher(PagePath.DATABASE_ERROR_PAGE);
			d.forward(request, response);
		}
	}

	private void createSession(HttpServletRequest request, User user, String languages, String tags) {
		HttpSession session = request.getSession(true);
		session.setAttribute(UserAttribute.CURRENT_USER, user);
		session.setAttribute(UserAttribute.STRING_LANGUAGES, user.getStringLanguages());
		session.setAttribute(TextAttribute.LANGUAGE_LIST, languages);
		session.setAttribute(TextAttribute.TAGS_LIST, tags);
	}

}
