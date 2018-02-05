package by.tr.web.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.CommandAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.controller.command.util.attribute.extractor.ExtractParameter;
import by.tr.web.entity.user.User;
import by.tr.web.service.UserService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class EditUser implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User currentUser = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		User modifiedUser = getModifiedUser(request);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {
			userService.updatePersonalInfo(currentUser, modifiedUser);
			request.setAttribute(CommandAttribute.COMMAND_RESULT, true);
			HttpSession session = request.getSession(true);
			session.removeAttribute(UserAttribute.STRING_LANGUAGES);
			session.setAttribute(UserAttribute.STRING_LANGUAGES, currentUser.getLanguages());
			response.sendRedirect(PagePath.AFTER_UPDATING);
		} catch (ServiceException e) {
			response.sendRedirect(PagePath.UPDATING_ERROR_PAGE);
		}
	}

	private User getModifiedUser(HttpServletRequest request) {

		User user = new User();
		
		ExtractParameter.extractPersonalParameters(request, user);
		ExtractParameter.extractLanguages(request, user);

		return user;

	}

}
