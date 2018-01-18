package by.tr.web.service;

import java.util.List;

import by.tr.web.entity.Question;
import by.tr.web.service.exception.ServiceException;

public interface QuestionService {

	Question addQuestion(int authorId, String title, List<String> languages, List<String> tags, String text)
			throws ServiceException;

}
