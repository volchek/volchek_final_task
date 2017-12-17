package by.tr.web.dao.exception;

public class FatalDaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public FatalDaoException() {
	}
		
	public FatalDaoException(String message) {
		super(message);
	}

	public FatalDaoException(Exception exception) {
		super(exception);
	}

	public FatalDaoException(String message, Exception exception) {
		super(message, exception);
	}

}
