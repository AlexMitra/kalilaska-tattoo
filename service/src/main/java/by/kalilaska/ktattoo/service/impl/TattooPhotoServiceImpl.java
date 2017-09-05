package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooPhotoDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.TattooPhotoEntity;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;

public class TattooPhotoServiceImpl implements TattooPhotoService {
	private final static Logger LOGGER = LogManager.getLogger(TattooPhotoServiceImpl.class);
	private TattooPhotoDAO tattooPhotoDAO;
	private TransactionManager transactionManager;
	private String worningMessage;

	public TattooPhotoServiceImpl() {		
		tattooPhotoDAO = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
	}
	
	@Override
	public TattooPhotoBean findTattooPhotoByPhotoUrl(String photoUrl) {
		TattooPhotoBean photoBean = null;
		if(photoUrl != null) {
			transactionManager.beginTransaction(tattooPhotoDAO);
			try {
				TattooPhotoEntity photoEntity = tattooPhotoDAO.findTattooPhotoByPhotoUrl(photoUrl);
				if(photoEntity != null) {
					photoBean = convertEntityToBean(photoEntity);
				}
			} catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "Exception in TattooPhotoDAO: " + e.getMessage());
			}
			transactionManager.endTransaction();
		}
		return photoBean;
	}

	@Override
	public List<TattooPhotoBean> findAllTattooPhotoByMasterId(int id) {
		LinkedList<TattooPhotoBean> tattooPhotoBeanList = null;
		TattooPhotoBean tattooPhotoBean = null;
		
		List<TattooPhotoEntity> tattooPhotoEntityList;
		try {
			tattooPhotoEntityList = tattooPhotoDAO.findAllTattooPhotoByMasterId(id);
			if(tattooPhotoEntityList != null && !tattooPhotoEntityList.isEmpty()) {
				tattooPhotoBeanList = new LinkedList<>();
				
				for (TattooPhotoEntity tattooPhotoEntity : tattooPhotoEntityList) {

					tattooPhotoBean = convertEntityToBean(tattooPhotoEntity);
					tattooPhotoBeanList.add(tattooPhotoBean);
				}
			}
		} catch (SQLDataException e) {
			LOGGER.log(Level.ERROR, "Exception in TattooPhotoDAO: " + e.getMessage());
		}
		
		return tattooPhotoBeanList;
	}

	@Override
	public TattooPhotoDAO getDao() {		
		return tattooPhotoDAO;
	}

	@Override
	public List<TattooPhotoBean> findAllTattooPhotoByMasterIdTransacted(int id) {
		LinkedList<TattooPhotoBean> tattooPhotoBeanList = null;
		TattooPhotoBean tattooPhotoBean = null;
		
		transactionManager.beginTransaction(tattooPhotoDAO);
		try {
			List<TattooPhotoEntity> tattooPhotoEntityList = tattooPhotoDAO.findAllTattooPhotoByMasterId(id);
			transactionManager.commit();
			if(tattooPhotoEntityList != null && !tattooPhotoEntityList.isEmpty()) {
				tattooPhotoBeanList = new LinkedList<>();
				
				for (TattooPhotoEntity tattooPhotoEntity : tattooPhotoEntityList) {

					tattooPhotoBean = convertEntityToBean(tattooPhotoEntity);
					tattooPhotoBeanList.add(tattooPhotoBean);
				}
			}
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooPhotoDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		
		return tattooPhotoBeanList;
	}

	@Override
	public TattooPhotoBean create(String photoUrl, int masterId, Boolean isDone) {
		TattooPhotoBean photoBean = null;
		if(photoUrl != null && !photoUrl.isEmpty() && isDone != null) {
			TattooPhotoBean urlCheckBean = findTattooPhotoByPhotoUrl(photoUrl);
			if(urlCheckBean == null) {
				TattooPhotoEntity photoEntity = new TattooPhotoEntity();
				photoEntity.setPhotoUrl(photoUrl);
				photoEntity.setMasterId(masterId);
				photoEntity.setDone(isDone);
				
				transactionManager.beginTransaction(tattooPhotoDAO);
				try {
					TattooPhotoEntity created = tattooPhotoDAO.create(photoEntity);
					if(created != null) {
						photoBean = convertEntityToBean(created);
					}
					transactionManager.commit();
				} catch (SQLDataException e) {
					transactionManager.rollback();
					LOGGER.log(Level.ERROR, "Exception in TattooPhotoDAO: " + e.getMessage());					
					worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_TATTOO_PHOTO_UNABLE);
				}
				transactionManager.endTransaction();
			}else {				
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_TATTOO_PHOTO_ALREADY_EXISTS);
			}
		}else {			
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_TATTOO_PHOTO_DATA_INVALID);
		}
		return photoBean;
	}
	
	@Override
	public boolean changeTattooPhotoDone(String idStr, boolean doneValue) {
		boolean changed = false;
		if(idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			transactionManager.beginTransaction(tattooPhotoDAO);
			try {
				changed = tattooPhotoDAO.changeTattooPhotoDone(id, doneValue);				
				transactionManager.commit();
			} catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "Exception in TattooPhotoDAO: " + e.getMessage());				
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CHANGE_TATTOO_PHOTO_UNABLE);
			}
			transactionManager.endTransaction();
		}else {			
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CHANGE_TATTOO_PHOTO_DATA_INVALID);
		}
		
		return changed;
	}
	
	@Override
	public String getWorningngMessage() {		
		String message = worningMessage;		
		worningMessage = null;
		return message;
	}
}
