package by.kalilaska.ktattoo.dataexception;

public class SQLDataException extends Exception{

	public SQLDataException() {
		super();		
	}

	public SQLDataException(String message, Throwable cause) {
		super(message, cause);		
	}

	public SQLDataException(String message) {
		super(message);		
	}

	public SQLDataException(Throwable cause) {
		super(cause);		
	}

}
