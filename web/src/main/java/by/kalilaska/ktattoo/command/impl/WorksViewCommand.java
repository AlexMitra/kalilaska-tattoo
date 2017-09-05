package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class WorksViewCommand extends SimpleViewBodyContentCommand {

	private TattooPhotoService photoService;
	
	public WorksViewCommand(TattooPhotoService photoService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		super(viewPath, viewBodyPath, bodyContentPath);
		this.photoService = photoService;
	}
    
	@Override
	protected void handle(SessionRequestContent content) {
		Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
		
		if(bean != null && bean.getClass() == MasterPersonalAreaViewBean.class) {
			MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
	    	List<TattooPhotoBean> photoList = photoService.findAllTattooPhotoByMasterIdTransacted(masterViewBean.getId());
	    	masterViewBean.setPhotos(photoList);
	    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, masterViewBean);
		}
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    }

	@Override
	public String getView(SessionRequestContent content) {
		handle(content);
        return view;
	}
}
