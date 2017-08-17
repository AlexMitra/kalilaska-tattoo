package by.kalilaska.ktattoo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import by.kalilaska.ktattoo.pool.ConnectionPool;
import by.kalilaska.ktattoo.pool.ProxyConnection;

public class TransactionManager {
	private ProxyConnection connection;
	
    public void beginTransaction(AbstractDAO dao, AbstractDAO ... daos) {
    	connection = ConnectionPool.getInstance().getConnection();
    	if(connection != null) {
    		try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                //LOG
            }
            dao.setConnection(connection);
            for (AbstractDAO d : daos) {
                d.setConnection(connection);
            }
    	}        
    }
    
    public void endTransaction() {
    	if(connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                //LOG
            }            
            ConnectionPool.getInstance().releaseConnection(connection);           
    	}
    }
    
    public void commit() {    	
        try {
            connection.commit();
            
        } catch (SQLException e) {
            //LOG
        }        
    }
    
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            //LOG
        }
    }
}
