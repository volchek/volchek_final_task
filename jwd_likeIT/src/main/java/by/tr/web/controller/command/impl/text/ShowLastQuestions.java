package by.tr.web.controller.command.impl.text;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.question.QuestionService;

public class ShowLastQuestions implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();
		
		RequestDispatcher d = null;
		System.out.println("!!!");
		System.out.println(request.getQueryString());
		
		User currentUser = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		int userId = currentUser.getId();
		
		try {
			List<Question> questions = questionService.showLastQuestionsForRegisteredUser(userId);
			request.setAttribute(TextAttribute.QUESTION_LIST, questions);
			System.out.println(questions);
			d = request.getRequestDispatcher(PagePath.AFTER_SING_IN_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);
	}
}
