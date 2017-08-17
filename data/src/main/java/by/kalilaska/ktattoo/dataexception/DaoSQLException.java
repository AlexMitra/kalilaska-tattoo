package by.kalilaska.ktattoo.dataexception;

public class DaoSQLException extends Exception{

	public DaoSQLException() {
		super();		
	}

	public DaoSQLException(String message, Throwable cause) {
		super(message, cause);		
	}

	public DaoSQLException(String message) {
		super(message);		
	}

	public DaoSQLException(Throwable cause) {
		super(cause);		
	}

}
