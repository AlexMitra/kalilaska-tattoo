package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

/**
 * Created by lovcov on 23.07.2017.
 */
public class PersonalAreaViewCommand implements IActionCommand{
	private final static Logger LOGGER = LogManager.getLogger(PersonalAreaViewCommand.class);
	protected PathViewManager viewManager;
	protected PathBodyManager bodyManager;
	protected WebMessageManager messageManager;
	protected String defaultView;
	protected String view;
	protected String viewBody;
	protected PathBodyContentManager bodyContentManager;
	protected String bodyContent;

    public PersonalAreaViewCommand(String viewPath, String viewBodyPath, String bodyContentPath) {    	
    	initManager();
    	if(viewManager != null && viewPath != null) {
    		this.defaultView = viewManager.getProperty(viewPath);
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
			messageManager = new WebMessageManager();
		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		} catch (WebMessageFileNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for messages: " + e.getMessage());
		}
    }
    
    private void execute(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean == null) {
    		view = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
        	content.setTransition(TransitionType.SEND_REDIRECT);
    	}else {            
    		view = defaultView;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, null);
        	setContent(content);
    	}
    	
    }
    
    protected void setContent(SessionRequestContent content) {    	
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}    	
    }
    
    protected void makeWrongMessage(SessionRequestContent content, String attributeName, String messagePath) {
		if(messageManager != null) {
    		content.insertSessionAttribute(attributeName, messageManager.getProperty(messagePath));
    	}
	}
    
    @Override
    public String getView(SessionRequestContent content) {
        execute(content);
        return view;
    }
}
