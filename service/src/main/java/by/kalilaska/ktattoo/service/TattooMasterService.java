package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.dao.impl.TattooMasterDAO;

public interface TattooMasterService {
	TattooMasterBean findMasterById(int id);
	List<TattooMasterBean> findAllAllowedMasters();
	TattooMasterBean create(int id, String aboutInfo);
	boolean updateMasterProfile(TattooMasterBean masterBean);
	boolean deleteMasterById(Integer id);
	TattooMasterDAO getDao();
}
