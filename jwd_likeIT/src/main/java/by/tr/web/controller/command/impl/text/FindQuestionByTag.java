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

public class FindQuestionByTag implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();

		List<String> tags = ExtractParameter.extractParameterList(request, TextAttribute.TAG);
		System.out.println(tags);
		RequestDispatcher d = null;

		try {
			List<Question> questions = questionService.findQuestionByTag(tags);
			request.setAttribute(TextAttribute.QUESTION_LIST, questions);
			System.out.println(questions);
			d = request.getRequestDispatcher(PagePath.USER_QUESTIONS_PAGE);

		} catch (ServiceException e) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);
	}

}
