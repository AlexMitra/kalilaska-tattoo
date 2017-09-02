package by.kalilaska.ktattoo.webexception;

public class WebMessageFileNotFoundWebException extends Exception {

	public WebMessageFileNotFoundWebException() {		
	}

	public WebMessageFileNotFoundWebException(String message) {
		super(message);		
	}

	public WebMessageFileNotFoundWebException(Throwable cause) {
		super(cause);		
	}

	public WebMessageFileNotFoundWebException(String message, Throwable cause) {
		super(message, cause);		
	}
}
