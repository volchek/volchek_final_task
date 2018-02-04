package by.tr.web.controller.command.impl.keyword;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.service.TagService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class AddTag implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String tag = request.getParameter(TextAttribute.TAG);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		TagService tagService = serviceFactory.getTagService();

		try {
			tagService.addTag(tag);
			response.sendRedirect(PagePath.AFTER_UPDATING);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.CONTENT_ERROR_PAGE);
		}
	}
}
