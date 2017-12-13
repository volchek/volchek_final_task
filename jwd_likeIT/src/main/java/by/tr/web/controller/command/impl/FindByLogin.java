package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConsts;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class FindByLogin implements ControllerCommand {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login").toString();

		User user = null;
		RequestDispatcher d = null;
		
		try {
			user = userService.findUserByLogin(login);
			request.setAttribute("user", user);
			d = request.getRequestDispatcher(CommandConsts.RESULT_PAGE);

		} catch (ServiceException ex){
			d = request.getRequestDispatcher(CommandConsts.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);
	}

}
