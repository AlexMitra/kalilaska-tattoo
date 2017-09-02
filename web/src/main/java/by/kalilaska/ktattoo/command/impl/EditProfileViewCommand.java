package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class EditProfileViewCommand implements IActionCommand{
	private final static Logger LOGGER = LogManager.getLogger(EditProfileViewCommand.class);
	private TattooMasterService masterService;
	private TattooStyleService styleService;
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private String defaultView;
	private String view;
	private String viewBody;
	private PathBodyContentManager bodyContentManager;
	private String bodyContent;

    public EditProfileViewCommand(TattooMasterService masterService, TattooStyleService styleService, 
    		String viewPath, String viewBodyPath, String bodyContentPath) {
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
    		this.bodyContent = bodyContentManager.getProperty(bodyContentPath);
    	}
    }
    
    private void initManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
			bodyContentManager = new PathBodyContentManager();

		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
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
        	if(bean.getClass() == MasterPersonalAreaViewBean.class) {
        		MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
        		
        		TattooMasterBean masterBean = masterService.findMasterById(masterViewBean.getId());
        		masterViewBean.setAboutInfo(masterBean.getAboutInfo());
        		
        		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, masterViewBean);
        		
        		List<TattooStyleBean> allStyles = styleService.findAll();
        		
        		content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_ALL_TATTOO_STYLE_LIST, allStyles);
        	}
        	setContent(content);
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
        return view;
    }

}
