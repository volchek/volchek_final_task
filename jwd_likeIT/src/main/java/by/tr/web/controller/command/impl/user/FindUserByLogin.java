package by.tr.web.controller.command.impl.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;

import by.tr.web.controller.command.ControllerCommand;
import by.tr.web.controller.command.util.PagePath;
import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.User;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.text.CommonTextService;
import by.tr.web.service.user.UserService;

public class FindUserByLogin implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(UserAttribute.LOGIN).toString();

		User user = null;
		RequestDispatcher d = null;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		try {
			user = userService.findUserByLogin(login);
			int userId = user.getId();
			CommonTextService textService = serviceFactory.getCommonTextService();
			List<Question> questions = textService.showUserTexts(userId, TextType.QUESTION);
			List<Question> answers = textService.showUserTexts(userId, TextType.ANSWER);
			
			request.setAttribute(UserAttribute.USER_ENTITY, user);
			List<String> userLanguages = Lists.newArrayList(user.getLanguages().keySet());
			request.setAttribute(UserAttribute.STRING_LANGUAGES, userLanguages);
			request.setAttribute(TextAttribute.QUESTION_LIST, questions);
			request.setAttribute(TextAttribute.ANSWER_LIST, answers);
			
			d = request.getRequestDispatcher(PagePath.USER_INFO_PAGE);
		} catch (ServiceException ex) {
			d = request.getRequestDispatcher(PagePath.CONTENT_ERROR_PAGE);
		}
		d.forward(request, response);
	}

}
