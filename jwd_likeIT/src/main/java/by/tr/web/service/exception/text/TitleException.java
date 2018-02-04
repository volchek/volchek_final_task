package by.tr.web.service.exception.text;

import by.tr.web.service.exception.common.ServiceException;

public class TitleException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public TitleException() {

	}

	public TitleException(String message) {
		super(message);
	}

	public TitleException(Exception exception) {
		super(exception);
	}

	public TitleException(String message, Exception exception) {
		super(message, exception);
	}

}
