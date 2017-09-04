package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AuthenticationService;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class AuthenticationCommand implements IActionCommand{

    private AuthenticationService service;
    private String redirectedURI;
	private String defaultURI;
	
    public AuthenticationCommand(AuthenticationService service, String redirectedURI) {
    	this.service = service;
    	this.defaultURI = redirectedURI;
    }
    
    private void handle(SessionRequestContent content) {    	
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
            	redirectedURI = URINameList.LOGIN_PAGE_URI;

            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS,
            			service.getWorningMessage());            	
            	
            }else {
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
            			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
            	redirectedURI = defaultURI;
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
            			personalAreaViewBean);            	
            }            
        }else {
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.LOGIN_VIEW_COMMAND);
        	redirectedURI = URINameList.LOGIN_PAGE_URI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_INVALID_NAME_PASS, 
        			WebMessageManager.getMessage(MessageNameList.NAME_OR_PASS_IS_NULL));
        }
        content.setTransition(TransitionType.SEND_REDIRECT);
    }

	@Override
	public String getView(SessionRequestContent content) {
		handle(content);
        return redirectedURI;
	}
}
