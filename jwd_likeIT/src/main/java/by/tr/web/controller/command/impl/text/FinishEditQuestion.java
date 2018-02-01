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
import by.tr.web.service.language.LanguageService;
import by.tr.web.service.question.QuestionService;

public class FinishEditQuestion implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();

		User user = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		int userId = user.getId();

		RequestDispatcher d = null;

		int questionId = Integer.parseInt(request.getParameter(TextAttribute.QUESTION_ID));
		String old_text = request.getParameter(TextAttribute.QUESTION_OLD_TEXT);
		String new_text = request.getParameter(TextAttribute.QUESTION_TEXT);

		try {
			Question question = questionService.editQuestion(questionId, userId, old_text, new_text);
			request.setAttribute(TextAttribute.QUESTION, question);

			LanguageService languageService = serviceFactory.getLanguageService();
			List<String> languages = languageService.findFrequentLanguages();
			request.setAttribute(TextAttribute.LANGUAGE_LIST, languages);

			d = request.getRequestDispatcher(PagePath.QUESTION_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);

	}

}
