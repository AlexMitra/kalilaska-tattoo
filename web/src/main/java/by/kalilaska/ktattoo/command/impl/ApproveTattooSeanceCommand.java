package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class ApproveTattooSeanceCommand extends AbstractPrgCommand {	
	
	private SeanceService seanceService;

	public ApproveTattooSeanceCommand(SeanceService seanceService, String redirectedURI) {
		super(redirectedURI);
		this.seanceService = seanceService;
	}
    
	@Override
    protected void handle(SessionRequestContent content) {
    	
    	String idArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_APPROVE_SEANCE_ID);
    	String costArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_APPROVE_SEANCE_COST);
    	String id = getFirstParameter(idArr);
    	String cost = getFirstParameter(costArr);
    	
        boolean approved = seanceService.approveSeanceById(id, cost);
        if(!approved) {        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_APPROVE_SEANCE_FAILURE,
        			seanceService.getWorningMessage());
        }
        
        redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
    			CommandNameList.PERSONAL_AREA_ALL_SEANCES_VIEW_COMMAND);
    }
}
