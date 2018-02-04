package by.tr.web.dao.mark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.Answer;
import by.tr.web.entity.text.TextType;
import by.tr.web.service.AnswerService;
import by.tr.web.service.CommonTextService;
import by.tr.web.service.InitializeService;
import by.tr.web.service.QuestionService;
import by.tr.web.service.exception.common.FatalServiceException;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.impl.AnswerServiceImpl;
import by.tr.web.service.impl.CommonTextServiceImpl;
import by.tr.web.service.impl.QuestionServiceImpl;

public class AddMarkTest {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();
	private CommonTextService textService = new CommonTextServiceImpl();
	private QuestionService questionService = new QuestionServiceImpl();
	private AnswerService answerService = new AnswerServiceImpl();
	private static double MIN_DELTA_VALUE = 0.0000000001;

	private int mark = 6;
	private int userId = 16;
	private int questionId = 1;
	private int answerId = 13;

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

		Question oldQuestion = questionService.findQuestionById(String.valueOf(questionId));

		Question questionAfterMarkInserting = textService.evaluateText(userId, questionId, mark, TextType.QUESTION);
		boolean isEquals = oldQuestion.equals(questionAfterMarkInserting);
		assertFalse(isEquals);

		Question questionAfterMarkDeleting = textService.deleteTextMark(userId, questionId, TextType.QUESTION);
		assertEquals(oldQuestion, questionAfterMarkDeleting);
	}

	@Test
	public void shouldUpdateQuestionMark() throws ServiceException {

		Question oldQuestion = questionService.findQuestionById(String.valueOf(questionId));

		Question question = textService.evaluateText(userId, questionId, mark, TextType.QUESTION);
		double firstAverageMark = question.getAverageMark();

		int newMark = 10;
		Question modifiedQuestion = textService.evaluateText(userId, questionId, newMark, TextType.QUESTION);
		double secondAverageMark = modifiedQuestion.getAverageMark();

		assertNotEquals(firstAverageMark, secondAverageMark, MIN_DELTA_VALUE);

		Question questionAfterMarkRemoving = textService.deleteTextMark(userId, questionId, TextType.QUESTION);
		assertEquals(oldQuestion, questionAfterMarkRemoving);
	}

	@Test
	public void shouldAddAndDeleteNewAnswerMark() throws ServiceException {

		Answer oldAnswer = answerService.findAnswerById(answerId);

		textService.evaluateText(userId, answerId, mark, TextType.ANSWER);
		Answer answerAfterMarkInserting = answerService.findAnswerById(answerId);

		boolean isEquals = oldAnswer.equals(answerAfterMarkInserting);
		assertFalse(isEquals);

		textService.deleteTextMark(userId, answerId, TextType.ANSWER);
		Answer answerAfterMarkDeleting = answerService.findAnswerById(answerId);
		assertEquals(oldAnswer, answerAfterMarkDeleting);
	}

	@Test
	public void shouldUpdateAnswerMark() throws ServiceException {

		Answer oldAnswer = answerService.findAnswerById(answerId);

		textService.evaluateText(userId, answerId, mark, TextType.ANSWER);
		Answer answerAfterMarkInserting = answerService.findAnswerById(answerId);

		int newMark = 10;
		textService.evaluateText(userId, answerId, newMark, TextType.ANSWER);
		Answer answerAfterMarkUpdating = answerService.findAnswerById(answerId);
		assertNotEquals(answerAfterMarkInserting.getAverageMark(), answerAfterMarkUpdating.getAverageMark(),
				MIN_DELTA_VALUE);

		textService.deleteTextMark(userId, answerId, TextType.ANSWER);
		Answer answerAfterMarkRemoving = answerService.findAnswerById(answerId);
		assertEquals(oldAnswer.getAverageMark(), answerAfterMarkRemoving.getAverageMark(), MIN_DELTA_VALUE);
	}

}
