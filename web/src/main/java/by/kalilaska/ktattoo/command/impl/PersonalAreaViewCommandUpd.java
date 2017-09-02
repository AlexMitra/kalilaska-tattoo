package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webexception.WebMessageFileNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyContentManager;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

/**
 * Created by lovcov on 23.07.2017.
 */
public class PersonalAreaViewCommandUpd implements IActionCommand{
	private final static Logger LOGGER = LogManager.getLogger(PersonalAreaViewCommand.class);
	
	private ConsultationService consultationService;
	private SeanceService seanceService;
	protected PathViewManager viewManager;
	protected PathBodyManager bodyManager;
	protected WebMessageManager messageManager;
	protected String defaultView;
	protected String view;
	protected String viewBody;
	protected PathBodyContentManager bodyContentManager;
	protected String bodyContent;

    public PersonalAreaViewCommandUpd(ConsultationService consultationService, SeanceService seanceService, 
    		String viewPath, String viewBodyPath, String bodyContentPath) {
    	this.consultationService = consultationService;
    	this.seanceService = seanceService;
    	
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
			messageManager = new WebMessageManager();
		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		} catch (WebMessageFileNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for messages: " + e.getMessage());
		}
    }
    
    private void execute(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean == null) {
    		view = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
        	content.setTransition(TransitionType.SEND_REDIRECT);
    	}else {
    		if(bean instanceof AbstractPersonalAreaViewBean) {
    			AbstractPersonalAreaViewBean viewBean = (AbstractPersonalAreaViewBean)bean;
    			List<ConsultationBean> consultations = 
    					consultationService.findAllConsultationsByClientId(viewBean.getId());
    			List<SeanceBean> seances = seanceService.findAllSeancesByClientId(viewBean.getId());
    			viewBean.setConsultations(consultations);
    			viewBean.setSeances(seances);
    			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, viewBean);
    		}
    		
    		view = defaultView;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, null);
        	setContent(content);
    	}
    	
    }
    
    protected void setContent(SessionRequestContent content) {    	
    	if(bodyContent != null) {
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    	}    	
    }
    
    protected void makeWrongMessage(SessionRequestContent content, String attributeName, String messagePath) {
		if(messageManager != null) {
    		content.insertSessionAttribute(attributeName, messageManager.getProperty(messagePath));
    	}
	}
    
    @Override
    public String getView(SessionRequestContent content) {
        execute(content);
        return view;
    }
}
