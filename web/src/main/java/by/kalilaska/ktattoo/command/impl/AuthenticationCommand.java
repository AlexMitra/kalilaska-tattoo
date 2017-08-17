package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.manager.MessageManager;
import by.kalilaska.ktattoo.manager.PathBodyManager;
import by.kalilaska.ktattoo.manager.PathViewManager;
import by.kalilaska.ktattoo.pathlist.PathBodyList;
import by.kalilaska.ktattoo.service.AuthenticationService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class AuthenticationCommand implements IActionCommand{
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
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
		} catch (ViewSourceNotFoundException e) {
			// LOG			
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
            	
            	if(bodyManager != null) {
            		viewBody = bodyManager.getProperty(PathBodyList.LOGIN_VIEW_BODY);
            		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS,
                			service.getWrongMessage());
            	}           	
            	content.setTransition(TransitionType.SEND_REDIRECT);
            	
            }else if(personalAreaViewBean != null) {//SEND REDIRECT NE NADO!            	
            	view = defaultView;
            	viewBody = defaultViewBody;            	
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
            			personalAreaViewBean);
            }
            content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        }else {
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS,
        			MessageManager.getProperty(MessageNameList.NAME_OR_PASS_IS_NULL));//SDELAT' METHOD getProperty NON STATIC
        	
        }
    }

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
        return view;
	}

}
