package by.kalilaska.ktattoo.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class AuthenticationServiceImpl implements AuthenticationService{
	private final static Logger LOGGER = LogManager.getLogger(AuthenticationServiceImpl.class);
	private AccountService accountService;
	private ConsultationService consultationService;
	private SeanceService seanceService;
	private TattooStyleService styleService;	
	
//	private ServiceMessageManager messageManager;

	public AuthenticationServiceImpl(AccountService accountService, ConsultationService consultationService, 
			SeanceService seanceService, TattooStyleService styleService) {		
		this.accountService = accountService;
		this.consultationService = consultationService;
		this.seanceService = seanceService;
		this.styleService = styleService;		
//		initManager();
	}
	
//	private void initManager() {
//		try {
//			messageManager = new ServiceMessageManager();
//		} catch (MessageFileNotFoundServiceException e) {
//			LOGGER.log(Level.WARN, "can not init ServiceMessageManager: " + e.getMessage());
//		}
//	}
	
//	private String makeWorningMessage(String messagePath) {
//		String message = null;
//		if(messageManager != null) {
//			message = messageManager.getProperty(messagePath);
//		}
//		return message;
//	}
	
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
				
				List<ConsultationBean> consultations = 
						consultationService.findAllApprovedConsultationsByClientId(accountBean.getId());
				
				if(consultations != null) {
					viewBean.setConsultations(consultations);
				}
				
				List<SeanceBean> seances = seanceService.findAllApprovedSeancesByClientId(accountBean.getId());
				
				if(seances != null) {
					viewBean.setSeances(seances);
				}
				
				if(roleType == AccountRoleType.MASTER) {
					MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)viewBean;
					List<TattooStyleBean> styles = styleService.findAllTattooStyleByMasterId(accountBean.getId());
					if(styles != null) {						
						masterViewBean.setStyles(styles);
					}
					
					List<ConsultationBean> unapprovedConsultations = 
							consultationService.findAllUnapprovedConsultationsByMasterId(accountBean.getId());
					
					if(unapprovedConsultations != null) {
						masterViewBean.setUnapprovedConsultations(unapprovedConsultations);
					}
					
					List<SeanceBean> unapprovedSeances = 
							seanceService.findAllUnapprovedSeancesByMasterId(accountBean.getId());
					
					if(unapprovedSeances != null) {
						masterViewBean.setUnapprovedSeances(unapprovedSeances);
					}
				}				
			}
		}
		return viewBean;		
	}

	@Override
	public String getWorningMessage() {		
//		return makeWorningMessage(ServiceMessageNameList.AUTHENTICATION_FAILURE);		
		return ServiceMessageManager.getMessage(ServiceMessageNameList.AUTHENTICATION_FAILURE);
	}

}
