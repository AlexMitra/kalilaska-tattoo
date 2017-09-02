package by.kalilaska.ktattoo.serviceexception;

public class ConnectionPoolInitServiceException extends Exception {

	public ConnectionPoolInitServiceException() {		
	}

	public ConnectionPoolInitServiceException(String message) {
		super(message);		
	}

	public ConnectionPoolInitServiceException(Throwable cause) {
		super(cause);		
	}

	public ConnectionPoolInitServiceException(String message, Throwable cause) {
		super(message, cause);		
	}
}
