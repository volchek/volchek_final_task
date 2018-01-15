package by.tr.web.service.exception.user_exception;

import by.tr.web.service.exception.ServiceException;

public class EmailException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public EmailException() {
	}

	public EmailException(String message) {
		super(message);
	}

	public EmailException(Exception exception) {
		super(exception);
	}

	public EmailException(String message, Exception exception) {
		super(message, exception);
	}

}
