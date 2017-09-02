package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.entity.SeanceEntity;

public interface SeanceService extends BaseService<SeanceBean, SeanceEntity>{	
	
	default SeanceBean convertEntityToBean(SeanceEntity entity) {
		SeanceBean seanceBean = null;		
		if(entity != null) {
			seanceBean = new SeanceBean(
					entity.getId(), 
					entity.getDate(), 
					entity.getDuration(), 
					entity.getCostPerHour(), 
					entity.getClientId(), 
					entity.getClientName(), 
					entity.getMasterId(), 
					entity.getMasterName());
		}
		return seanceBean;
	}
	
	List<SeanceBean> findAllSeancesByClientId(int id);
	List<SeanceBean> findAllSeancesByMasterId(int id);
}
