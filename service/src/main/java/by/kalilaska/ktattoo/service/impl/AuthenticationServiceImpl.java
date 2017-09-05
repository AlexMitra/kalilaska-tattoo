package by.kalilaska.ktattoo.service.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.encoder.MD5Encoder;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.AuthenticationService;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class AuthenticationServiceImpl implements AuthenticationService{
	private AccountService accountService;
	private TattooStyleService styleService;
	private String worningMessage;

	public AuthenticationServiceImpl(AccountService accountService, TattooStyleService styleService) {		
		this.accountService = accountService;
		this.styleService = styleService;
	}
	
	public AbstractPersonalAreaViewBean checkAccount(String name, String password) {
		AbstractPersonalAreaViewBean viewBean = null;		
		
		if(name != null && FormDataValidator.validate(name, DataType.NAME) 
				&& password != null && FormDataValidator.validate(password, DataType.PASS)){
			
			password = MD5Encoder.encode(password);			
			AccountBean accountBean = accountService.findAccountByName(name);			
			
			if(accountBean != null && name.equals(accountBean.getName()) && 
					password.equals(accountBean.getPassword())){
				
				AccountRoleType roleType = AccountRoleType.valueOf(accountBean.getRole().toUpperCase());
				
				viewBean = roleType.getPersonalAreaViewBean();
				
				viewBean.setId(accountBean.getId());
				viewBean.setName(accountBean.getName());
				viewBean.setEmail(accountBean.getEmail());
				viewBean.setPhone(accountBean.getPhone());
				viewBean.setPhotoURL(accountBean.getPhotoURL());
				viewBean.setAllowed(accountBean.isAllowed());
				viewBean.setRole(accountBean.getRole());
				
				if(roleType == AccountRoleType.MASTER) {
					MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)viewBean;
					List<TattooStyleBean> styles = styleService.findAllTattooStyleByMasterId(accountBean.getId());
					if(styles != null) {						
						masterViewBean.setStyles(styles);
					}
				}				
			}else {
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.AUTHENTICATION_FAILURE);
			}
		}else {
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.AUTHENTICATION_FAILURE);
		}
		return viewBean;		
	}

	@Override
	public String getWorningMessage() {
		String message = worningMessage;		
		worningMessage = null;
		return message;
	}
}
