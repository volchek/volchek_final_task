package by.tr.web.controller.command.impl.text;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;
import by.tr.web.entity.user.User;
import by.tr.web.service.CommonTextService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class EvaluateQuestion implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		CommonTextService textService = serviceFactory.getCommonTextService();

		try {
			User user = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
			int userId = user.getId();
			int textId = Integer.parseInt(request.getParameter(TextAttribute.QUESTION));
			int mark = Integer.parseInt(request.getParameter(TextAttribute.MARK));

			Question question = textService.evaluateText(userId, textId, mark, TextType.QUESTION);
			request.setAttribute(TextAttribute.QUESTION, question);

			RequestDispatcher d = null;
			d = request.getRequestDispatcher(PagePath.QUESTION_PAGE);
			d.forward(request, response);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.UNKNOWN_ERROR_PAGE);
		}
	}
}
