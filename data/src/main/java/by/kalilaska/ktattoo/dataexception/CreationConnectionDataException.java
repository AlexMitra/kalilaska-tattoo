package by.kalilaska.ktattoo.dataexception;

public class CreationConnectionDataException extends Exception {

	public CreationConnectionDataException() {		
	}

	public CreationConnectionDataException(String message) {
		super(message);		
	}

	public CreationConnectionDataException(Throwable cause) {
		super(cause);		
	}

	public CreationConnectionDataException(String message, Throwable cause) {
		super(message, cause);		
	}
}
