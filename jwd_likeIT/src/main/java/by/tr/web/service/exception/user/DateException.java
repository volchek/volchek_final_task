package by.tr.web.service.exception.user;

import by.tr.web.service.exception.common.ServiceException;

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
