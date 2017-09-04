package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.dao.impl.TattooMasterDAO;
import by.kalilaska.ktattoo.entity.TattooMasterEntity;

public interface TattooMasterService extends BaseService<TattooMasterBean, TattooMasterEntity>{	
	
	default TattooMasterBean convertEntityToBean(TattooMasterEntity entity) {
		TattooMasterBean tattooMasterBean = null;
		if(entity != null) {
			tattooMasterBean = new TattooMasterBean(
					entity.getId(), 
					entity.getName(), 
					entity.getPhotoUrl(), 
					entity.getAboutInfo());
		}
		return tattooMasterBean;
	}
	
	TattooMasterDAO getDao();
	
	TattooMasterBean findMasterById(int id);
	List<TattooMasterBean> findAllAllowedMasters();
	TattooMasterBean create(int id, String aboutInfo);
	boolean updateMasterProfile(TattooMasterBean masterBean);
	boolean deleteMasterById(Integer id);
	
}
