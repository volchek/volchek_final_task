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
import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.language.LanguageService;
import by.tr.web.service.question.QuestionService;

public class FindQuestionById implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();

		RequestDispatcher d = null;

		try {
			String questionId = request.getParameter(TextAttribute.QUESTION_ID);
			Question question = questionService.findQuestionById(questionId);
			
			LanguageService languageService = serviceFactory.getLanguageService();
			List<String> languages = languageService.findFrequentLanguages();
			
			request.setAttribute(TextAttribute.QUESTION, question);
			request.setAttribute(TextAttribute.LANGUAGE_LIST, languages);
			
			d = request.getRequestDispatcher(PagePath.QUESTION_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);
	}
}
