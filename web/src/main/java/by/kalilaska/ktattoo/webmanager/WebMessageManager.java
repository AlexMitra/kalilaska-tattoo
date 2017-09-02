package by.kalilaska.ktattoo.webmanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundWebException;
import by.kalilaska.ktattoo.webname.PropFileNameList;

/**
 * Created by lovcov on 13.07.2017.
 */
public class WebMessageManager {
	private final static Logger LOGGER = LogManager.getLogger(WebMessageManager.class);
    private static ResourceBundle resourceBundle;

    public WebMessageManager() throws WebMessageFileNotFoundWebException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.WEB_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		throw new WebMessageFileNotFoundWebException(e);
    	}
    }
    
    private static void initManager() {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.WEB_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		LOGGER.log(Level.WARN, "can not find configuration file for messages: " + e.getMessage());
    	}
    }
    
    public String getProperty(String key) {
    	String result = null;
    	if(resourceBundle.containsKey(key)){
    		result = resourceBundle.getString(key);
    	}
        return result;
    }
    
    public static String getMessage(String key) {
    	String message = null;
    	if(resourceBundle == null) {
    		initManager();
    	}
    	if(resourceBundle != null && resourceBundle.containsKey(key)){
    		message = resourceBundle.getString(key);
    	}
    	return message;
    }
}
