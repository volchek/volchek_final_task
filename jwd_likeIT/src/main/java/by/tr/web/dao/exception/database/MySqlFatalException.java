package by.tr.web.dao.exception.database;

public class MySqlFatalException extends Exception {

	private static final long serialVersionUID = 1L;

	public MySqlFatalException() {

	}

	public MySqlFatalException(String message) {
		super(message);
	}

	public MySqlFatalException(Exception exception) {
		super(exception);
	}

	public MySqlFatalException(String message, Exception exception) {
		super(message, exception);
	}

}
