package by.tr.web.service.application;

import by.tr.web.dao.exception.DaoException;
import by.tr.web.service.exception.common.FatalServiceException;

public interface InitializeService {

	void initializeApplication() throws FatalServiceException, DaoException;

	void stopApplication();

}
