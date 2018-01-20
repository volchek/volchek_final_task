package by.tr.web.controller.command.impl.language;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.CommandAttribute;

public class ChangeLanguage implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession(true).setAttribute(CommandAttribute.LOCAL_PARAMETER,
		request.getParameter(CommandAttribute.LOCAL_PARAMETER));
		String address = request.getParameter(CommandAttribute.ADDRESS_PARAMETER);
		String query = request.getParameter(CommandAttribute.QUERY_PARAMETER);
		String previousQuery = createQuery(address, query);

		if (previousQuery != null && !previousQuery.isEmpty()) {
			response.sendRedirect(previousQuery);
		} else {
			response.sendRedirect(PagePath.CONTENT_ERROR_PAGE);
		}

	}
	
	private String createQuery(String address, String query) {
		StringBuilder resultQuery = new StringBuilder();
		if (query != null && !query.isEmpty()) {
			resultQuery.append("Controller");
			resultQuery.append("?").append(query);
		}
		else {
			if (address != null && !address.isEmpty()) {
				resultQuery.append(address);
			}	
		}
		return resultQuery.toString();
	}
}