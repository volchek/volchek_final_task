package by.tr.web.dao.exception;

public class DaoLoginException extends DaoException {

	private static final long serialVersionUID = 1L;

	public DaoLoginException() {
	}

	public DaoLoginException(String message) {
		super(message);
	}

	public DaoLoginException(Exception exception) {
		super(exception);
	}

	public DaoLoginException(String message, Exception exception) {
		super(message, exception);
	}
}
