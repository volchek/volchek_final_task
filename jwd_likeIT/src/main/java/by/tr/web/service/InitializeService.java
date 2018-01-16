package by.tr.web.service;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.service.exception.FatalServiceException;

public interface InitializeService {

	void initializeApplication() throws FatalServiceException, DaoException;

	void stopApplication();

}
