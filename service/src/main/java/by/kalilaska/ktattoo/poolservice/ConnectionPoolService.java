package by.kalilaska.ktattoo.poolservice;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.dataexception.CreationConnectionDataException;
import by.kalilaska.ktattoo.dataexception.JdbcDriverNotFoundDataException;
import by.kalilaska.ktattoo.dataexception.PoolSizeUnadmittedDataException;
import by.kalilaska.ktattoo.pool.ConnectionPool;
import by.kalilaska.ktattoo.serviceexception.ConnectionPoolInitServiceException;

public class ConnectionPoolService {
	private final static Logger LOGGER = LogManager.getLogger(ConnectionPoolService.class);
	
    public static void initPool() throws ConnectionPoolInitServiceException {
    	ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
		} catch (JdbcDriverNotFoundDataException | CreationConnectionDataException | PoolSizeUnadmittedDataException e) {
			throw new ConnectionPoolInitServiceException(e);
		}
    }

    public static void closePool() {
    	ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
		} catch (JdbcDriverNotFoundDataException | CreationConnectionDataException | PoolSizeUnadmittedDataException e) {
			LOGGER.log(Level.ERROR, "can not close connectionPool: " + e.getMessage());
		}
    	pool.removeConnections();
    }

}
