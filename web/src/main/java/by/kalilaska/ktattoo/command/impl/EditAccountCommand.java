package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class EditAccountCommand extends PersonalAreaViewCommand{
	
	private AccountService accountService;

	public EditAccountCommand(AccountService accountService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		
		super(viewPath, viewBodyPath, bodyContentPath);		
		this.accountService = accountService;
	}
    
    @Override
    protected void setContent(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	String[] idArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_ID);
        	String[] nameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_NAME);
        	String[] emailArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_EMAIL);
        	String[] phoneArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_PHONE);
        	String[] roleArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_ACCOUNT_ROLE);
        	
        	if(idArr != null && idArr.length > 0 && 
        			nameArr != null && nameArr.length > 0 
        			&& emailArr != null && emailArr.length > 0 
        			&& phoneArr != null && phoneArr.length > 0 
        			&& roleArr != null && roleArr.length > 0) {
        		String id = idArr[0];
        		String name = nameArr[0];
        		String email = emailArr[0];
        		String phone = phoneArr[0];
        		String role = roleArr[0];            		
        		
        		AccountBean account = new AccountBean();
        		account.setId(Integer.parseInt(id));
        		account.setName(name);
        		account.setEmail(email);
        		account.setPhone(phone);
        		account.setRole(role);
        		
        		boolean flag = accountService.updateAccount(account);
        		
        		if(flag) {
        			AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
                	List<AccountBean> accountBeanList = accountService.findAll();
                	adminViewBean.setAccounts(accountBeanList);
                	
                	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);
        		}else {
        			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_EDIT_ACCOUNT_FAILURE,
        					accountService.getWrongMessage());
        		}
        	}else {
        		makeWrongMessage(content, SessionAttrNameList.ATTRIBUTE_FOR_EDIT_ACCOUNT_FAILURE, 
        				MessageNameList.SOME_DATA_IS_NULL);
        	}
        }
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}
    }
}
