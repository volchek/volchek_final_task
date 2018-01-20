package by.tr.web.service.exception.common;

public class FatalServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public FatalServiceException() {
	}

	public FatalServiceException(String message) {
		super(message);
	}

	public FatalServiceException(Exception exception) {
		super(exception);
	}

	public FatalServiceException(String message, Exception exception) {
		super(message, exception);
	}
	
}
