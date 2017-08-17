package by.kalilaska.ktattoo.service;

import by.kalilaska.ktattoo.pool.ConnectionPool;

/**
 * Created by lovcov on 23.07.2017.
 */
public interface BaseService {
    static void initPool() {
    	ConnectionPool pool = ConnectionPool.getInstance();
    	if(pool == null) {
    		//throw exception
    	}
    }

    static void closePool() {
    	ConnectionPool pool = ConnectionPool.getInstance();
    	pool.removeConnections();
    }

}
