package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooMasterDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.TattooMasterEntity;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.service.TattooStyleService;

public class TattooMasterServiceImpl implements TattooMasterService {
	private final static Logger LOGGER = LogManager.getLogger(TattooMasterServiceImpl.class);
	private TattooMasterDAO tattooMasterDAO;
	private TattooPhotoService tattooPhotoService;
	private TattooStyleService tattooStyleService;
	private TransactionManager transactionManager;

	public TattooMasterServiceImpl() {		
		tattooMasterDAO = DaoFactory.createDao(this.getClass());
		tattooPhotoService = new TattooPhotoServiceImpl();
		tattooStyleService = new TattooStyleServiceImpl();
		transactionManager = new TransactionManager();
	}

	@Override
	public List<TattooMasterBean> findAllAllowedMasters() {
		List<TattooMasterBean> tattooMasterBeanList = null;
		TattooMasterBean tattooMasterBean = null;
		
		transactionManager.beginTransaction(tattooMasterDAO, tattooPhotoService.getDao(), tattooStyleService.getDao());
		try {
			List<TattooMasterEntity> tattooMasterEntityList = tattooMasterDAO.findAll();
			if(tattooMasterEntityList != null && !tattooMasterEntityList.isEmpty()) {
				tattooMasterBeanList = new LinkedList<>();
				
				for (TattooMasterEntity tattooMasterEntity : tattooMasterEntityList) {

					tattooMasterBean = convertEntityToBean(tattooMasterEntity);
					
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
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooMasterDAO or TattooPhotoDAO or TattoStyleDAO: " + e.getMessage());
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
			transactionManager.commit();
			if(masterEntity != null) {
				masterBean = convertEntityToBean(masterEntity);
			}			
		}catch(SQLDataException e) {			
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooMasterDAO: " + e.getMessage());
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
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooMasterDAO: " + e.getMessage());
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
				masterBean = convertEntityToBean(masterEntity);
			}			
		}catch (SQLDataException e) {			
			LOGGER.log(Level.ERROR, "Exception in TattooMasterDAO: " + e.getMessage());
		}		
		return masterBean;
	}
	
	@Override
	public boolean deleteMasterById(Integer id) {
		boolean deleted = false;
		if(id != null) {
			try {			
				deleted = tattooMasterDAO.delete(id);			
			}catch (SQLDataException e) {			
				LOGGER.log(Level.ERROR, "Exception in TattooMasterDAO: " + e.getMessage());
			}
		}
		return deleted;
	}
}
