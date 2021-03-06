package by.tr.web.dao;

import java.util.List;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.text.Question;

public interface QuestionDao {

	Question addQuestion(int authorID, String title, List<String> languages, List<String> tags, String text)
			throws DaoException;

	Question editQuestion(int questionId, int userId, String oldText, String newText) throws DaoException;

	boolean deleteQuestion(int questionId, int userId) throws DaoException;

	Question findQuestionById(int questionId) throws DaoException;

	List<Question> showLastQuestions() throws DaoException;

	List<Question> showLastQuestionsForRegisteredUser(int userId) throws DaoException;

	List<Question> findQuestionByLanguage(List<String> languages) throws DaoException;

	List<Question> findQuestionByTag(List<String> tags) throws DaoException;

}
