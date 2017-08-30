package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class UpdateAvatarCommand implements IActionCommand {

	private AccountService accountService;
	private String redirectedURI;
	private String defaultURI;

	public UpdateAvatarCommand(AccountService accountService, String redirectedURI) {
		this.accountService = accountService;
		this.defaultURI = redirectedURI;
	}
	
	private void execute(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	AbstractPersonalAreaViewBean personalAreaViewBean = null;
    	
    	if(bean == null) {
    		redirectedURI = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);        	
    	}else {
    		redirectedURI = defaultURI;    		
    		boolean updated = false;
    		if(bean instanceof AbstractPersonalAreaViewBean) {
    			personalAreaViewBean = (AbstractPersonalAreaViewBean)bean;
    			String photoUrl = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_UPDATED_PHOTO_URL);
    			
    			AccountBean accountBean = new AccountBean();
    			accountBean.setId(personalAreaViewBean.getId());
    			accountBean.setPhotoURL(photoUrl);    			
    			updated = accountService.updatePhotoUrl(accountBean);
    			
        		if(updated == false) {
        			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_UPDATE_AVATAR_FAILURE, 
        					accountService.getWrongMessage());
    			}else {
    				personalAreaViewBean.setPhotoURL(photoUrl);
    				content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
    						personalAreaViewBean);	
    			}
    		}
    	}
	}

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
		return redirectedURI;
	}

}
