package by.tr.web.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.ExtractParameter;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.Question;
import by.tr.web.entity.User;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class AddQuestion implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();

		User user = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		String title = request.getParameter(TextAttribute.TITLE);
		List<String> languages = ExtractParameter.extractParameterList(request, TextAttribute.LANGUAGE);
		List<String> tags = ExtractParameter.extractParameterList(request, TextAttribute.TAG);
		String text = request.getParameter(TextAttribute.QUESTION_TEXT);		

		RequestDispatcher d = null;

		try {
			Question question = questionService.addQuestion(user, title, languages, tags, text);
			request.setAttribute(TextAttribute.QUESTION, question);
			System.out.println("CONTROLLER");
			System.out.println(question);
			d = request.getRequestDispatcher(PagePath.RESULT_PAGE);

		} catch (ServiceException e) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		} catch (FatalServiceException e) {
			d = request.getRequestDispatcher(PagePath.DATABASE_ERROR_PAGE);
		}
		d.forward(request, response);
	}
}
