package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.pathlist.PathBodyList;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class RegistrationAccountCommand implements IActionCommand {
	private final static Logger LOGGER = LogManager.getLogger(RegistrationAccountCommand.class);
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private WebMessageManager messageManager;
	private String defaultView;
    private String view;
    private String defaultViewBody;
    private String viewBody;
    private RegistrationService registrationService;    

	public RegistrationAccountCommand(RegistrationService registrationService, 
			String viewPath, String viewBodyPath) {
		this.registrationService = registrationService;		
		initManager();
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
			LOGGER.log(Level.WARN, "can not find configuration file for view creation: " + e.getMessage());
		} catch (WebMessageFileNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find creation file for messages: " + e.getMessage());
		}
    }
    
    private void execute(SessionRequestContent content) {
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
        	view = URINameList.REGISTRATION_PAGE_URI;
        	
        	if(bodyManager != null) {
        		viewBody = bodyManager.getProperty(PathBodyList.REGISTRATION_VIEW_BODY);
        		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_REGISTER_ACCOUNT_FAILURE,
        				registrationService.getWrongMessage());			
        	}
        	content.setTransition(TransitionType.SEND_REDIRECT);
		}else {
			view = defaultView;
        	viewBody = defaultViewBody;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
        			personalAreaViewBean); 
		}
		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
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
		execute(content);
		return view;
	}

}
