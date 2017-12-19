package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConst;
import by.tr.web.controller.command.util.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;

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
			session.setAttribute(UserAttribute.USER_ENTITY, user);
			response.sendRedirect(CommandConst.AFTER_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(CommandConst.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		} catch (FatalServiceException ex) {
			d = request.getRequestDispatcher(CommandConst.DATABASE_ERROR_PAGE);
			d.forward(request, response);
		}
	}

}
