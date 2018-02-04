package by.tr.web.service.exception.text;

import by.tr.web.service.exception.common.ServiceException;

public class LanguageException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public LanguageException() {

	}

	public LanguageException(String message) {
		super(message);
	}

	public LanguageException(Exception exception) {
		super(exception);
	}

	public LanguageException(String message, Exception exception) {
		super(message, exception);
	}

}
