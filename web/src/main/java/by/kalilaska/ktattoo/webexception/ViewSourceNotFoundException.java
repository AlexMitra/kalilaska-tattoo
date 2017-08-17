package by.kalilaska.ktattoo.webexception;

public class ViewSourceNotFoundException extends Exception {

	public ViewSourceNotFoundException() {
		
	}

	public ViewSourceNotFoundException(String message) {
		super(message);		
	}

	public ViewSourceNotFoundException(Throwable cause) {
		super(cause);		
	}

	public ViewSourceNotFoundException(String message, Throwable cause) {
		super(message, cause);		
	}

}
