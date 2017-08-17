package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.ConsultationDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.ConsultationEntity;
import by.kalilaska.ktattoo.service.ConsultationService;

public class ConsultationServiceJdbc implements ConsultationService {
	
	private ConsultationDAO consultationDao;
	private TransactionManager transactionManager;

	public ConsultationServiceJdbc() {
		consultationDao = new ConsultationDAO();
		transactionManager = new TransactionManager();
	}

	@Override
	public List<ConsultationBean> findAllConsultationsByClientId(int id) {
		transactionManager.beginTransaction(consultationDao);
		
		List<ConsultationEntity> consultationEntityList = null;
		List<ConsultationBean> consultationBeanList = null;
		ConsultationBean consultationBean = null;
		
		try {
			consultationEntityList = consultationDao.findAllConsultationsByClientId(id);
			if(consultationEntityList != null) {
				consultationBeanList = new LinkedList<>();
				for (ConsultationEntity consultationEntity : consultationEntityList) {
					consultationBean = new ConsultationBean();
					consultationBean.setId(consultationEntity.getId());
					consultationBean.setDate(consultationEntity.getDate());
					consultationBean.setClientId(consultationEntity.getClientId());
					consultationBean.setClientName(consultationEntity.getClientName());
					consultationBean.setMasterId(consultationEntity.getMasterId());
					consultationBean.setMasterName(consultationEntity.getMasterName());
					consultationBeanList.add(consultationBean);
				}
			}		
			
			transactionManager.commit();
		}catch (DaoSQLException e) {
			transactionManager.rollback();
		}
		transactionManager.endTransaction();
		
		return consultationBeanList;
	}

	@Override
	public List<ConsultationBean> findAllConsultationsByMasterId(int id) {
		throw new UnsupportedOperationException();
	}

}
