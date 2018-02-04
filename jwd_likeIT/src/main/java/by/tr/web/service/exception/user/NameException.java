package by.tr.web.service.exception.user;

import by.tr.web.service.exception.common.ServiceException;

public class NameException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public NameException() {

	}

	public NameException(String message) {
		super(message);
	}

	public NameException(Exception exception) {
		super(exception);
	}

	public NameException(String message, Exception exception) {
		super(message, exception);
	}

}
