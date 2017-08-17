package by.kalilaska.ktattoo.manager;

import java.util.ResourceBundle;

import by.kalilaska.ktattoo.webname.PropFileNameList;

/**
 * Created by lovcov on 13.07.2017.
 */
public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(PropFileNameList.MESSAGE_FILE);

    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
