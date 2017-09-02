package by.kalilaska.ktattoo.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.dataexception.CreationConnectionDataException;
import by.kalilaska.ktattoo.dataexception.JdbcDriverNotFoundDataException;
import by.kalilaska.ktattoo.dataexception.PoolSizeUnadmittedDataException;
import by.kalilaska.ktattoo.pool.ConnectionPool;
import by.kalilaska.ktattoo.pool.ProxyConnection;

public class TransactionManager {
	private final static Logger LOGGER = LogManager.getLogger(TransactionManager.class);
	private ProxyConnection connection;
	
    public void beginTransaction(AbstractDAO dao, AbstractDAO ... daos) {
    	try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (JdbcDriverNotFoundDataException | CreationConnectionDataException | PoolSizeUnadmittedDataException e) {
			LOGGER.log(Level.ERROR, "can not get connectionPool: " + e.getMessage());
		}
    	if(connection != null) {
    		try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
            	LOGGER.log(Level.ERROR, "can not begin transaction and make setAutoCommit(false): " + e.getMessage());
            }
            dao.setConnection(connection);
            for (AbstractDAO d : daos) {
            	if(d != null) {
            		d.setConnection(connection);
            	}                
            }
    	}        
    }
    
    public void endTransaction() {
    	if(connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Can not end transaction and make setAutoCommit(true): " + e.getMessage());
            }            
            try {
				ConnectionPool.getInstance().releaseConnection(connection);
			} catch (JdbcDriverNotFoundDataException | CreationConnectionDataException | PoolSizeUnadmittedDataException e) {
				LOGGER.log(Level.ERROR, "can not get connectionPool: " + e.getMessage());
			}
    	}
    }
    
    public void commit() {    	
        try {
            connection.commit();            
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can not commit transaction: " + e.getMessage());
        }        
    }
    
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "can not rollback transaction: " + e.getMessage());
        }
    }
}
