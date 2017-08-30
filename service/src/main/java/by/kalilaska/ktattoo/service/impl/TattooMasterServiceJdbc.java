package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooMasterDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.TattooMasterEntity;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.service.TattooStyleService;

public class TattooMasterServiceJdbc implements TattooMasterService {
	private TattooMasterDAO tattooMasterDAO;
	private TattooPhotoService tattooPhotoService;
	private TattooStyleService tattooStyleService;
	private TransactionManager transactionManager;	

	public TattooMasterServiceJdbc() {
		tattooMasterDAO = new TattooMasterDAO();
		tattooPhotoService = new TattooPhotoServiceJdbc();
		tattooStyleService = new TattooStyleServiceJdbc();
		transactionManager = new TransactionManager();
	}

	@Override
	public List<TattooMasterBean> findAllAllowedMasters() {
		transactionManager.beginTransaction(tattooMasterDAO, tattooPhotoService.getDao(), tattooStyleService.getDao());
		
		List<TattooMasterBean> tattooMasterBeanList = null;
		TattooMasterBean tattooMasterBean = null;
		
		try {
			List<TattooMasterEntity> tattooMasterEntityList = tattooMasterDAO.findAll();
			if(tattooMasterEntityList != null && !tattooMasterEntityList.isEmpty()) {
				tattooMasterBeanList = new LinkedList<>();
				
				for (TattooMasterEntity tattooMasterEntity : tattooMasterEntityList) {
					tattooMasterBean = 
							new TattooMasterBean(tattooMasterEntity.getId(), 
							tattooMasterEntity.getName(), 
							tattooMasterEntity.getPhotoUrl(), 
							tattooMasterEntity.getAboutInfo());
					
					List<TattooPhotoBean> tattooPhotos = 
							tattooPhotoService.findAllTattooPhotoByMasterId(tattooMasterEntity.getId());
					if(tattooPhotos != null) {
						tattooMasterBean.setPhotos(tattooPhotos);
					}
					
					List<String> styleNames = 
							tattooStyleService.findAllTattooStyleNameByMasterId(tattooMasterEntity.getId());
					if(styleNames != null) {
						tattooMasterBean.setStyleNames(styleNames);
					}
					
					tattooMasterBeanList.add(tattooMasterBean);
				}				
			}
			transactionManager.commit();
		} catch (DaoSQLException e) {
			transactionManager.rollback();
			//LOG
		}
		transactionManager.endTransaction();
		
		return tattooMasterBeanList;
	}

	@Override
	public TattooMasterBean findMasterById(int id) {
		transactionManager.beginTransaction(tattooMasterDAO);
		TattooMasterBean masterBean = null;
		
		try {
			TattooMasterEntity masterEntity = tattooMasterDAO.findById(id);
			if(masterEntity != null) {
				masterBean = new TattooMasterBean(
						masterEntity.getId(), 
						masterEntity.getName(), 
						masterEntity.getPhotoUrl(), 
						masterEntity.getAboutInfo());
			}
			transactionManager.commit();
		}catch(DaoSQLException e) {
			//LOG
			transactionManager.rollback();
		}
		
		transactionManager.endTransaction();
		return masterBean;
	}
	
	@Override
	public boolean updateMasterProfile(TattooMasterBean masterBean){	
		boolean result = false;
		
		int id = masterBean.getId();
		String aboutInfo = masterBean.getAboutInfo();
		
		TattooMasterEntity masterEntity = new TattooMasterEntity();
		masterEntity.setId(id);
		masterEntity.setAboutInfo(aboutInfo);
		
		transactionManager.beginTransaction(tattooMasterDAO);
		try {
			result = tattooMasterDAO.update(masterEntity);			
			transactionManager.commit();
		} catch (DaoSQLException e) {
			transactionManager.rollback();
			//LOG
		}
		
		transactionManager.endTransaction();
		return result;
	}

	@Override
	public TattooMasterDAO getDao() {		
		return tattooMasterDAO;
	}

	@Override
	public TattooMasterBean create(int id, String aboutInfo) {
		TattooMasterBean masterBean = null;
		TattooMasterEntity masterEntity = new TattooMasterEntity();
		masterEntity.setId(id);
		masterEntity.setAboutInfo(aboutInfo);
		try {			
			masterEntity = tattooMasterDAO.create(masterEntity);			
			if(masterEntity != null) {
				masterBean = new TattooMasterBean(masterEntity.getId(), 
						masterEntity.getName(), 
						masterEntity.getPhotoUrl(), 
						masterEntity.getAboutInfo());
			}			
		}catch (DaoSQLException e) {			
	           // log
		}		
		return masterBean;
	}
	
	@Override
	public boolean deleteMasterById(Integer id) {
		boolean deleted = false;
		if(id != null) {
			try {			
				deleted = tattooMasterDAO.delete(id);			
			}catch (DaoSQLException e) {			
		           // log
			}
		}
		return deleted;
	}
}
