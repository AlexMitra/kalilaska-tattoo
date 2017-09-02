package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AuthenticationService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class AuthenticationCommand implements IActionCommand{
	private final static Logger LOGGER = LogManager.getLogger(AuthenticationCommand.class);
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private WebMessageManager messageManager;
	private String defaultView;
    private String view;
    private String defaultViewBody;
    private String viewBody;
    private AuthenticationService service;    
	
    public AuthenticationCommand(AuthenticationService service, String viewPath, String viewBodyPath) {
    	initManager();
    	this.service = service;
    	if(viewManager != null && viewPath != null) {
    		defaultView = viewManager.getProperty(viewPath);
    	}
    	if(bodyManager != null && viewBodyPath != null) {
    		defaultViewBody = bodyManager.getProperty(viewBodyPath);
    	}
    }
    
    private void initManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
			messageManager = new WebMessageManager();
		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		} catch (WebMessageFileNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for messages: " + e.getMessage());
		}
    }
    
    private void execute(SessionRequestContent content) {    	
    	String nameArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_AUTHENTICATION_ACCOUNT_NAME);
        String passwordArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_AUTHENTICATION_ACCOUNT_PASS);
        String name = null;
        String password = null;
        if(nameArr != null && nameArr.length > 0 && passwordArr != null && passwordArr.length > 0) {
        	name = nameArr[0];
        	password = passwordArr[0];
        	
        	AbstractPersonalAreaViewBean personalAreaViewBean = service.checkAccount(name, password);
    		
            if (personalAreaViewBean == null) {
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
            			CommandNameList.LOGIN_VIEW_COMMAND);
            	view = URINameList.LOGIN_PAGE_URI;
            	
//            	if(bodyManager != null) {
//            		viewBody = bodyManager.getProperty(PathBodyList.LOGIN_VIEW_BODY);
//            		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS,
//                			service.getWrongMessage());
//            	}
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS,
            			service.getWrongMessage());            	
            	
            }else if(personalAreaViewBean != null) {          	
//            	view = defaultView;
//            	viewBody = defaultViewBody;
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
            			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
            	view = URINameList.PERSONAL_AREA_PAGE_URI;
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
            			personalAreaViewBean);            	
            }
            content.setTransition(TransitionType.SEND_REDIRECT);
            //content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        }else {
        	makeWrongMessage(content, SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS, 
        			MessageNameList.NAME_OR_PASS_IS_NULL);
        }
    }

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
        return view;
	}
	
	private void makeWrongMessage(SessionRequestContent content, String attributeName, String messagePath) {
		if(messageManager != null) {
    		content.insertSessionAttribute(attributeName, messageManager.getProperty(messagePath));
    	}
	}
}
