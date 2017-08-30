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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.dataname.DBConfigNameList;

public class ConnectionPool {
	private final static Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
	
	private static AtomicBoolean instanced = new AtomicBoolean(false);
	private static ConnectionPool instance;
	
	private static Lock lock = new ReentrantLock();
	
	private BlockingQueue<ProxyConnection> connectionQueue;
	
	private ConnectionPool(){
		connectionQueue = new ArrayBlockingQueue<>(DBConfigNameList.POOL_SIZE);
		initQueue();
	}
	
	public static ConnectionPool getInstance() {
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

	
	private void initQueue() {
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
			System.out.println(connectionQueue);//UDALIT!!!!!
		} catch (SQLException e) {			
			//Throw Exception
		} catch (ClassNotFoundException e) {			
			//Throw Exception
		}			
	}

	
	public ProxyConnection getConnection() {
		ProxyConnection connection = null;
		try {
			connection = connectionQueue.take();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		return connection;		
	}
	
	public void releaseConnection(ProxyConnection connection) {		
		try {
			if(connection != null) {
				connectionQueue.put(connection);
			}			
		} catch (InterruptedException e) {			
			//LOG
		}
	}
	
	public void removeConnections() {
		for (int i = 0; i < DBConfigNameList.POOL_SIZE; i++) {				
			try {
				ProxyConnection connection = connectionQueue.take();
				connection.closeConnection();
			} catch (SQLException | InterruptedException e) {					
				//LOG
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
			//LOG
		}
	}
}
