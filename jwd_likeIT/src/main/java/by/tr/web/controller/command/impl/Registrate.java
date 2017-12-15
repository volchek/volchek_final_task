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
import by.tr.web.service.UserService;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;


public class Registrate implements ControllerCommand {
   
	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static UserService userService = serviceFactory.getUserService();

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ 

		User user = new User();
		getParameters(request, user);
		
		try {
			boolean result = userService.registrate(user);
			if (result == true){
				response.sendRedirect(CommandConsts.ENTRY_PAGE);
			}

		} catch (ServiceException ex){
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(CommandConsts.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		}
	}
	
	
	private void getParameters(HttpServletRequest request, User user){
		
		String surname = request.getParameter("surname").toString();
		String name = request.getParameter("name").toString();
		String status = request.getParameter("status").toString();
		String login = request.getParameter("login").toString();
		String password = request.getParameter("password").toString();

		user.setSurname(surname);
		user.setName(name);
		user.setStatus(status);
		user.setLogin(login);
		user.setPassword(password);
		
	}
	
}
