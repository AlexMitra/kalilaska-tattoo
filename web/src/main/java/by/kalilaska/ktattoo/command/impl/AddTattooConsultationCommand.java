package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

public class AddTattooConsultationCommand extends AbstractPrgCommand {
	
	private ConsultationService consultationService;
	
	public AddTattooConsultationCommand(ConsultationService consultationService, String redirectedURI) {
		super(redirectedURI);
		this.consultationService = consultationService;

	}
    
    @Override
    protected void handle(SessionRequestContent content) {
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
}
