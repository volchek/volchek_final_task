package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConst;
import by.tr.web.entity.User;
import by.tr.web.entity.UserAttribute;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class Register implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = new User();
		getParameters(request, user);

		try {
			boolean result = userService.register(user);
			if (result == true) {
				response.sendRedirect(CommandConst.ENTRY_PAGE);
			}

		} catch (ServiceException ex) {
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(CommandConst.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		} catch (FatalServiceException ex) {
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(CommandConst.DATABASE_ERROR_PAGE);
			d.forward(request, response);
		}
	}

	private void getParameters(HttpServletRequest request, User user) {

		String surname = request.getParameter(UserAttribute.SURNAME).toString();
		String name = request.getParameter(UserAttribute.NAME).toString();
		String status = request.getParameter(UserAttribute.STATUS).toString();
		String login = request.getParameter(UserAttribute.LOGIN).toString();
		String password = request.getParameter(UserAttribute.PASSWORD).toString();

		user.setSurname(surname);
		user.setName(name);
		user.setStatus(status);
		user.setLogin(login);
		user.setPassword(password);

	}

}
