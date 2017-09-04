package by.kalilaska.ktattoo.servicemanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.servicename.ServicePropFileNameList;


public class ServiceMessageManager {
	private final static Logger LOGGER = LogManager.getLogger(ServiceMessageManager.class);
	private static ResourceBundle resourceBundle = initManager();

//    public ServiceMessageManager() throws MessageFileNotFoundServiceException {
//    	System.out.println("in ServiceMessageManager()");
//    	try {
//    		resourceBundle = ResourceBundle.getBundle(ServicePropFileNameList.SERVICE_MESSAGE_FILE);
//    	}catch(MissingResourceException e) {
//    		throw new MessageFileNotFoundServiceException(e);
//    	}
//    }
    
    private static ResourceBundle initManager() {    	
    	try {
    		resourceBundle = ResourceBundle.getBundle(ServicePropFileNameList.SERVICE_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		LOGGER.log(Level.WARN, "can not find configuration file for messages: " + e.getMessage());
    	}
    	return resourceBundle;
    }
    
//    public String getProperty(String key) {
//    	String result = null;
//    	if(resourceBundle.containsKey(key)){
//    		result = resourceBundle.getString(key);
//    	}
//        return result;
//    }
    
    public static String getMessage(String key) {
    	String message = null;

    	if(resourceBundle != null && resourceBundle.containsKey(key)){
    		message = resourceBundle.getString(key);
    	}
    	return message;
    }
}
