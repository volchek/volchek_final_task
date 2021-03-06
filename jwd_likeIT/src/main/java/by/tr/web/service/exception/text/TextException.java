package by.tr.web.service.exception.text;

import by.tr.web.service.exception.common.ServiceException;

public class TextException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public TextException() {

	}

	public TextException(String message) {
		super(message);
	}

	public TextException(Exception exception) {
		super(exception);
	}

	public TextException(String message, Exception exception) {
		super(message, exception);
	}

}
