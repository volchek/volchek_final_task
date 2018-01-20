package by.tr.web.service.factory;

import by.tr.web.service.answer.AnswerService;
import by.tr.web.service.answer.impl.AnswerServiceImpl;
import by.tr.web.service.application.InitializeService;
import by.tr.web.service.application.impl.InitializeServiceImpl;
import by.tr.web.service.language.LanguageService;
import by.tr.web.service.language.impl.LanguageServiceImpl;
import by.tr.web.service.question.QuestionService;
import by.tr.web.service.question.impl.QuestionServiceImpl;
import by.tr.web.service.tag.TagService;
import by.tr.web.service.tag.impl.TagServiceImpl;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.impl.UserServiceImpl;

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
