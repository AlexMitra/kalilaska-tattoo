package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.dao.impl.TattooStyleDAO;
import by.kalilaska.ktattoo.entity.TattooStyleEntity;

public interface TattooStyleService extends BaseService<TattooStyleBean, TattooStyleEntity>{
	
	default TattooStyleBean convertEntityToBean(TattooStyleEntity entity) {
		TattooStyleBean tattooStyleBean = null;
		if(entity != null) {
			tattooStyleBean = new TattooStyleBean(
					entity.getId(), 
					entity.getName(), 
					entity.getDescription());
		}
		return tattooStyleBean;
	}
	
	String getWorningMessage();
	TattooStyleDAO getDao();
	
	List<String> findAllTattooStyleNameByMasterId(int id);
	TattooStyleBean findTattooStyleByStyleName(String name);
	List<TattooStyleBean> findAllTattooStyleByMasterId(int id);
	List<TattooStyleBean> findAll();
	TattooStyleBean create (String name, String description);
	List<TattooStyleBean> updateAllTattooStyleByMasterId(TattooMasterBean masterBean);	
}
