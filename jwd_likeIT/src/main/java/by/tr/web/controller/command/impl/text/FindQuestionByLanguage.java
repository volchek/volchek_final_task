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
import by.tr.web.controller.command.util.attribute.extractor.ExtractParameter;
import by.tr.web.entity.text.Question;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.question.QuestionService;

public class FindQuestionByLanguage implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();

		List<String> languages = ExtractParameter.extractParameterList(request, TextAttribute.LANGUAGE);
		System.out.println(languages);
		
		try {
			List<Question> questions = questionService.findQuestionByLanguage(languages);
			request.setAttribute(TextAttribute.QUESTION_LIST, questions);
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(PagePath.QUESTION_LIST);
			d.forward(request, response);
		} catch (ServiceException e) {
			response.sendRedirect(PagePath.CONTENT_ERROR_PAGE);
		}
	}
	
}
