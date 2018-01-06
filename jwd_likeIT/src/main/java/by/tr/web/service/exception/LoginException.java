package by.tr.web.service.exception;

public class LoginException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public LoginException() {
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Exception exception) {
		super(exception);
	}

	public LoginException(String message, Exception exception) {
		super(message, exception);
	}
	
}
