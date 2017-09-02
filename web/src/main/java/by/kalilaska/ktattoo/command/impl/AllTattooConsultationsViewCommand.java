package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class AllTattooConsultationsViewCommand implements IActionCommand {
	private final static Logger LOGGER = LogManager.getLogger(AllTattooConsultationsViewCommand.class);
	
	private ConsultationService consultationService;
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private String defaultView;
	private String view;
	private String viewBody;
	private PathBodyContentManager bodyContentManager;
	private String bodyContent;
	
	public AllTattooConsultationsViewCommand(ConsultationService consultationService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		
		this.consultationService = consultationService;
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
    	}else if(!bean.getClass().equals(MasterPersonalAreaViewBean.class)){            
    		view = URINameList.PERSONAL_AREA_PAGE_URI;
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
        	content.setTransition(TransitionType.SEND_REDIRECT);
    	}else {
    		view = defaultView;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, null);        	
        	setContent(content);
    	}
    }
    
    private void setContent(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
    	
    	List<ConsultationBean> consultations = consultationService.findAllConsultationsByMasterId(masterViewBean.getId());
    	if(consultations != null) {
    		content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_ALL_CONSULTATION_LIST, consultations);
    	}    	
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
