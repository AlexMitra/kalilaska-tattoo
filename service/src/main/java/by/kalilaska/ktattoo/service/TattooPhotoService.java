package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.impl.TattooPhotoDAO;
import by.kalilaska.ktattoo.entity.TattooPhotoEntity;

public interface TattooPhotoService extends BaseService<TattooPhotoBean, TattooPhotoEntity>{	
	
	default TattooPhotoBean convertEntityToBean(TattooPhotoEntity entity) {
		TattooPhotoBean tattooPhotoBean = null;
		if(entity != null) {
			tattooPhotoBean = new TattooPhotoBean(
					entity.getId(), 
					entity.getPhotoUrl(), 
					entity.isDone());
		}
		return tattooPhotoBean;
	}
	
	List<TattooPhotoBean> findAllTattooPhotoByMasterId(int id);
	
	TattooPhotoDAO getDao();
}
