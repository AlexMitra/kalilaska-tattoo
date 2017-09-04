package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class EditProfileViewCommand extends SimpleViewBodyContentCommand{

	private TattooMasterService masterService;
	private TattooStyleService styleService;

    public EditProfileViewCommand(TattooMasterService masterService, TattooStyleService styleService, 
    		String viewPath, String viewBodyPath, String bodyContentPath) {
    	super(viewPath, viewBodyPath, bodyContentPath);
    	this.masterService = masterService;
    	this.styleService = styleService;
    }
    
    @Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
          
        if(bean != null && bean.getClass() == MasterPersonalAreaViewBean.class) {
        	MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
        		
        	TattooMasterBean masterBean = masterService.findMasterById(masterViewBean.getId());
        	masterViewBean.setAboutInfo(masterBean.getAboutInfo());
        		
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, masterViewBean);
        		
        	List<TattooStyleBean> allStyles = styleService.findAll();        		
        	content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_ALL_TATTOO_STYLE_LIST, allStyles);
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
