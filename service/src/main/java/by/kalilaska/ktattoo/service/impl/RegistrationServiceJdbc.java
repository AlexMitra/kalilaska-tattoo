package by.kalilaska.ktattoo.service.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.encoder.MD5Encoder;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.serviceexception.MessageFileNotFoundServiceException;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class RegistrationServiceJdbc implements RegistrationService {
	private final static Logger LOGGER = LogManager.getLogger(RegistrationServiceJdbc.class);
	private AccountService accountService;
	private FormDataValidator validator;
	private ServiceMessageManager messageManager;
	private String wrongMessage;

	public RegistrationServiceJdbc(AccountService accountService) {
		this.accountService = accountService;
		validator = new FormDataValidator();
		initManager();
	}
	
	private void initManager() {
		try {
			messageManager = new ServiceMessageManager();
		} catch (MessageFileNotFoundServiceException e) {
			LOGGER.log(Level.WARN, "can not init ServiceMessageManager: " + e.getMessage());
		}
	}

	@Override
	public AbstractPersonalAreaViewBean registerAccount(String name, String email, String password,
			String confirmPassword) {
		wrongMessage = null;
		AbstractPersonalAreaViewBean viewBean = null;		
		
		if(name != null && validator.validate(name, DataType.NAME) 
				&& password != null && validator.validate(password, DataType.PASS) 
						&& email != null && validator.validate(email, DataType.EMAIL) 
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
					wrongMessage = makeWrongMessage(ServiceMessageNameList.CREATE_ACCOUNT_UNKNOWN_ERROR);					
				}				
			}else {
				wrongMessage = makeWrongMessage(ServiceMessageNameList.CREATE_ACCOUNT_DATA_ALREADY_EXISTS);				
			}
		}else {
			wrongMessage = makeWrongMessage(ServiceMessageNameList.CREATE_ACCOUNT_DATA_INVALID);			
		}
		return viewBean;
	}
	
	private String makeWrongMessage(String messagePath) {
		String message = null;
		if(messageManager != null) {
			message = messageManager.getProperty(messagePath);
		}
		return message;
	}
	
	@Override
	public String getWrongMessage() {
		return wrongMessage;
	}

}
