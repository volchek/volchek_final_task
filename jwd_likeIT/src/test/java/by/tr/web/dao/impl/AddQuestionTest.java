package by.tr.web.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Question;
import by.tr.web.service.InitializeService;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class AddQuestionTest {

	QuestionService questionService = serviceFactory.getQuestionService();
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
	public void shouldInsertNewQuestion() throws ServiceException {
		int authorId = 1;
		String title = "Шаблоны с переменным числом параметров";
		String text = "Что такое шаблоны с переменным числом параметров?<br>Где про это можно почитать?";

		List<String> languages = new ArrayList<String>();
		languages.add("C++");
		languages.add("C++11");
		languages.add("C++14");

		List<String> tags = new ArrayList<String>();
		tags.add("template");
		tags.add("function");

		Question result = questionService.addQuestion(authorId, title, languages, tags, text);
		assertEquals(result.getAuthorLogin(), "Zayatc");
	}
	
}
