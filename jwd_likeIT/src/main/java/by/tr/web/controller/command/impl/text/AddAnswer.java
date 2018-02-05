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
import by.tr.web.entity.user.User;
import by.tr.web.service.AnswerService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text.TextException;
import by.tr.web.service.factory.ServiceFactory;

public class AddAnswer implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		AnswerService answerService = serviceFactory.getAnswerService();

		User user = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		int userId = user.getId();
		String text = request.getParameter(TextAttribute.ANSWER_TEXT);
		int questionId = Integer.parseInt(request.getParameter(TextAttribute.QUESTION_ID));

		try {
			Question question = (Question) answerService.addAnswer(questionId, text, userId);
			request.setAttribute(TextAttribute.QUESTION, question);

			RequestDispatcher d = null;
			d = request.getRequestDispatcher(PagePath.QUESTION_PAGE);
			d.forward(request, response);
		} catch (TextException ex) {
			response.sendRedirect(PagePath.INSERTING_ERROR_PAGE);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.UNKNOWN_ERROR_PAGE);
		}
	}

}
