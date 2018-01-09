package by.tr.web.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.CommandAttribute;
import by.tr.web.controller.command.util.attribute.ExtractParameter;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class EditUser implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User currentUser = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		User modifiedUser = getModifiedUser(request);

		System.out.println(currentUser);
		System.out.println(modifiedUser);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
			request.setAttribute(CommandAttribute.COMMAND_RESULT, true);

//			request.removeAttribute(UserAttribute.STRING_LANGUAGES);
//			request.setAttribute(UserAttribute.STRING_LANGUAGES, currentUser.getStringLanguages());
			
			
		} catch (ServiceException | FatalServiceException e) {
			// TODO Auto-generated catch block
			request.setAttribute(CommandAttribute.COMMAND_RESULT, false);
		}
		response.sendRedirect(PagePath.AFTER_USER_UPDATING);
	}

	private User getModifiedUser(HttpServletRequest request) {

		User user = new User();
		
		ExtractParameter.extractPersonalParameters(request, user);
		ExtractParameter.extractLanguages(request, user);

		return user;

	}

}