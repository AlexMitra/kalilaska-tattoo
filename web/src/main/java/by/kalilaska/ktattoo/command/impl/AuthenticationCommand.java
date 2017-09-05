package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AuthenticationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
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

        name = getFirstParameter(nameArr);
        password = getFirstParameter(passwordArr);
        	
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
        content.setTransition(TransitionType.SEND_REDIRECT);
    }
    
    private String getFirstParameter(String arr[]) {
    	String parameter = null;
    	if(arr != null && arr.length > 0) {
    		parameter = arr[0];
    	}
    	return parameter;
    }

	@Override
	public String getView(SessionRequestContent content) {
		handle(content);
        return redirectedURI;
	}
}
