package by.tr.web.dao.question;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;
import by.tr.web.service.application.InitializeService;
import by.tr.web.service.exception.common.FatalServiceException;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.question.QuestionService;
import by.tr.web.service.question.impl.QuestionServiceImpl;
import by.tr.web.service.text.CommonTextService;
import by.tr.web.service.text.impl.CommonTextServiceImpl;

public class ShowQuestionTest {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();
	private CommonTextService textService = new CommonTextServiceImpl();
	private QuestionService questionService = new QuestionServiceImpl();

	@BeforeClass
	public static void startApplication() throws FatalServiceException, DaoException {
		initializeService.initializeApplication();
	}

	@AfterClass
	public static void stopApplication() {
		initializeService.stopApplication();
	}

	@Test
	public void shouldShowUserQuestions() throws ServiceException {

		int userId = 1;
		List<Question> questions = textService.showUserTexts(userId, TextType.QUESTION);
		assertNotNull(questions);

		userId = 0;
		questions = textService.showUserTexts(userId, TextType.QUESTION);
		assertEquals(questions, new ArrayList<Question>());
	}

	@Test
	public void shouldShowQuestionsByLanguages() throws ServiceException {

		List<String> languages = new ArrayList<String>();

		languages.add("basic");
		List<Question> questions = questionService.findQuestionByLanguage(languages);
		assertEquals(questions, new ArrayList<Question>());

		languages.add("C++");
		questions = questionService.findQuestionByLanguage(languages);
		assertNotNull(questions);

		languages.add("Java");
		questions = questionService.findQuestionByLanguage(languages);
		assertNotNull(questions);

		languages.add("Perl");
		questions = questionService.findQuestionByLanguage(languages);
		assertNotNull(questions);
	}

	@Test
	public void shouldShowQuestionsByTags() throws ServiceException {

		List<String> tags = new ArrayList<String>();

		tags.add("jar");
		List<Question> questions = questionService.findQuestionByTag(tags);
		assertEquals(questions, new ArrayList<Question>());

		tags.add("file");
		questions = questionService.findQuestionByTag(tags);
		assertNotNull(questions);

		tags.add("template");
		questions = questionService.findQuestionByTag(tags);
		assertNotNull(questions);

		tags.add("write");
		questions = questionService.findQuestionByTag(tags);
		System.out.println(questions);
		assertNotNull(questions);
	}

}
