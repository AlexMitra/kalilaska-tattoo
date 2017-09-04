package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class DeleteAvatarCommand extends AbstractPrgCommand{

	private AccountService accountService;

	public DeleteAvatarCommand(AccountService accountService, String redirectedURI) {	
		super(redirectedURI);
		this.accountService = accountService;
	}
	
	@Override
	protected void handle(SessionRequestContent content) {
		Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	AbstractPersonalAreaViewBean personalAreaViewBean = null;
   		
    	boolean deleted = false;
    		
    	if(bean instanceof AbstractPersonalAreaViewBean) {
    		personalAreaViewBean = (AbstractPersonalAreaViewBean)bean;
    		deleted = accountService.deletePhotoUrl(personalAreaViewBean.getId());
    			
        	if(deleted == false) {
        		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_DELETE_AVATAR_FAILURE, 
        				accountService.getWorningngMessage());
    		}else {
    			personalAreaViewBean.setPhotoURL(null);
    			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, 
    					personalAreaViewBean);
    		}
    	}
    	redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        		CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
    }
}