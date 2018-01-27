package by.tr.web.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.user.UserService;

public class SignIn implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(UserAttribute.LOGIN);
		String password = request.getParameter(UserAttribute.PASSWORD);

		RequestDispatcher d = null;

		try {
			User user = userService.signIn(login, password);

			HttpSession session = request.getSession(true);
			session.setAttribute(UserAttribute.CURRENT_USER, user);
			session.setAttribute(UserAttribute.STRING_LANGUAGES, user.getLanguages());
			
			response.sendRedirect(PagePath.AFTER_SING_IN_PAGE + "?command=SHOW_LAST_QUESTIONS");
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		}
	}

}
