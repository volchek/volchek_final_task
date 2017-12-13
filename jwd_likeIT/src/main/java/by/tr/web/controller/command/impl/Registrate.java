package by.tr.web.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.CommandConsts;


public class Registrate implements ControllerCommand {
   
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ 

//		List<CD> result = ResultHolder.getInstance().getResult();
  
		RequestDispatcher d = null;
	}
	
}
