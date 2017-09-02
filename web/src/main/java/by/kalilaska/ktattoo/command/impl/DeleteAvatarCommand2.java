package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class DeleteAvatarCommand2 implements IActionCommand{

	private AccountService accountService;
//	private String redirectedURI;
//	private String defaultURI;
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private PathBodyContentManager bodyContentManager;
	private String defaultView;
	private String view;
	private String viewBody;	
	private String bodyContent;
//	public DeleteAvatarCommand(AccountService accountService, String redirectedURI) {		
//		this.accountService = accountService;
//		this.defaultURI = redirectedURI;
//	}
	
	public DeleteAvatarCommand2(AccountService accountService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		
		this.accountService = accountService;
		initManager();
		
    	if(viewManager != null && viewPath != null) {
    		this.defaultView = viewManager.getProperty(viewPath);
    	}
    	if(bodyManager != null && viewBodyPath != null) {
    		this.viewBody = bodyManager.getProperty(viewBodyPath);
    	}
    	if(bodyContentManager != null && bodyContentPath != null) {
    		this.bodyContent = bodyContentManager.getProperty(bodyContentPath);
    	}
	}
	
    private void initManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
			bodyContentManager = new PathBodyContentManager();
			//messageManager = new WebMessageManager();
		} catch (ViewSourceNotFoundWebException e) {
			// LOG
		}
    }
	
	private void execute(SessionRequestContent content) {
		Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	AbstractPersonalAreaViewBean personalAreaViewBean = null;
    	
    	if(bean == null) {
    		//redirectedURI = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
    		view = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
    	}else {
    		//redirectedURI = defaultURI;
    		view = defaultView;
    		view = defaultView;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, null);
        	setContent(content);
    		boolean deleted = false;
    		
    		if(bean instanceof AbstractPersonalAreaViewBean) {
    			personalAreaViewBean = (AbstractPersonalAreaViewBean)bean;  			
    			deleted = accountService.deletePhotoUrl(personalAreaViewBean.getId());
    			System.out.println("in delete avatar command, deleted: " + deleted);
        		if(deleted == false) {
        			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_DELETE_AVATAR_FAILURE, 
        					accountService.getWrongMessage() != null ? accountService.getWrongMessage() : "");
    			}else {
    				personalAreaViewBean.setPhotoURL(null);
    				content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
    						personalAreaViewBean);
    			}
    		}
    	}
	}
	
    private void setContent(SessionRequestContent content) {    	
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}    	
    }

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
		//return redirectedURI;
		return view;
	}
}
