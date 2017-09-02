package by.kalilaska.ktattoo.dataexception;

public class PoolSizeUnadmittedDataException extends Exception {

	public PoolSizeUnadmittedDataException() {		
	}

	public PoolSizeUnadmittedDataException(String message) {
		super(message);		
	}

	public PoolSizeUnadmittedDataException(Throwable cause) {
		super(cause);		
	}

	public PoolSizeUnadmittedDataException(String message, Throwable cause) {
		super(message, cause);		
	}
}
