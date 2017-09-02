package by.kalilaska.ktattoo.dataexception;

public class JdbcDriverNotFoundDataException extends Exception {

	public JdbcDriverNotFoundDataException() {		
	}

	public JdbcDriverNotFoundDataException(String message) {
		super(message);		
	}

	public JdbcDriverNotFoundDataException(Throwable cause) {
		super(cause);		
	}

	public JdbcDriverNotFoundDataException(String message, Throwable cause) {
		super(message, cause);		
	}
}
