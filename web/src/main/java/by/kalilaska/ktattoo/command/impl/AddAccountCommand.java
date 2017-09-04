package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AddAccountCommand extends AbstractPrgCommand{

	private RegistrationService registrationService;
	private AccountService accountService;

	public AddAccountCommand(RegistrationService registrationService, AccountService accountService, 
			String redirectedURI) {		
		super(redirectedURI);
		this.registrationService = registrationService;
		this.accountService = accountService;
	}
    
    @Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean != null && bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	String[] nameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_NAME);
        	String[] emailArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_EMAIL);
        	String[] passArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_PASS);
        	String[] confirmPassArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_REGISTER_ACCOUNT_CONFIRM_PASS);
        	

        	String name = getFirstParameter(nameArr);
        	String email = getFirstParameter(emailArr);
        	String pass = getFirstParameter(passArr);
        	String confirmPass = getFirstParameter(confirmPassArr);            		
        		
        	AbstractPersonalAreaViewBean personalAreaViewBean = registrationService.registerAccount(name, email, pass, confirmPass);
        	if(personalAreaViewBean != null) {
        		AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
                List<AccountBean> accountBeanList = accountService.findAll();
                adminViewBean.setAccounts(accountBeanList);
                	
                content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);
        	}else {
        		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_CREATE_ACCOUNT_FAILURE, 
        				registrationService.getWorningMessage());
        	}
        	
        	redirectedURI = defaultURI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_ALL_ACCOUNTS_VIEW_COMMAND);
        }
    }
}