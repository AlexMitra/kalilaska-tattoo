package by.kalilaska.ktattoo.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.encoder.MD5Encoder;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class RegistrationServiceImpl implements RegistrationService {
	private final static Logger LOGGER = LogManager.getLogger(RegistrationServiceImpl.class);
	private AccountService accountService;
	private String worningMessage;

	public RegistrationServiceImpl(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public AbstractPersonalAreaViewBean registerAccount(String name, String email, String password,
			String confirmPassword) {		
		AbstractPersonalAreaViewBean viewBean = null;		
		
		if(name != null && FormDataValidator.validate(name, DataType.NAME) 
				&& password != null && FormDataValidator.validate(password, DataType.PASS) 
						&& email != null && FormDataValidator.validate(email, DataType.EMAIL) 
								&& confirmPassword != null && confirmPassword.equals(password)){
			
			List<AccountBean> accountCheckList = accountService.findAccountByNameOrEmailOrPass(name, email, password);			
			
			if(accountCheckList == null || accountCheckList.isEmpty()){
				password = MD5Encoder.encode(password);
				AccountBean accountBean = accountService.create(name, email, password);
				
				if(accountBean != null) {
					AccountRoleType roleType = AccountRoleType.valueOf(accountBean.getRole().toUpperCase());
					
					viewBean = roleType.getPersonalAreaViewBean();
					
					viewBean.setId(accountBean.getId());
					viewBean.setName(accountBean.getName());
					viewBean.setEmail(accountBean.getEmail());
					viewBean.setPhone(accountBean.getPhone());
					viewBean.setPhotoURL(accountBean.getPhotoURL());
					viewBean.setAllowed(accountBean.isAllowed());
					viewBean.setRole(accountBean.getRole());					
				}else {
					worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_ACCOUNT_UNKNOWN_ERROR);					
				}				
			}else {
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_ACCOUNT_DATA_ALREADY_EXISTS);				
			}
		}else {
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_ACCOUNT_DATA_INVALID);
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
