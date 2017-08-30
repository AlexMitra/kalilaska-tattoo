package by.kalilaska.ktattoo.webmanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webname.PropFileNameList;

public class PathBodyManager {
    private static ResourceBundle resourceBundle;

    public PathBodyManager() throws ViewSourceNotFoundException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.PATH_BODY_FILE);
    	}catch(MissingResourceException e) {
    		throw new ViewSourceNotFoundException(e);
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
