package by.kalilaska.ktattoo.service.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.encoder.MD5Encoder;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.AuthenticationService;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class AuthenticationServiceJdbc implements AuthenticationService{
	private AccountService accountService;
	private ConsultationService consultationService;
	private SeanceService seanceService;
	private TattooStyleService styleService;
	private FormDataValidator validator;

	public AuthenticationServiceJdbc(AccountService accountService, ConsultationService consultationService, 
			SeanceService seanceService, TattooStyleService styleService) {		
		this.accountService = accountService;
		this.consultationService = consultationService;
		this.seanceService = seanceService;
		this.styleService = styleService;
		validator = new FormDataValidator();
	}	
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public AbstractPersonalAreaViewBean checkAccount(String name, String password) {
		AbstractPersonalAreaViewBean viewBean = null;		
		
		if(name != null && validator.validate(name, DataType.NAME) 
				&& password != null && validator.validate(password, DataType.PASS)){
			
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
				
				List<ConsultationBean> consultations = 
						consultationService.findAllConsultationsByClientId(accountBean.getId());
				
				if(consultations != null) {
					viewBean.setConsultations(consultations);
				}
				
				List<SeanceBean> seances = seanceService.findAllSeancesByClientId(accountBean.getId());
				
				if(seances != null) {
					viewBean.setSeances(seances);
				}
				
				if(roleType == AccountRoleType.MASTER) {
					List<TattooStyleBean> styles = styleService.findAllTattooStyleByMasterId(accountBean.getId());
					if(styles != null) {
						MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)viewBean;
						masterViewBean.setStyles(styles);
					}
				}
				//System.out.println(viewBean);
			}
		}
		return viewBean;		
	}

	@Override
	public String getWrongMessage() {		
		return "Invalid Name or Password";
	}

}
