package by.tr.web.service.language;

import java.util.List;

import by.tr.web.service.exception.common.ServiceException;

public interface LanguageService {

	List<String> findFrequentLanguages() throws ServiceException;
}
