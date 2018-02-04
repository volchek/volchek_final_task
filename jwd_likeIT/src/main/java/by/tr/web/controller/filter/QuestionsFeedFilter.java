package by.tr.web.controller.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.attribute.UserAttribute;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.user.User;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class QuestionsFeedFilter implements Filter {

	private ServiceFactory serviceFactory;
	private QuestionService questionService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false);

		if (session == null) {
			try {
				List<Question> questions = questionService.showLastQuestions();
				request.setAttribute(TextAttribute.QUESTION_LIST, questions);
			} catch (ServiceException ex) {
//				logger
			} finally {
				chain.doFilter(request, response);	
			}
			return;
		}

		User currentUser = (User) session.getAttribute(UserAttribute.CURRENT_USER);
		List<Question> questions = null;

		try {
			if (currentUser == null) {
				questions = questionService.showLastQuestions();
			} else {
				int userId = currentUser.getId();
				questions = questionService.showLastQuestionsForRegisteredUser(userId);
			}
			request.setAttribute(TextAttribute.QUESTION_LIST, questions);
		} catch (ServiceException ex) {
//			logger;
		} finally {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) {
		serviceFactory = ServiceFactory.getInstance();
		questionService = serviceFactory.getQuestionService();
	}
	
	@Override
	public void destroy(){
		
	}


}
