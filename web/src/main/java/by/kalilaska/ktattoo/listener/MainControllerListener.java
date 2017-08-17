package by.kalilaska.ktattoo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.kalilaska.ktattoo.service.BaseService;



@WebListener
public class MainControllerListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("in listener, start destroy");
		BaseService.closePool();
//		ConnectionPool pool = ConnectionPool.getInstance();
//		pool.removeConnections();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		ConnectionPool.initPool();
		BaseService.initPool();
		
	}

}
