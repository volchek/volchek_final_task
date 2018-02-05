package by.tr.web.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class BanUser implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int userId = Integer.parseInt(request.getParameter(UserAttribute.USER_ID));
		Boolean ban = Boolean.valueOf(request.getParameter(UserAttribute.BAN));

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		try {
			userService.banUser(userId, ban);
			response.sendRedirect(PagePath.AFTER_UPDATING);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.UPDATING_ERROR_PAGE);
		}
	}
}
