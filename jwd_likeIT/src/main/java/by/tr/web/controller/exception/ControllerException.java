package by.tr.web.controller.exception;

public class ControllerException extends Exception {

	private static final long serialVersionUID = 1L;

	public ControllerException() {
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Exception ex) {
		super(ex);
	}

	public ControllerException(String message, Exception ex) {
		super(message, ex);
	}

}
