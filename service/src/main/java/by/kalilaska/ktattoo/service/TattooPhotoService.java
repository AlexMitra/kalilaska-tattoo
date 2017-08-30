package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.impl.TattooPhotoDAO;

public interface TattooPhotoService {
	List<TattooPhotoBean> findAllTattooPhotoByMasterId(int id);
	
	TattooPhotoDAO getDao();
}
