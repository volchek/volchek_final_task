package by.tr.web.controller.command.impl.text;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.entity.text.Question;
import by.tr.web.service.answer.AnswerService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.question.QuestionService;

public class EditAnswer implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();

		String questionId = request.getParameter(TextAttribute.QUESTION_ID);
		RequestDispatcher d = null;

		try {
			AnswerService answerService = serviceFactory.getAnswerService();
			String answerId = request.getParameter(TextAttribute.ANSWER_ID);
			answerService.checkIsAnswerCorrect(answerId);
			request.setAttribute(TextAttribute.ANSWER_ID, answerId);

			QuestionService questionService = serviceFactory.getQuestionService();
			Question question = questionService.findQuestionById(questionId);
			request.setAttribute(TextAttribute.QUESTION, question);
			
			d = request.getRequestDispatcher(PagePath.EDIT_TEXT);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);
	}

}
