package by.kalilaska.ktattoo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.poolservice.ConnectionPoolService;
import by.kalilaska.ktattoo.serviceexception.ConnectionPoolInitServiceException;



@WebListener
public class MainControllerListener implements ServletContextListener{
	private final static Logger LOGGER = LogManager.getLogger(MainControllerListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
		ConnectionPoolService.closePool();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			ConnectionPoolService.initPool();
		} catch (ConnectionPoolInitServiceException e) {
			LOGGER.log(Level.ERROR, "connection pool did not initialize! " + e.getMessage());
		}		
	}
}
