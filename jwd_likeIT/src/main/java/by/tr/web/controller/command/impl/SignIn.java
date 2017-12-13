package by.tr.web.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConsts;
import by.tr.web.controller.exception.ControllerException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.exception.ServiceException;


public class SignIn implements ControllerCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		ServiceFactory serviceObject = ServiceFactory.getInstance();        
		UserService userObject = serviceObject.getUserService();
		
//		String parser = request.getParameter(PARSER_TYPE);

		RequestDispatcher d = null;
	}

}

