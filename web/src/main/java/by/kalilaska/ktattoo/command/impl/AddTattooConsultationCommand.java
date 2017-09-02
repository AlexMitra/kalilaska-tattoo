package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class AddTattooConsultationCommand implements IActionCommand {
	private final static Logger LOGGER = LogManager.getLogger(AddTattooConsultationCommand.class);
	
	private ConsultationService consultationService;
	private String redirectedURI;
	private String defaultURI;
	
	public AddTattooConsultationCommand(ConsultationService consultationService, String redirectedURI) {
		this.consultationService = consultationService;
		this.defaultURI = redirectedURI;
		
	}
	
    private void defineWay(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean == null) {
    		redirectedURI = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);        	
    	}else {    		
            execute(content);                		
    	}
    	content.setTransition(TransitionType.SEND_REDIRECT);
    }
    
    private void execute(SessionRequestContent content) {
    	String[] masterIdArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_CONSULTATION_MASTER_ID);
        String[] masterNameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_CONSULTATION_MASTER_NAME);
        String[] dateArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_CONSULTATION_DATE);
        
        String masterId = getFirstParameter(masterIdArr);
        String masterName = getFirstParameter(masterNameArr);
        String date = getFirstParameter(dateArr);
        
        Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
        AbstractPersonalAreaViewBean viewBean = (AbstractPersonalAreaViewBean)bean;
        int cliendId = viewBean.getId();
        String clientName = viewBean.getName();
        
        ConsultationBean consultation = consultationService.create(cliendId, masterId, clientName, masterName, date);
        
        if(consultation != null) {
        	redirectedURI = defaultURI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
        }else {        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_ADD_CONSULTATION_FAILURE,
        			consultationService.getWorningMessage());
        	
        	redirectedURI = URINameList.PERSONAL_AREA_ADD_CONSULTATION_PAGE_URI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_ADD_CONSULTATION_VIEW_COMMAND);
		}
    }
    
    private String getFirstParameter(String arr[]) {
    	String parameter = null;
    	if(arr != null && arr.length > 0) {
    		parameter = arr[0];
    	}
    	return parameter;
    }
    
	@Override
	public String getView(SessionRequestContent content) {
		defineWay(content);
		return redirectedURI;
	}

}
