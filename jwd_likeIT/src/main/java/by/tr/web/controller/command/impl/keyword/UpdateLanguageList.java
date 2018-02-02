package by.tr.web.controller.command.impl.keyword;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.json.JsonConverter;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.language.LanguageService;

public class UpdateLanguageList implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		LanguageService languageService = serviceFactory.getLanguageService();

		try {
			Set<String> languages = languageService.updateLanguageList();
			String jsonLanguages = JsonConverter.getJson(languages);
			
			ServletContext context = request.getSession().getServletContext();
			context.removeAttribute(TextAttribute.LANGUAGE_LIST);
			context.setAttribute(TextAttribute.LANGUAGE_LIST, jsonLanguages);
			
			response.sendRedirect(PagePath.AFTER_UPDATING);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.CONTENT_ERROR_PAGE);
		}
	}
}
