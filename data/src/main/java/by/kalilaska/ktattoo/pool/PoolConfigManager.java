package by.kalilaska.ktattoo.pool;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import by.kalilaska.ktattoo.dataname.DBConfigNameList;



public class PoolConfigManager {
	private final static String DEFAULT_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DEFAULT_URL = "jdbc:mysql://localhost/kalilaska_tattoo";
	private final static String DEFAULT_USER = "root";
	private final static String DEFAULT_PASS = "root";
	
	private static ResourceBundle resourceBundle;

	private static String driver;
	private static String url;
	private static String user;
	private static String password;	
	
    public PoolConfigManager() {
    	try {
    		resourceBundle = ResourceBundle.getBundle(DBConfigNameList.DB_CONFIG_FILE_NAME);
    		
    		driver = getProperty(DBConfigNameList.DRIVER_PROPERTY);		
    		url = getProperty(DBConfigNameList.URL_PROPERTY);
    		user = getProperty(DBConfigNameList.USER_PROPERTY);
    		password = getProperty(DBConfigNameList.PASS_PROPERTY);
    		
    	}catch (MissingResourceException e) {
			if(driver == null || driver.isEmpty()) {
				driver = DEFAULT_JDBC_DRIVER;
			}
			if(url == null || url.isEmpty()) {
				url = DEFAULT_URL;
			}
			if(user == null || user.isEmpty()) {
				user = DEFAULT_USER;
			}
			
			if(password == null || password.isEmpty()) {
				password = DEFAULT_PASS;
			}
		}    	
	}

	public static String getProperty(String key) {
    	String result = null;
    	if(resourceBundle.containsKey(key)){
    		result = resourceBundle.getString(key);
    	}
        return result;
    }
    
	String getDriver() {		
		return driver;
	}
	
	String getUrl(){		
		return url;
	}
	
	String getUser() {		
		return user;
	}
	
	String getPassword() {		
		return password;
	}

}
