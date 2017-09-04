package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AllTattooConsultationsViewCommand extends SimpleViewBodyContentCommand {

	private ConsultationService consultationService;
	
	public AllTattooConsultationsViewCommand(ConsultationService consultationService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		
		super(viewPath, viewBodyPath, bodyContentPath);
		this.consultationService = consultationService;
	}
    
	@Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean != null && bean.getClass() == MasterPersonalAreaViewBean.class) {
        	MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)bean;
        	
        	List<ConsultationBean> consultations = consultationService.findAllConsultationsByMasterId(masterViewBean.getId());
        	if(consultations != null) {
        		content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_ALL_CONSULTATION_LIST, consultations);
        	} 
    	}   	
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    }
}
