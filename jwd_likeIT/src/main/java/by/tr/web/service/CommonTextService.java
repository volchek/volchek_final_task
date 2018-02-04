package by.tr.web.service;

import java.util.List;

import by.tr.web.entity.text.Question;
import by.tr.web.entity.text.TextType;
import by.tr.web.service.exception.common.ServiceException;

public interface CommonTextService {

	Question evaluateText(int userId, int textId, int mark, TextType textType) throws ServiceException;

	Question deleteTextMark(int userId, int textId, TextType textType) throws ServiceException;

	List<Question> showUserTexts(int userId, TextType textType) throws ServiceException;

}
