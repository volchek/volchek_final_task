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
import by.tr.web.entity.user.User;
import by.tr.web.service.answer.AnswerService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class DeleteAnswer implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		AnswerService answerService = serviceFactory.getAnswerService();

		User user = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		int userId = user.getId();

		try {
			int answerId = Integer.parseInt(request.getParameter(TextAttribute.ANSWER_ID));
			answerService.deleteAnswer(answerId, userId);
			response.sendRedirect(request.getContextPath().concat("/").concat(PagePath.AFTER_UPDATING));
		} catch (ServiceException ex) {
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
			d.forward(request, response);
		}
	}

}
