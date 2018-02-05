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
import by.tr.web.entity.text.Question;
import by.tr.web.entity.user.User;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.exception.text.LanguageException;
import by.tr.web.service.exception.text.TagException;
import by.tr.web.service.exception.text.TextException;
import by.tr.web.service.exception.text.TitleException;
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

		try {
			Question question = questionService.addQuestion(user.getId(), title, languages, tags, text);
			request.setAttribute(TextAttribute.QUESTION, question);

			RequestDispatcher d = null;
			d = request.getRequestDispatcher(PagePath.QUESTION_PAGE);
			d.forward(request, response);
		} catch (TextException | TitleException | LanguageException | TagException ex) {
			response.sendRedirect(PagePath.INSERTING_ERROR_PAGE);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.UNKNOWN_ERROR_PAGE);
		}
	}
}
