package by.kalilaska.ktattoo.service;

import java.util.Date;
import java.util.List;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.entity.ConsultationEntity;

public interface ConsultationService extends BaseService<ConsultationBean, ConsultationEntity>{
	
	default ConsultationBean convertEntityToBean(ConsultationEntity entity) {
		ConsultationBean consultationBean = null;
		
		if(entity != null) {
			consultationBean = new ConsultationBean(
					entity.getId(), 
					entity.getDateStart(), 
					entity.getClientId(), 
					entity.getClientName(), 
					entity.getMasterId(), 
					entity.getMasterName(), 
					entity.isApproved());	
		}
		return consultationBean;
	}
	
	String getWorningMessage();
	
	ConsultationBean findConsultationByMasterIdAndDate(int id, Date date);
	ConsultationBean findConsultationByClientIdAndDate(int id, Date date);
	
	List<ConsultationBean> findAllConsultationsByClientId(int id);
	List<ConsultationBean> findAllConsultationsByMasterId(int id);
	
	ConsultationBean create(int cliendId, String masterIdStr, String clientName, String masterName, String dateStr);
}
