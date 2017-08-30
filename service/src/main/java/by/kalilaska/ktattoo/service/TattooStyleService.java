package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.dao.impl.TattooStyleDAO;

public interface TattooStyleService {
	List<String> findAllTattooStyleNameByMasterId(int id);
	List<TattooStyleBean> findAllTattooStyleByMasterId(int id);
	List<TattooStyleBean> findAll();
	List<TattooStyleBean> updateAllTattooStyleByMasterId(TattooMasterBean masterBean);
	TattooStyleDAO getDao();
}
