package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AllTattooSeancesViewCommand extends SimpleViewBodyContentCommand {
	
	private SeanceService seanceService;

	public AllTattooSeancesViewCommand(SeanceService seanceService, String viewPath, String viewBodyPath, String bodyContentPath) {
		super(viewPath, viewBodyPath, bodyContentPath);
		this.seanceService = seanceService;
	}
    
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean != null && bean.getClass() == MasterPersonalAreaViewBean.class) {
        	MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
        	
        	List<SeanceBean> seances = seanceService.findAllSeancesByMasterId(masterViewBean.getId());
        	if(seances != null) {
        		content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_ALL_SEANCE_LIST, seances);
        	} 
    	}
    	
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);   	
    }
}
