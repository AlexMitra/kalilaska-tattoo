package by.kalilaska.ktattoo.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.dataexception.CreationConnectionDataException;
import by.kalilaska.ktattoo.dataexception.JdbcDriverNotFoundDataException;
import by.kalilaska.ktattoo.dataexception.PoolSizeUnadmittedDataException;
import by.kalilaska.ktattoo.dataname.DBConfigNameList;

public class ConnectionPool {
	private final static Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
	
	private static AtomicBoolean instanced = new AtomicBoolean(false);
	private static ConnectionPool instance;
	
	private static Lock lock = new ReentrantLock();
	
	private static BlockingQueue<ProxyConnection> connectionQueue;
	
	private ConnectionPool() throws JdbcDriverNotFoundDataException, 
	CreationConnectionDataException, PoolSizeUnadmittedDataException{
		
		connectionQueue = new ArrayBlockingQueue<>(DBConfigNameList.POOL_SIZE);
		initQueue();
	}
	
	public static ConnectionPool getInstance() throws JdbcDriverNotFoundDataException, 
	CreationConnectionDataException, PoolSizeUnadmittedDataException {
		if(!instanced.get()) {
			lock.lock();
			try {
				if(instance == null) {
					instance = new ConnectionPool();
					instanced.set(true);					
				}
			}finally {
				lock.unlock();
			}
		}
		return instance;
	}

	
	private void initQueue() throws JdbcDriverNotFoundDataException, 
	CreationConnectionDataException, PoolSizeUnadmittedDataException {
		
		Connection connection = null;				      
		try {			
			PoolConfigManager poolConfigManager = new PoolConfigManager();
			
			Class.forName(poolConfigManager.getDriver());
			
			for (int i = 0; i < DBConfigNameList.POOL_SIZE; i++) {				
				connection = DriverManager.getConnection(
						poolConfigManager.getUrl(), 
						poolConfigManager.getUser(), 
						poolConfigManager.getPassword());
				connectionQueue.add(new ProxyConnection(connection));
			}
			if(connectionQueue.size() < DBConfigNameList.ADMITTED_POOL_SIZE) {
				throw new PoolSizeUnadmittedDataException("connection pool size is so few: " + connectionQueue.size());
			}
			LOGGER.log(Level.INFO, "connectionPool size after initialization: " + connectionQueue.size());
		} catch (SQLException e) {			
			throw new CreationConnectionDataException(e);
		} catch (ClassNotFoundException e) {			
			throw new JdbcDriverNotFoundDataException(e);
		}			
	}

	
	public ProxyConnection getConnection() {
		ProxyConnection connection = null;
		try {
			if(connectionQueue != null) {
				connection = connectionQueue.take();
			}			
		} catch (InterruptedException e) {			
			LOGGER.log(Level.ERROR, "can not take connetion from connectionQueue: " + e.getMessage());
		}
		return connection;		
	}
	
	public void releaseConnection(ProxyConnection connection) {		
		try {
			if(connection != null) {
				connectionQueue.put(connection);
			}			
		} catch (InterruptedException e) {			
			LOGGER.log(Level.ERROR, "can not put connetion into connectionQueue: " + e.getMessage());
		}
	}
	
	public void removeConnections() {
		LOGGER.log(Level.INFO, "connectionPool size before close: " + connectionQueue.size());
		for (int i = 0; i < DBConfigNameList.POOL_SIZE; i++) {				
			try {
				ProxyConnection connection = connectionQueue.take();
				connection.closeConnection();
			} catch (SQLException | InterruptedException e) {					
				LOGGER.log(Level.ERROR, "can not close connetions in connectionPool: " + e.getMessage());
			}
		}
		checkOutDrivers();		
	}
	
	private void checkOutDrivers() {
		try {
		    Enumeration<Driver> drivers = DriverManager.getDrivers();
		    while (drivers.hasMoreElements()) {
		        Driver driver = drivers.nextElement();
		        DriverManager.deregisterDriver(driver);
		    }
		} catch (SQLException e) {
			LOGGER.log(Level.ERROR, "can not check out drivers after closing all connetions: " + e.getMessage());
		}
	}
}
