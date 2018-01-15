package by.tr.web.service.exception.user_exception;

import by.tr.web.service.exception.ServiceException;

public class DateException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public DateException() {
	}

	public DateException(String message) {
		super(message);
	}

	public DateException(Exception exception) {
		super(exception);
	}

	public DateException(String message, Exception exception) {
		super(message, exception);
	}

}