package by.tr.web.service.factory;

import by.tr.web.service.AnswerService;
import by.tr.web.service.InitializeService;
import by.tr.web.service.LanguageService;
import by.tr.web.service.QuestionService;
import by.tr.web.service.TagService;
import by.tr.web.service.UserService;
import by.tr.web.service.impl.AnswerServiceImpl;
import by.tr.web.service.impl.InitializeServiceImpl;
import by.tr.web.service.impl.LanguageServiceImpl;
import by.tr.web.service.impl.QuestionServiceImpl;
import by.tr.web.service.impl.TagServiceImpl;
import by.tr.web.service.impl.UserServiceImpl;

public final class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();

	private final InitializeService initService = new InitializeServiceImpl();
	private final UserService userService = new UserServiceImpl();
	private final LanguageService langService = new LanguageServiceImpl();
	private final TagService tagService = new TagServiceImpl();
	private final QuestionService questionService = new QuestionServiceImpl();
	private final AnswerService answerService = new AnswerServiceImpl();

	private ServiceFactory() {

	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}

	public LanguageService getLanguageService() {
		return langService;
	}

	public TagService getTagService() {
		return tagService;
	}

	public InitializeService getInitializeService() {
		return initService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}
	
}
