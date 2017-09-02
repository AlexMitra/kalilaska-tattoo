package by.kalilaska.ktattoo.serviceexception;

public class MessageFileNotFoundServiceException extends Exception {

	public MessageFileNotFoundServiceException() {		
	}

	public MessageFileNotFoundServiceException(String message) {
		super(message);		
	}

	public MessageFileNotFoundServiceException(Throwable cause) {
		super(cause);		
	}

	public MessageFileNotFoundServiceException(String message, Throwable cause) {
		super(message, cause);		
	}
}
