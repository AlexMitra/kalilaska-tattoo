package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class PersonalAreaViewCommand extends SimpleViewBodyContentCommand{
	
	private ConsultationService consultationService;
	private SeanceService seanceService;


    public PersonalAreaViewCommand(ConsultationService consultationService, SeanceService seanceService, 
    		String viewPath, String viewBodyPath, String bodyContentPath) {
    	super(viewPath, viewBodyPath, bodyContentPath);
    	this.consultationService = consultationService;
    	this.seanceService = seanceService;
    }
    
    @Override
    protected void handle(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
 		 
    	if(bean != null && bean instanceof AbstractPersonalAreaViewBean) {
    		AbstractPersonalAreaViewBean viewBean = (AbstractPersonalAreaViewBean)bean;
    		List<ConsultationBean> consultations = 
    				consultationService.findAllApprovedConsultationsByClientId(viewBean.getId());
    		List<SeanceBean> seances = seanceService.findAllApprovedSeancesByClientId(viewBean.getId());
    		viewBean.setConsultations(consultations);
    		viewBean.setSeances(seances);
    			
    		if(bean.getClass().equals(MasterPersonalAreaViewBean.class)) {
        		MasterPersonalAreaViewBean masterViewBean = (MasterPersonalAreaViewBean)viewBean;
        			
        		List<ConsultationBean> unapprovedConsultations = 
        				consultationService.findAllUnapprovedConsultationsByMasterId(viewBean.getId());
        		masterViewBean.setUnapprovedConsultations(unapprovedConsultations);
        			
        		List<SeanceBean> unapprovedSeances = 
        				seanceService.findAllUnapprovedSeancesByMasterId(viewBean.getId());
        			
        		masterViewBean.setUnapprovedSeances(unapprovedSeances);
        	}
    			
    		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN, viewBean);
    	}
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);
    }
    
    @Override
    public String getView(SessionRequestContent content) {
        handle(content);
        return view;
    }
}
