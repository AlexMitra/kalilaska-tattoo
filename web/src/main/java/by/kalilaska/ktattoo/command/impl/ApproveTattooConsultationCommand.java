package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class ApproveTattooConsultationCommand extends AbstractPrgCommand {	
	
	private ConsultationService consultationService;

	public ApproveTattooConsultationCommand(ConsultationService consultationService, String redirectedURI) {
		super(redirectedURI);
		this.consultationService = consultationService;		
	}
    
	@Override
    protected void handle(SessionRequestContent content) {    	
    	
    	String idArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_APPROVE_CONSULTATION_ID);
    	String id = getFirstParameter(idArr);
    	
        boolean approved = consultationService.approveConsultationById(id);        
        if(!approved) {        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_APPROVE_CONSULTATION_FAILURE,
        			consultationService.getWorningMessage());
        }
        
        redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
    			CommandNameList.PERSONAL_AREA_ALL_CONSULTATIONS_VIEW_COMMAND);
    }
}
