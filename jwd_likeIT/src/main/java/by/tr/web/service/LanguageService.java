package by.tr.web.service;

import java.util.List;

import by.tr.web.service.exception.FatalServiceException;
import by.tr.web.service.exception.ServiceException;

public interface LanguageService {

	List<String> getLanguageList() throws ServiceException, FatalServiceException;

}
