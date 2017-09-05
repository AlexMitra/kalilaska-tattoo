package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AddWorkPhotoCommand extends AbstractPrgCommand {
	
	private TattooPhotoService photoService;

	public AddWorkPhotoCommand(TattooPhotoService photoService, String redirectedURI) {
		super(redirectedURI);
		this.photoService = photoService;
	}
    
    @Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean != null && bean.getClass().equals(MasterPersonalAreaViewBean.class)) {
    		MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
    		String photoUrl = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_WORK_PHOTO_URL);
    		Boolean isDone = (Boolean)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_WORK_PHOTO_IS_DONE);
    		int masterId = masterViewBean.getId();
    		
    		TattooPhotoBean newWorkPhoto = photoService.create(photoUrl, masterId, isDone);
    		
    		if(newWorkPhoto == null) {
    			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_UPDATE_AVATAR_FAILURE, 
    					photoService.getWorningngMessage());
    		}
        	
        	redirectedURI = defaultURI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_WORKS_VIEW_COMMAND);
        }
    }
}
