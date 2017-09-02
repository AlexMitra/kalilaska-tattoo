package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.SeanceDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.SeanceEntity;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.service.DaoFactory;

public class SeanceServiceJdbc implements SeanceService {
	private final static Logger LOGGER = LogManager.getLogger(SeanceServiceJdbc.class);
	private SeanceDAO seanceDao;
	private TransactionManager transactionManager;

	public SeanceServiceJdbc() {		
		seanceDao = DaoFactory.createDao(this.getClass());
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

					seanceBean = convertEntityToBean(seanceEntity);
					seanceBeanList.add(seanceBean);
				}
			}		
			
			transactionManager.commit();
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();		
		return seanceBeanList;
	}

	@Override
	public List<SeanceBean> findAllSeancesByMasterId(int id) {
		throw new UnsupportedOperationException();
	}

}
