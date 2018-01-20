package by.tr.web.service.exception.user_exception;

import by.tr.web.service.exception.common.ServiceException;

public class PasswordException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public PasswordException() {
	}

	public PasswordException(String message) {
		super(message);
	}

	public PasswordException(Exception exception) {
		super(exception);
	}
	
	public PasswordException(String message, Exception exception) {
		super(message, exception);
	}

}
