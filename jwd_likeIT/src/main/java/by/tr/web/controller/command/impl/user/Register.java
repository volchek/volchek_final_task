package by.tr.web.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.extractor.ExtractParameter;
import by.tr.web.entity.user.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.user.DateException;
import by.tr.web.service.exception.user.EmailException;
import by.tr.web.service.exception.user.LoginException;
import by.tr.web.service.exception.user.NameException;
import by.tr.web.service.exception.user.PasswordException;
import by.tr.web.service.factory.ServiceFactory;

public class Register implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = new User();
		ExtractParameter.extractAllUserParameters(request, user);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {
			userService.register(user);
			response.sendRedirect(PagePath.AFTER_REGISTER_PAGE);
		} catch (LoginException | DateException | PasswordException | NameException | EmailException ex) {
			response.sendRedirect(PagePath.INSERTING_ERROR_PAGE);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.UNKNOWN_ERROR_PAGE);
		}
	}
}
