package by.tr.web.service.exception;

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
