package by.tr.web.service.language;

import java.util.List;
import java.util.Set;

import by.tr.web.service.exception.common.ServiceException;

public interface LanguageService {

	List<String> findFrequentLanguages() throws ServiceException;

	boolean addLanguage(String language) throws ServiceException;

	Set<String> updateLanguageList() throws ServiceException;
	
}
