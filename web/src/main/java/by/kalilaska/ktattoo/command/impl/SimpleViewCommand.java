package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

/**
 * Created by lovcov on 23.07.2017.
 */
public class SimpleViewCommand implements IActionCommand{
	private final static Logger LOGGER = LogManager.getLogger(SimpleViewCommand.class);
	protected PathViewManager viewManager;
	protected PathBodyManager bodyManager;
	protected String view;
	protected String viewBody;
	
    public SimpleViewCommand(String viewPath) {    	
    	initViewManager();
    	if(viewManager != null && viewPath != null) {
    		this.view = viewManager.getProperty(viewPath);
    	}
    }

    public SimpleViewCommand(String viewPath, String viewBodyPath) {    	
    	initAllManager();
    	if(viewManager != null && viewPath != null) {
    		this.view = viewManager.getProperty(viewPath);
    	}    	
    	if(bodyManager != null && viewBodyPath != null) {
    		this.viewBody = bodyManager.getProperty(viewBodyPath);    		
    	}
    }
    
    private void initViewManager() {
    	try {
			viewManager = new PathViewManager();			
		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		}
    }
    
    private void initAllManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		}
    }
    
    protected void handle(SessionRequestContent content) {    	
        if(viewBody != null){        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        }        
    }

    @Override
    public String getView(SessionRequestContent content) {
        handle(content);
        return view;
    }
}
