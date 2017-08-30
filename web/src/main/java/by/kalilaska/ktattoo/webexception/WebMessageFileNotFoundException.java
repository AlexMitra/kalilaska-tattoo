package by.kalilaska.ktattoo.webexception;

public class WebMessageFileNotFoundException extends Exception {

	public WebMessageFileNotFoundException() {		
	}

	public WebMessageFileNotFoundException(String message) {
		super(message);		
	}

	public WebMessageFileNotFoundException(Throwable cause) {
		super(cause);		
	}

	public WebMessageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);		
	}
}
