package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class ApproveAllTattooConsultationsCommand extends AbstractPrgCommand {	

	private ConsultationService consultationService;
	
	public ApproveAllTattooConsultationsCommand(ConsultationService consultationService, String redirectedURI) {
		super(redirectedURI);
		this.consultationService = consultationService;
	}
    
	@Override
    protected void handle(SessionRequestContent content) {    	
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	if(bean != null && bean.getClass() == MasterPersonalAreaViewBean.class) {
    		MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;        	
            boolean approved = consultationService.approveAllConsultationByMasterId(masterViewBean.getId());
            if(!approved) {        	
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_APPROVE_CONSULTATION_FAILURE,
            			consultationService.getWorningMessage());
            }
    	}
    	
        redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
    			CommandNameList.PERSONAL_AREA_ALL_CONSULTATIONS_VIEW_COMMAND);
    }
}
