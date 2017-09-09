package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class SimpleViewBodyContentCommand implements IActionCommand {
	private final static Logger LOGGER = LogManager.getLogger(SimpleViewBodyContentCommand.class);

	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private PathBodyContentManager bodyContentManager;	

	protected String view;
	protected String viewBody;	
	protected String bodyContent;

	public SimpleViewBodyContentCommand(String viewPath, String viewBodyPath, String bodyContentPath) {
    	initManager();
    	if(viewManager != null && viewPath != null) {
    		this.view = viewManager.getProperty(viewPath);
    	}
    	if(bodyManager != null && viewBodyPath != null) {
    		this.viewBody = bodyManager.getProperty(viewBodyPath);
    	}
    	if(bodyContentManager != null && bodyContentPath != null) {
    		this.bodyContent = bodyContentManager.getProperty(bodyContentPath);
    	}
	}
	
    private void initManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
			bodyContentManager = new PathBodyContentManager();

		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		}
    }
    
    protected void handle(SessionRequestContent content) {    	
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);        
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    }

	@Override
	public String getView(SessionRequestContent content) {
		handle(content);
        return view;
	}
}
