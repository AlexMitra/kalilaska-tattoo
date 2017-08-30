package by.kalilaska.ktattoo.webmanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundException;
import by.kalilaska.ktattoo.webname.PropFileNameList;

/**
 * Created by lovcov on 13.07.2017.
 */
public class WebMessageManager {
    private static ResourceBundle resourceBundle;

    public WebMessageManager() throws WebMessageFileNotFoundException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.WEB_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		throw new WebMessageFileNotFoundException(e);
    	}
    }
    
    private static void initManager() {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.WEB_MESSAGE_FILE);
    	}catch(MissingResourceException e) {
    		//LOG
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
