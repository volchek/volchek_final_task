package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class FindByLogin implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(UserAttribute.LOGIN).toString();

		User user = null;
		RequestDispatcher d = null;

		try {
			user = userService.findUserByLogin(login);
			request.setAttribute(UserAttribute.USER_ENTITY, user);
			d = request.getRequestDispatcher(PagePath.RESULT_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		} catch (FatalServiceException ex) {
			d = request.getRequestDispatcher(PagePath.DATABASE_ERROR_PAGE);
		}
		d.forward(request, response);
	}

}
