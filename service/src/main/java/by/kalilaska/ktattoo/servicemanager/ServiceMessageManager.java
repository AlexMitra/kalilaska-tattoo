package by.kalilaska.ktattoo.servicemanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.serviceexception.MessageFileNotFoundServiceException;
import by.kalilaska.ktattoo.servicename.ServicePropFileNameList;


public class ServiceMessageManager {
	private static ResourceBundle resourceBundle;

    public ServiceMessageManager() throws MessageFileNotFoundServiceException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(ServicePropFileNameList.SERVICE_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		throw new MessageFileNotFoundServiceException(e);
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
