package by.tr.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.ControllerCommandFactory;
import by.tr.web.controller.command.util.CommandConst;

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ControllerCommandFactory contrCommandObject = ControllerCommandFactory.getInstance();
		String commandName = request.getParameter(CommandConst.COMMAND_NAME);
		ControllerCommand command = contrCommandObject.getCommand(commandName);
		command.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
