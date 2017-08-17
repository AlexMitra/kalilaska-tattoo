package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.ConsultationBean;

public interface ConsultationService {
	List<ConsultationBean> findAllConsultationsByClientId(int id);
	List<ConsultationBean> findAllConsultationsByMasterId(int id);
}
