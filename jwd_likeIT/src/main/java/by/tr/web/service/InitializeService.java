package by.tr.web.service;

import by.tr.web.service.exception.FatalServiceException;

public interface InitializeService {

	void initializeApplication() throws FatalServiceException;

	void stopApplication();

}
