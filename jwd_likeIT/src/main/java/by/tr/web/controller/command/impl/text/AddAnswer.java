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
import by.tr.web.controller.command.util.attribute.extractor.ExtractParameter;
import by.tr.web.entity.Question;
import by.tr.web.entity.User;
import by.tr.web.service.answer.AnswerService;
import by.tr.web.service.exception.common.FatalServiceException;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.question.QuestionService;

public class AddAnswer implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		AnswerService answerService = serviceFactory.getAnswerService();
		
//		Answer answer = answerService.addAnswer(questionId, text, userId);
		
		RequestDispatcher d = null;
		d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		d.forward(request, response);
		

/*		User user = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		String title = request.getParameter(TextAttribute.TITLE);
		List<String> languages = ExtractParameter.extractParameterList(request, TextAttribute.LANGUAGE);
		List<String> tags = ExtractParameter.extractParameterList(request, TextAttribute.TAG);
		String text = request.getParameter(TextAttribute.QUESTION_TEXT);

		RequestDispatcher d = null;

		try {
			Question question = questionService.addQuestion(user, title, languages, tags, text);
			request.setAttribute(TextAttribute.QUESTION, question);
			System.out.println(question);
			d = request.getRequestDispatcher(PagePath.QUESTION_PAGE);

		} catch (ServiceException e) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		} catch (FatalServiceException e) {
			d = request.getRequestDispatcher(PagePath.DATABASE_ERROR_PAGE);
		}
		d.forward(request, response);
*/
		
	}

}
