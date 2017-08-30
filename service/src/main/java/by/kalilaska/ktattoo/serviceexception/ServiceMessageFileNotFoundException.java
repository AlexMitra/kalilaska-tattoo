package by.kalilaska.ktattoo.serviceexception;

public class ServiceMessageFileNotFoundException extends Exception {

	public ServiceMessageFileNotFoundException() {		
	}

	public ServiceMessageFileNotFoundException(String message) {
		super(message);		
	}

	public ServiceMessageFileNotFoundException(Throwable cause) {
		super(cause);		
	}

	public ServiceMessageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);		
	}
}
