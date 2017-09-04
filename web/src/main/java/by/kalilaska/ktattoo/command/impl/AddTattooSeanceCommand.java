package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

public class AddTattooSeanceCommand extends AbstractPrgCommand {
	
	private SeanceService seanceService;

	public AddTattooSeanceCommand(SeanceService seanceService, String redirectedURI) {
		super(redirectedURI);
		this.seanceService = seanceService;
	}
    
	@Override
    protected void handle(SessionRequestContent content) {
    	String[] masterIdArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_SEANCE_MASTER_ID);
        String[] masterNameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_SEANCE_MASTER_NAME);
        String[] dateArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_SEANCE_DATE);
        String[] durationArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_SEANCE_DURATION);
        
        String masterId = getFirstParameter(masterIdArr);
        String masterName = getFirstParameter(masterNameArr);
        String date = getFirstParameter(dateArr);
        String duration = getFirstParameter(durationArr);
        
        Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
        AbstractPersonalAreaViewBean viewBean = (AbstractPersonalAreaViewBean)bean;
        int cliendId = viewBean.getId();
        String clientName = viewBean.getName();
        
        SeanceBean seance = seanceService.create(cliendId, masterId, clientName, masterName, date, duration);
        
        if(seance != null) {
        	redirectedURI = defaultURI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
        }else {        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_ADD_SEANCE_FAILURE,
        			seanceService.getWorningMessage());
        	
        	redirectedURI = URINameList.PERSONAL_AREA_ADD_SEANCE_PAGE_URI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_ADD_SEANCE_VIEW_COMMAND);
		}        
    }
}
