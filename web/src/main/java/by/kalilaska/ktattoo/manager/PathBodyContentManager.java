package by.kalilaska.ktattoo.manager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webname.PropFileNameList;

public class PathBodyContentManager {
	private static ResourceBundle resourceBundle;
	
	public PathBodyContentManager() throws ViewSourceNotFoundException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.PATH_BODY_CONTENT_FILE);
    	}catch(MissingResourceException e) {
    		throw new ViewSourceNotFoundException();
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