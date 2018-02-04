package by.tr.web.service.exception.text;

import by.tr.web.service.exception.common.ServiceException;

public class TagException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public TagException() {

	}

	public TagException(String message) {
		super(message);
	}

	public TagException(Exception exception) {
		super(exception);
	}

	public TagException(String message, Exception exception) {
		super(message, exception);
	}

}
