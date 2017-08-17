package by.kalilaska.ktattoo.service.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.encoder.MD5Encoder;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.RegistrationService;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class RegistrationServiceJdbc implements RegistrationService {
	
	private AccountService accountService;
	private FormDataValidator validator;
	private String wrongMessage;

	public RegistrationServiceJdbc(AccountService accountService) {
		this.accountService = accountService;
		validator = new FormDataValidator();
	}

	@Override
	public AbstractPersonalAreaViewBean registerAccount(String name, String email, String password,
			String confirmPassword) {
		wrongMessage = null;
		AbstractPersonalAreaViewBean viewBean = null;		
		
		if(name != null && validator.validate(name, DataType.NAME) 
				&& password != null && validator.validate(password, DataType.PASS) 
						&& email != null && validator.validate(email, DataType.EMAIL) 
								&& confirmPassword.equals(password)){
			
			List<AccountBean> nameEmailCheckList = accountService.findAccountByNameOrEmail(name, email);			
			
			if(nameEmailCheckList == null || nameEmailCheckList.isEmpty()){
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
					wrongMessage = "can not create account, something wrong";
				}				
			}else {
				wrongMessage = "can not create account, name or email already exists";
			}
		}else {
			wrongMessage = "can not create account, name or email or password is invalid";
		}
		return viewBean;
	}
	
	@Override
	public String getWrongMessage() {
		return wrongMessage;
	}

}
