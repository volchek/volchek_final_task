package by.tr.web.dao.mark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.Question;
import by.tr.web.service.application.InitializeService;
import by.tr.web.service.exception.common.FatalServiceException;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.question.QuestionService;
import by.tr.web.service.question.impl.QuestionServiceImpl;

public class AddMarkTest {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();
	private QuestionService questionService = new QuestionServiceImpl();
	private static double MIN_DELTA_VALUE = 0.0000000001;

	private int mark = 6;
	private int userId = 16;
	private int questionId = 1;

	@BeforeClass
	public static void startApplication() throws FatalServiceException, DaoException {
		initializeService.initializeApplication();

	}

	@AfterClass
	public static void stopApplication() {
		initializeService.stopApplication();
	}

	@Test
	public void shouldAddAndDeleteNewQuestionMark() throws ServiceException {

		Question oldQuestion = questionService.findQuestionById(questionId);

		Question questionAfterMarkInserting = questionService.evaluateQuestion(userId, questionId, mark);
		boolean isEquals = oldQuestion.equals(questionAfterMarkInserting);
		assertFalse(isEquals);

		Question questionAfterMarkDeleting = questionService.deleteQuestionMark(userId, questionId);
		assertEquals(oldQuestion, questionAfterMarkDeleting);
	}
	
	@Test
	public void shouldUpdateQuestionMark() throws ServiceException {
		
		Question oldQuestion = questionService.findQuestionById(questionId);
		
		Question question = questionService.evaluateQuestion(userId, questionId, mark);
		double firstAverageMark = question.getAverageMark();
		
		int newMark = 10;
		Question modifiedQuestion = questionService.evaluateQuestion(userId, questionId, newMark);
		double secondAverageMark = modifiedQuestion.getAverageMark();
		
		assertNotEquals(firstAverageMark, secondAverageMark, MIN_DELTA_VALUE);
		
		Question questionAfterMarkRemoving = questionService.deleteQuestionMark(userId, questionId);
		assertEquals(oldQuestion, questionAfterMarkRemoving);		
	}

}
