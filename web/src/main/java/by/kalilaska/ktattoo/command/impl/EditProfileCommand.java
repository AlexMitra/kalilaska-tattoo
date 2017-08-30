package by.kalilaska.ktattoo.command.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class EditProfileCommand implements IActionCommand {

	private AccountService accountService;
	private TattooMasterService masterService;
	private TattooStyleService styleService;
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private WebMessageManager messageManager;
	private String defaultView;
	private String view;
	private String viewBody;
	private PathBodyContentManager bodyContentManager;	
	private String bodyContent;

	public EditProfileCommand(AccountService accountService, TattooMasterService masterService, TattooStyleService styleService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		this.accountService = accountService;
		this.masterService = masterService;
		this.styleService = styleService;
		initManager();
		if(viewManager != null && viewPath != null) {
			this.defaultView = viewManager.getProperty(viewPath);
		}
		if(bodyManager != null && viewBodyPath != null) {
			this.viewBody = bodyManager.getProperty(viewBodyPath);
		}
		if(bodyContentManager != null && bodyContentPath != null) {
			bodyContent = bodyContentManager.getProperty(bodyContentPath);
		}	
		
	}
	
    private void initManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
			bodyContentManager = new PathBodyContentManager();
			messageManager = new WebMessageManager();
		} catch (ViewSourceNotFoundException e) {
			// LOG
		}catch (WebMessageFileNotFoundException e) {
			// LOG			
		}
    }
    
    protected void execute(SessionRequestContent content) {    	
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);    	
    	if(bean == null) {
    		view = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
        	content.setTransition(TransitionType.SEND_REDIRECT);
    	}else {            
    		view = defaultView;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, null);
        	setContent(content);
    	}    	
    }
    
    protected void setContent(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	AbstractPersonalAreaViewBean abstractViewBean = null;
    	if(bean instanceof AbstractPersonalAreaViewBean) {
    		abstractViewBean = (AbstractPersonalAreaViewBean)bean;
    	}
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
	    			MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)abstractViewBean;
		    		masterViewBean.setAboutInfo(about);
		    		
		    		List<TattooStyleBean> styleBeanList = styleService.updateAllTattooStyleByMasterId(masterBean);		    		
		    		masterViewBean.setStyles(styleBeanList);
		    		
		    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, masterViewBean);
	    		}	    		
			}else {
				content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, abstractViewBean);
			}        	
		}else {
			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_EDIT_PROFILE_FAILURE,
					accountService.getWrongMessage());
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_EDIT_PROFILE_VIEW_COMMAND);
        	view = URINameList.PERSONAL_AREA_EDIT_PROFILE_PAGE_URI;
        	content.setTransition(TransitionType.SEND_REDIRECT);
		}
        
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}
    }
    
    private String getFirstParameter(String arr[]) {
    	String parameter = null;
    	if(arr != null && arr.length > 0) {
    		parameter = arr[0];
    	}
    	return parameter;
    }
    
    protected void makeWrongMessage(SessionRequestContent content, String attributeName, String messagePath) {
		if(messageManager != null) {
  		content.insertSessionAttribute(attributeName, messageManager.getProperty(messagePath));
		}
	}

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
        return view;
	}
}
