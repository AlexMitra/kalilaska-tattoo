package by.kalilaska.ktattoo.servicemanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.serviceexception.ServiceMessageFileNotFoundException;
import by.kalilaska.ktattoo.servicename.ServicePropFileNameList;


public class ServiceMessageManager {
	private static ResourceBundle resourceBundle;

    public ServiceMessageManager() throws ServiceMessageFileNotFoundException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(ServicePropFileNameList.SERVICE_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		throw new ServiceMessageFileNotFoundException(e);
    	}
    }
    public String getProperty(String key) {
    	String result = null;
    	if(resourceBundle.containsKey(key)){
    		result = resourceBundle.getString(key);
    	}
        return result;
    }
}
