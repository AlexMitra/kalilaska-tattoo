package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.SeanceDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.SeanceEntity;
import by.kalilaska.ktattoo.service.SeanceService;

public class SeanceServiceJdbc implements SeanceService {
	private SeanceDAO seanceDao;
	private TransactionManager transactionManager;

	public SeanceServiceJdbc() {
		seanceDao = new SeanceDAO();
		transactionManager = new TransactionManager();
	}

	@Override
	public List<SeanceBean> findAllSeancesByClientId(int id) {
		transactionManager.beginTransaction(seanceDao);
		
		List<SeanceEntity> seanceEntityList = null;
		List<SeanceBean> seanceBeanList = null;
		SeanceBean seanceBean = null;
		
		try {
			seanceEntityList = seanceDao.findAllSeancesByClientId(id);
			if(seanceEntityList != null) {
				seanceBeanList = new LinkedList<>();
				for (SeanceEntity seanceEntity : seanceEntityList) {
					seanceBean = new SeanceBean();
					seanceBean.setId(seanceEntity.getId());
					seanceBean.setDate(seanceEntity.getDate());
					seanceBean.setDuration(seanceEntity.getDuration());
					seanceBean.setCostPerHour(seanceEntity.getCostPerHour());
					seanceBean.setClientId(seanceEntity.getClientId());
					seanceBean.setClientName(seanceEntity.getClientName());
					seanceBean.setMasterId(seanceEntity.getMasterId());
					seanceBean.setMasterName(seanceEntity.getMasterName());
					seanceBeanList.add(seanceBean);
				}
			}		
			
			transactionManager.commit();
		}catch (DaoSQLException e) {
			//log
			transactionManager.rollback();
		}
		transactionManager.endTransaction();
		
		return seanceBeanList;
	}

	@Override
	public List<SeanceBean> findAllSeancesByMasterId(int id) {
		throw new UnsupportedOperationException();
	}

}
