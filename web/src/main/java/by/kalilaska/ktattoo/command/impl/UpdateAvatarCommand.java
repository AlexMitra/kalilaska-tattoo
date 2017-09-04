package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class UpdateAvatarCommand extends AbstractPrgCommand {

	private AccountService accountService;

	public UpdateAvatarCommand(AccountService accountService, String redirectedURI) {
		super(redirectedURI);
		this.accountService = accountService;
	}
	
	@Override
	protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	AbstractPersonalAreaViewBean personalAreaViewBean = null;
   		
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
        				accountService.getWorningngMessage());
    		}else {
    			personalAreaViewBean.setPhotoURL(photoUrl);
    			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
    					personalAreaViewBean);	
    		}
    	}
    	redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        		CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
    }
}
