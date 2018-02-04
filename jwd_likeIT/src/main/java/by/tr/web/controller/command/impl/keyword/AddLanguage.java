package by.tr.web.controller.command.impl.keyword;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.service.LanguageService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class AddLanguage implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String language = request.getParameter(TextAttribute.LANGUAGE);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		LanguageService languageService = serviceFactory.getLanguageService();

		try {
			languageService.addLanguage(language);
			response.sendRedirect(PagePath.AFTER_UPDATING);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.CONTENT_ERROR_PAGE);
		}
	}
}
