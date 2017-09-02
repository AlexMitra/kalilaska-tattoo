package by.kalilaska.ktattoo.webexception;

public class ViewSourceNotFoundWebException extends Exception {

	public ViewSourceNotFoundWebException() {
		
	}

	public ViewSourceNotFoundWebException(String message) {
		super(message);		
	}

	public ViewSourceNotFoundWebException(Throwable cause) {
		super(cause);		
	}

	public ViewSourceNotFoundWebException(String message, Throwable cause) {
		super(message, cause);		
	}

}
