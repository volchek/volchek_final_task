package by.tr.web.service.exception.text;

import by.tr.web.service.exception.common.ServiceException;

public class IdException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public IdException() {

	}

	public IdException(String message) {
		super(message);
	}

	public IdException(Exception exception) {
		super(exception);
	}

	public IdException(String message, Exception exception) {
		super(message, exception);
	}

}
