package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.RoleBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.RoleService;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class PersonalAreaAllAccountsViewCommand extends PersonalAreaViewCommand {
	
	private AccountService accountService;
	private RoleService roleService;

	public PersonalAreaAllAccountsViewCommand(AccountService accountService, RoleService roleService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {		
		super(viewPath, viewBodyPath, bodyContentPath);
		this.accountService = accountService;
		this.roleService = roleService;    			
	}
    
    @Override
    protected void setContent(SessionRequestContent content) {
    	
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
        	AdminPersonalAreaViewBean adminViewBean = (AdminPersonalAreaViewBean)bean;
        	
        	List<RoleBean> roleBeanList = roleService.findAll();
        	List<AccountBean> accountBeanList = accountService.findAll();
        	adminViewBean.setRoles(roleBeanList);
        	adminViewBean.setAccounts(accountBeanList);
        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, adminViewBean);        	
        }
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}
    }
}
