package by.tr.web.controller.command.impl.user;

import java.io.IOException;
import java.util.Set;

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
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.user.UserService;

public class SignIn implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();
	private static LanguageSet LanguageSetInstance = LanguageSetSingleton.getInstance().getLanguageSet();
	private static TagSet TagSetInstance = TagSetSingleton.getInstance().getTagSet();

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
				Set<String> languages = LanguageSetInstance.getLanguageSet();
				jsonLanguages = JsonConverter.getJson(languages);

				Set<String> tags = TagSetInstance.getTagSet();
				jsonTags = JsonConverter.getJson(tags);
			}
			createSession(request, user, jsonLanguages, jsonTags);
			response.sendRedirect(PagePath.AFTER_SING_IN_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		}
	}

	private void createSession(HttpServletRequest request, User user, String languages, String tags) {
		HttpSession session = request.getSession(true);
		session.setAttribute(UserAttribute.CURRENT_USER, user);
		session.setAttribute(UserAttribute.STRING_LANGUAGES, user.getLanguages());
		session.setAttribute(TextAttribute.LANGUAGE_LIST, languages);
		session.setAttribute(TextAttribute.TAGS_LIST, tags);
	}

}
