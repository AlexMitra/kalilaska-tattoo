package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AllowAccountCommand extends AbstractPrgCommand {
	
	private AccountService accountService;

	public AllowAccountCommand(AccountService accountService, String redirectedURI) {
		super(redirectedURI);
		this.accountService = accountService;
	}
    
    @Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	String[] idArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ALLOW_ACCOUNT);
        	
        	if(idArr != null && idArr.length > 0) {
        		String accountId = idArr[0];
        		boolean allowed = accountService.allowAccountById(Integer.valueOf(accountId));
        		if(allowed) {
        			AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
                	List<AccountBean> accountBeanList = accountService.findAll();
                	adminViewBean.setAccounts(accountBeanList);
                	
                	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);
        		}else {
        			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_ALLOW_ACCOUNT_FAILURE, 
        					WebMessageManager.getMessage(MessageNameList.ALLOW_ACCOUNT_ERROR));
        		}
        	}else {
        		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_ALLOW_ACCOUNT_FAILURE, 
    					WebMessageManager.getMessage(MessageNameList.SOME_DATA_IS_NULL));
        	}
        }
    	
    	redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
    			CommandNameList.PERSONAL_AREA_ALL_ACCOUNTS_VIEW_COMMAND);
    }
}
