package by.tr.web.dao.impl.mySqlUtil.mySqlException;

public class MySqlException extends Exception {

	private static final long serialVersionUID = 1L;

	public MySqlException() {
	}

	public MySqlException(String message) {
		super(message);
	}

	public MySqlException(Exception exception) {
		super(exception);
	}

	public MySqlException(String message, Exception exception) {
		super(message, exception);
	}

}
