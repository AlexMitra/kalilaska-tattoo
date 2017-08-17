package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AddAccountCommand extends PersonalAreaViewCommand{

	private RegistrationService registrationService;
	private AccountService accountService;

	public AddAccountCommand(RegistrationService registrationService, AccountService accountService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		
		super(viewPath, viewBodyPath, bodyContentPath);
		this.registrationService = registrationService;
		this.accountService = accountService;
	}
    
    @Override
    protected void setContent(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	String[] nameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_NAME);
        	String[] emailArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_EMAIL);
        	String[] passArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_PASS);
        	String[] confirmPassArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_CONFIRM_PASS);
        	
        	if(nameArr != null && nameArr.length > 0 
        			&& emailArr != null && emailArr.length > 0 
        			&& passArr != null && passArr.length > 0 
        			&& confirmPassArr != null && confirmPassArr.length > 0) {
        		String name = nameArr[0];
        		String email = emailArr[0];
        		String pass = passArr[0];
        		String confirmPass = confirmPassArr[0];            		
        		
        		AbstractPersonalAreaViewBean personalAreaViewBean = registrationService.registerAccount(name, email, pass, confirmPass);
        		if(personalAreaViewBean != null) {
        			AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
                	List<AccountBean> accountBeanList = accountService.findAll();
                	adminViewBean.setAccounts(accountBeanList);
                	
                	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);
        		}else {
        			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_CREATE_ACCOUNT_FAILURE,
                            registrationService.getWrongMessage());
        		}
        	}else {
        		//SOME MESSAGE
        	}
        }
    	
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}
    }
}
