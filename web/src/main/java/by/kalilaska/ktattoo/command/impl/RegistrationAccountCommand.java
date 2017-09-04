package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class RegistrationAccountCommand implements IActionCommand {

    private RegistrationService registrationService;
    private String redirectedURI;
	private String defaultURI;

	public RegistrationAccountCommand(RegistrationService registrationService, String redirectedURI) {
		this.registrationService = registrationService;
		this.defaultURI = redirectedURI;
	}
    
    private void handle(SessionRequestContent content) {
    	String nameArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_NAME);
    	String emailArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_EMAIL);
        String passArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_PASS);
        String confirmPassArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_CONFIRM_PASS);
        
        String name = getFirstParameter(nameArr);
        String email = getFirstParameter(emailArr);
        String pass = getFirstParameter(passArr);
        String confirmPass = getFirstParameter(confirmPassArr);
        
        AbstractPersonalAreaViewBean personalAreaViewBean = registrationService.registerAccount(name, email, pass, confirmPass);
        
		if(personalAreaViewBean == null) {
			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.REGISTRATION_VIEW_COMMAND);
			redirectedURI = URINameList.REGISTRATION_PAGE_URI;
			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_REGISTER_ACCOUNT_FAILURE,
    				registrationService.getWorningMessage());
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
