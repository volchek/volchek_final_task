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
import by.tr.web.service.text.CommonTextService;
import by.tr.web.service.text.impl.CommonTextServiceImpl;

public class ShowQuestionTest {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();
	private CommonTextService textService = new CommonTextServiceImpl();

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
		System.out.println(questions);
		assertNotNull(questions);

		userId = 0;
		questions = textService.showUserTexts(userId, TextType.QUESTION);
		System.out.println(questions);
		assertEquals(questions, new ArrayList<Question>());
	}

}
