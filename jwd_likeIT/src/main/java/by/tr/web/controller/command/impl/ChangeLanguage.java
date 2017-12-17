package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;

public class ChangeLanguage implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).setAttribute("local", request.getParameter("local"));
		request.getRequestDispatcher("index.jsp").forward(request, response);

	}
}