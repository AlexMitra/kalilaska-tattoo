package by.kalilaska.ktattoo.webmanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webname.PropFileNameList;

/**
 * Created by lovcov on 13.07.2017.
 */
public class PathViewManager {
    private static ResourceBundle resourceBundle;

    public PathViewManager() throws ViewSourceNotFoundWebException {
    	try {
    		resourceBundle = ResourceBundle.getBundle(PropFileNameList.PATH_VIEW_FILE);
    	}catch(MissingResourceException e) {
    		throw new ViewSourceNotFoundWebException(e);
    	}
    	
    }
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
