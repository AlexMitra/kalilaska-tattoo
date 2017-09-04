package by.kalilaska.ktattoo.service;

import java.util.Date;
import java.util.List;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.entity.SeanceEntity;

public interface SeanceService extends BaseService<SeanceBean, SeanceEntity>{	
	
	default SeanceBean convertEntityToBean(SeanceEntity entity) {
		SeanceBean seanceBean = null;		
		if(entity != null) {
			seanceBean = new SeanceBean(
					entity.getId(), 
					entity.getDateStart(), 
					entity.getDuration(), 
					entity.getCostPerHour(), 
					entity.getClientId(), 
					entity.getClientName(), 
					entity.getMasterId(), 
					entity.getMasterName());
		}
		return seanceBean;
	}
	
	String getWorningMessage();
	
	SeanceBean findSeanceByMasterIdAndDate(int id, Date date);
	SeanceBean findSeanceByClientIdAndDate(int id, Date date);
	
	List<SeanceBean> findAllApprovedSeancesByClientId(int id);
	List<SeanceBean> findAllSeancesByMasterId(int id);
	List<SeanceBean> findAllUnapprovedSeancesByMasterId(int id);

	boolean approveSeanceById(String idStr, String costStr);
	
	SeanceBean create(int clientId, String masterIdStr, String clientName, String masterName, 
			String dateStr, String durationStr);
}
