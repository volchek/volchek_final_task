package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConsts;
import by.tr.web.entity.User;
import by.tr.web.entity.UserAttributes;
import by.tr.web.service.UserService;
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
			boolean result = userService.registrate(user);
			if (result == true) {
				response.sendRedirect(CommandConsts.ENTRY_PAGE);
			}

		} catch (ServiceException ex) {
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(CommandConsts.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		}
	}

	private void getParameters(HttpServletRequest request, User user) {

		String surname = request.getParameter(UserAttributes.SURNAME).toString();
		String name = request.getParameter(UserAttributes.NAME).toString();
		String status = request.getParameter(UserAttributes.STATUS).toString();
		String login = request.getParameter(UserAttributes.LOGIN).toString();
		String password = request.getParameter(UserAttributes.PASSWORD).toString();

		user.setSurname(surname);
		user.setName(name);
		user.setStatus(status);
		user.setLogin(login);
		user.setPassword(password);

	}

}
