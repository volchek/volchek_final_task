package by.tr.web.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Question;
import by.tr.web.service.AnswerService;
import by.tr.web.service.InitializeService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class AddAnswerTest {

	AnswerService answerService = serviceFactory.getAnswerService();
	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();

	@BeforeClass
	public static void startApplication() throws FatalServiceException, DaoException {
		initializeService.initializeApplication();

	}

	@AfterClass
	public static void stopApplication() {
		initializeService.stopApplication();
	}
	
	@Ignore
	@Test
	public void shouldInsertNewAnswer() throws ServiceException{
		int userId = 2;
		String text = "Я не знаю.";
		int questionId = 16;

		Question result = (Question) answerService.addAnswer(questionId, text, userId);
		System.out.println(result);
		assertEquals(result.getId(), 16);
		assertEquals(result.getAuthorLogin(), "Zayatc");
	}

	
	
}
