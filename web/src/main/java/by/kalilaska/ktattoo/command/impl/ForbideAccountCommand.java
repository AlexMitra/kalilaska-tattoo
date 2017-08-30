package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class ForbideAccountCommand extends PersonalAreaViewCommand {
	
	private AccountService accountService;

	public ForbideAccountCommand(AccountService accountService, String viewPath, String viewBodyPath, 
			String bodyContentPath) {
		super(viewPath, viewBodyPath, bodyContentPath);
		this.accountService = accountService;
	}
    
    @Override
    protected void setContent(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	String[] paramArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_FORBIDE_ACCOUNT);
        	
        	if(paramArr != null && paramArr.length > 0) {
        		String accountId = paramArr[0];            		
        		boolean flag = accountService.forbideAccountById(Integer.valueOf(accountId));
        		if(flag) {
        			AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
                	List<AccountBean> accountBeanList = accountService.findAll();
                	adminViewBean.setAccounts(accountBeanList);
                	
                	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);
        		}else {
        			makeWrongMessage(content, SessionAttrNameList.ATTRIBUTE_FOR_FORBIDE_ACCOUNT_FAILURE, 
        					MessageNameList.FORBIDE_ACCOUNT_ERROR);
        		}
        	}else {
        		makeWrongMessage(content, SessionAttrNameList.ATTRIBUTE_FOR_FORBIDE_ACCOUNT_FAILURE, 
        				MessageNameList.SOME_DATA_IS_NULL);
        	}
        }
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}
    }

}
