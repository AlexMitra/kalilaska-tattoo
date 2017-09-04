package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class EditAccountCommand extends AbstractPrgCommand{
	
	private AccountService accountService;

	public EditAccountCommand(AccountService accountService, String redirectedURI) {		
		super(redirectedURI);		
		this.accountService = accountService;
	}
    
    @Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean != null && bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	String[] idArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_ID);
        	String[] nameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_NAME);
        	String[] emailArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_EMAIL);
        	String[] phoneArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_PHONE);
        	String[] roleArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_ROLE);
        	

        	String id = getFirstParameter(idArr);
        	String name = getFirstParameter(nameArr);
        	String email = getFirstParameter(emailArr);
        	String phone = getFirstParameter(phoneArr);
        	String role = getFirstParameter(roleArr);    		
        		
        	AccountBean account = new AccountBean();
        	account.setId(Integer.parseInt(id));
        	account.setName(name);
        	account.setEmail(email);
        	account.setPhone(phone);
        	account.setRole(role);
        		
        	boolean updated = accountService.updateAccount(account);
        		
        	if(updated) {
        		AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
                List<AccountBean> accountBeanList = accountService.findAll();
                adminViewBean.setAccounts(accountBeanList);
                	
                content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);
        	}else {
        		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_EDIT_ACCOUNT_FAILURE,
        				accountService.getWorningngMessage());
        	}
        }
    	
    	redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
    			CommandNameList.PERSONAL_AREA_ALL_ACCOUNTS_VIEW_COMMAND);
    }

}

