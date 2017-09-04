package by.kalilaska.ktattoo.command.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

public class EditProfileCommand extends AbstractPrgCommand {

	private AccountService accountService;
	private TattooMasterService masterService;
	private TattooStyleService styleService;

	public EditProfileCommand(AccountService accountService, TattooMasterService masterService, 
			TattooStyleService styleService, String redirectedURI) {
		super(redirectedURI);
		this.accountService = accountService;
		this.masterService = masterService;
		this.styleService = styleService;
	}
    
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);    	
    	AbstractPersonalAreaViewBean abstractViewBean = (AbstractPersonalAreaViewBean)bean;

        String[] idArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_ID);
        String[] nameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_NAME);
        String[] emailArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_EMAIL);
        String[] phoneArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_PHONE);
        String[] aboutArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_ABOUT);
        String[] passArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_PASS);
        String[] confirmPassArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_CONFIRM_PASS);
        	
		String id = getFirstParameter(idArr);
		String name = getFirstParameter(nameArr);
		String email = getFirstParameter(emailArr);
		String phone = getFirstParameter(phoneArr);
		String pass = getFirstParameter(passArr);
		String confirmPass = getFirstParameter(confirmPassArr);
		
        AccountBean account = new AccountBean();
        account.setId(Integer.parseInt(id));
        account.setName(name);
        account.setEmail(email);
        account.setPhone(phone);
        account.setPassword(pass);
        account.setConfirmPassword(confirmPass);
        account.setRole(abstractViewBean.getRole());
        		
        boolean profileUpdated = accountService.updateProfile(account);
		if(profileUpdated) {
			abstractViewBean.setName(name);
			abstractViewBean.setEmail(email);
			abstractViewBean.setPhone(phone);
			
			if(abstractViewBean.getClass() == MasterPersonalAreaViewBean.class) {
				String about = getFirstParameter(aboutArr);
				List<TattooStyleBean> styleBeanList = updateMasterProfile(content, abstractViewBean);
				
	    		if(styleBeanList != null) {
	    			MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)abstractViewBean;
		    		masterViewBean.setAboutInfo(about);		    		
		    		masterViewBean.setStyles(styleBeanList);		    		
		    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, masterViewBean);
	    		}    		
			}else {
				content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, abstractViewBean);
			}
			redirectedURI = defaultURI;
			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
		}else {
			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_EDIT_PROFILE_FAILURE,
					accountService.getWorningngMessage());
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_EDIT_PROFILE_VIEW_COMMAND);
        	redirectedURI = URINameList.PERSONAL_AREA_EDIT_PROFILE_PAGE_URI;
		}
    }
    
    private List<TattooStyleBean> updateMasterProfile (SessionRequestContent content, 
    		AbstractPersonalAreaViewBean abstractViewBean) {
    	List<TattooStyleBean> styleBeanList = null;
    	
    	String[] idArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_ID);
    	String[] aboutArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_ABOUT);
    	String id = getFirstParameter(idArr);
    	String about = getFirstParameter(aboutArr);
		LinkedList<String> styles = new LinkedList<>();
		
    	for (String key : content.getRequestParameters().keySet()) {
			if(key.startsWith(RequestParamNameList.PARAMETER_FOR_EDIT_PROFILE_STYLE)) {
				String[] styleArr = content.getRequestParameters().get(key);
				if(styleArr!= null && styleArr.length > 0) {
					styles.add(getFirstParameter(styleArr));
				}
			}
		}
    	
    	TattooMasterBean masterBean = new TattooMasterBean();
    	masterBean.setId(Integer.parseInt(id));
		masterBean.setAboutInfo(about);
		masterBean.setStyleNames(styles);
		
		boolean masterProfileUpdated = masterService.updateMasterProfile(masterBean);
		if(masterProfileUpdated) {
			styleBeanList = styleService.updateAllTattooStyleByMasterId(masterBean);
		}
		
    	return styleBeanList;
    }
}
