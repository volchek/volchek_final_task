package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConsts;
import by.tr.web.entity.User;
import by.tr.web.entity.UserAttributes;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.exception.ServiceException;

public class SignIn implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(UserAttributes.LOGIN);
		String password = request.getParameter(UserAttributes.PASSWORD);

		RequestDispatcher d = null;

		try {
			User user = userService.signIn(login, password);
			HttpSession session = request.getSession(true);
			session.setAttribute(UserAttributes.USER_ENTITY, user);
			response.sendRedirect(CommandConsts.AFTER_PAGE);

		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(CommandConsts.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		}
	}

}
