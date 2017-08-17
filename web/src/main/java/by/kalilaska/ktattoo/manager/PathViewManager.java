package by.kalilaska.ktattoo.manager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webname.PropFileNameList;

/**
 * Created by lovcov on 13.07.2017.
 */
public class PathViewManager {
    private static ResourceBundle resourceBundle;

    public PathViewManager() throws ViewSourceNotFoundException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.PATH_VIEW_FILE);
    	}catch(MissingResourceException e) {
    		throw new ViewSourceNotFoundException();
    	}
    	
    }
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
