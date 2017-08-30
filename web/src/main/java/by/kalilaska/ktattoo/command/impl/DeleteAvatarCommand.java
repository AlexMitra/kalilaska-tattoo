package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class DeleteAvatarCommand implements IActionCommand{

	private AccountService accountService;
	private String redirectedURI;
	private String defaultURI;

	public DeleteAvatarCommand(AccountService accountService, String redirectedURI) {		
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

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
		return redirectedURI;		
	}
}
