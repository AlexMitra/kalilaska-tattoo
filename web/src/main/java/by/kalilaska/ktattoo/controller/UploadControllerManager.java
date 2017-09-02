package by.kalilaska.ktattoo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.webname.UploadControllerConfigNameList;

public class UploadControllerManager {
	private final static Logger LOGGER = LogManager.getLogger(UploadControllerManager.class);
	private final static String DEFAULT_UPLOAD_FOLDER = "images";
	private final static String DEFAULT_ROOT_PATH = "D:/JAVA/EPAM/kalilaska-tattoo/web/src/main/webapp/";
	private final static String DEFAULT_FILE_MAX_SIZE = "1536000";
	private final static String DEFAULT_REQUEST_MAX_SIZE = "1920000";
	private final static String DEFAULT_ADMITTED_FILE_SUFFIX = "JPG";
	private final static String DEFAULT_PHOTO_URL_SEPARATOR = "/";
	private final static String SUFFIX_VALUES_SEPARATOR = "_";
	
	private static ResourceBundle resourceBundle;

	private static String uploadFolder;
	private static String rootPath;
	private static String fileMaxSize;
	private static String requestMaxSize;
	private static List<String> admittedFileSuffixList;	
	private static String photoUrlSeparator;
	
    public UploadControllerManager() {
    	try {
    		resourceBundle = ResourceBundle.getBundle(UploadControllerConfigNameList.CONFIG_FILE_NAME);
    		
    		uploadFolder = getProperty(UploadControllerConfigNameList.UPLOAD_FOLDER_PROPERTY);		
    		rootPath = getProperty(UploadControllerConfigNameList.ROOT_PATH_PROPERTY);
    		fileMaxSize = getProperty(UploadControllerConfigNameList.FILE_MAX_SIZE_PROPERTY);
    		requestMaxSize = getProperty(UploadControllerConfigNameList.REQUEST_MAX_SIZE_PROPERTY);
    		String value = getProperty(UploadControllerConfigNameList.ADMITTED_FILE_SUFFIX_LIST_PROPERTY);
    		
    		admittedFileSuffixList = new ArrayList<String>(Arrays.asList(value.split(SUFFIX_VALUES_SEPARATOR)));    		
    		photoUrlSeparator = getProperty(UploadControllerConfigNameList.PHOTO_URL_SEPARATOR_PROPERTY);
    		
    	}catch (MissingResourceException e) {
    		LOGGER.log(Level.ERROR, "can not find UploadController config file: " + e.getMessage());
    		
			if(uploadFolder == null || uploadFolder.isEmpty()) {
				uploadFolder = DEFAULT_UPLOAD_FOLDER;
			}
			if(rootPath == null || rootPath.isEmpty()) {
				rootPath = DEFAULT_ROOT_PATH;
			}
			if(fileMaxSize == null || fileMaxSize.isEmpty()) {
				fileMaxSize = DEFAULT_FILE_MAX_SIZE;
			}
			
			if(requestMaxSize == null || requestMaxSize.isEmpty()) {
				requestMaxSize = DEFAULT_REQUEST_MAX_SIZE;
			}
			
			if(admittedFileSuffixList == null || admittedFileSuffixList.isEmpty()) {
				admittedFileSuffixList = new ArrayList<>();
				admittedFileSuffixList.add(DEFAULT_ADMITTED_FILE_SUFFIX);
			}
			
			if(photoUrlSeparator == null || photoUrlSeparator.isEmpty()) {
				photoUrlSeparator = DEFAULT_PHOTO_URL_SEPARATOR;
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

	String getUploadFolder() {
		return uploadFolder;
	}

	String getRootPath() {
		return rootPath;
	}

	String getFileMaxSize() {
		return fileMaxSize;
	}

	String getRequestMaxSize() {
		return requestMaxSize;
	}

	List<String> getAdmittedFileSuffixList() {
		return admittedFileSuffixList;
	}

	String getPhotoUrlSeparator() {
		return photoUrlSeparator;
	}

}
