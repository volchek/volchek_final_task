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
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;
import by.tr.web.entity.user.User;
import by.tr.web.service.CommonTextService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class ShowUserAnswer implements ControllerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User currentUser = (User) request.getSession().getAttribute(UserAttribute.CURRENT_USER);
		if (currentUser == null) {
			response.sendRedirect(PagePath.ENTRY_PAGE);
			return;
		}

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		CommonTextService textService = serviceFactory.getCommonTextService();

		try {
			int userId = currentUser.getId();
			List<Question> questions = textService.showUserTexts(userId, TextType.ANSWER);
			request.setAttribute(TextAttribute.QUESTION_LIST, questions);
			
			RequestDispatcher d = null;
			d = request.getRequestDispatcher(PagePath.USER_ANSWERS_PAGE);
			d.forward(request, response);
		} catch (ServiceException ex) {
			response.sendRedirect(PagePath.CONTENT_ERROR_PAGE);
		}
	}

}
