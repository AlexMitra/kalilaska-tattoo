package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooStyleDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.TattooStyleEntity;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class TattooStyleServiceImpl implements TattooStyleService{
	private final static Logger LOGGER = LogManager.getLogger(TattooStyleServiceImpl.class);
	private TattooStyleDAO tattooStyleDAO;
	private TransactionManager transactionManager;	
//	private ServiceMessageManager messageManager;
	private String worningMessage;

	public TattooStyleServiceImpl() {		
		tattooStyleDAO = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();		
//		initManager();
	}
	
//	private void initManager() {
//		try {
//			messageManager = new ServiceMessageManager();
//		} catch (MessageFileNotFoundServiceException e) {
//			LOGGER.log(Level.WARN, "can not init ServiceMessageManager: " + e.getMessage());
//		}
//	}

	@Override
	public TattooStyleBean findTattooStyleByStyleName(String name) {
		TattooStyleBean styleBean = null;	
		transactionManager.beginTransaction(tattooStyleDAO);
		try {
			TattooStyleEntity styleEntity = tattooStyleDAO.findTattooStyleByName(name);
			transactionManager.commit();
			
			if(styleEntity != null) {
				styleBean = convertEntityToBean(styleEntity);
			}			
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooStyleDAO: " + e.getMessage());
		}		
		transactionManager.endTransaction();
		return styleBean;
	}

	@Override
	public List<String> findAllTattooStyleNameByMasterId(int id) {
		LinkedList<String> styleNames = null;
		try {
			List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAllTattooStyleByMasterId(id);
			if(styleEntityList != null && !styleEntityList.isEmpty()) {
				styleNames = new LinkedList<>();
				for (TattooStyleEntity tattooStyleEntity : styleEntityList) {
					styleNames.add(tattooStyleEntity.getName());
				}
			}
		} catch (SQLDataException e) {
			LOGGER.log(Level.ERROR, "Exception in TattooStyleDAO: " + e.getMessage());
		}
		return styleNames;
	}

	@Override
	public TattooStyleDAO getDao() {		
		return tattooStyleDAO;
	}

	@Override
	public List<TattooStyleBean> findAllTattooStyleByMasterId(int id) {		
		LinkedList<TattooStyleBean> styleBeanList = null;
		TattooStyleBean tattooStyleBean = null;
		
		transactionManager.beginTransaction(tattooStyleDAO);
		try {
			List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAllTattooStyleByMasterId(id);
			transactionManager.commit();
			
			if(styleEntityList != null && !styleEntityList.isEmpty()) {
				styleBeanList = new LinkedList<>();
				
				for (TattooStyleEntity tattooStyleEntity : styleEntityList) {

					tattooStyleBean = convertEntityToBean(tattooStyleEntity);
					styleBeanList.add(tattooStyleBean);
				}
			}			
		} catch (SQLDataException e) {			
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooStyleDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		return styleBeanList;
	}

	@Override
	public List<TattooStyleBean> findAll() {
		transactionManager.beginTransaction(tattooStyleDAO);
		List<TattooStyleBean> styleBeanList = null;
		TattooStyleBean tattooStyleBean = null;
		
		try {
			List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAll();
			transactionManager.commit();
			
			if(styleEntityList != null && !styleEntityList.isEmpty()) {
				styleBeanList = new LinkedList<>();
				
				for (TattooStyleEntity tattooStyleEntity : styleEntityList) {

					tattooStyleBean = convertEntityToBean(tattooStyleEntity);
					styleBeanList.add(tattooStyleBean);
				}
			}			
		}catch(SQLDataException e){
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooStyleDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		return styleBeanList;
	}

	@Override
	public List<TattooStyleBean> updateAllTattooStyleByMasterId(TattooMasterBean masterBean) {
		List<TattooStyleBean> styleBeanList = null;
		TattooStyleBean styleBean = null;
		
		int masterId = masterBean.getId();
		List<String> styleIdList = masterBean.getStyleNames();
		
		transactionManager.beginTransaction(tattooStyleDAO);
		try {
			boolean masterStyleCleaned = tattooStyleDAO.cleanAllMasterTattooStyle(masterId);
			
			if(masterStyleCleaned) {
				boolean masterStyleInserted = tattooStyleDAO.insertMasterTattooStyle(masterId, styleIdList);
				
				if(masterStyleInserted) {
					List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAllTattooStyleByMasterId(masterId);
					
					if(styleEntityList != null) {
						styleBeanList = new LinkedList<>();
						for (TattooStyleEntity tattooStyleEntity : styleEntityList) {

							styleBean = convertEntityToBean(tattooStyleEntity);
							styleBeanList.add(styleBean);
						}
					}
				}
			} 
			transactionManager.commit();
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in TattooStyleDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		return styleBeanList;
	}

	@Override
	public TattooStyleBean create(String name, String description) {		
		TattooStyleBean styleBean = null;
		
		if(name != null && FormDataValidator.validate(name, DataType.NAME)) {
			TattooStyleBean nameCheckBean = findTattooStyleByStyleName(name);
			
			if(nameCheckBean == null) {
				TattooStyleEntity styleEntity = new TattooStyleEntity();
				styleEntity.setName(name);
				styleEntity.setDescription(description);
				
				transactionManager.beginTransaction(tattooStyleDAO);
				try {
					TattooStyleEntity created = tattooStyleDAO.create(styleEntity);
					transactionManager.commit();
					if(created != null) {
						styleBean = convertEntityToBean(created);
					}
				} catch (SQLDataException e) {
					transactionManager.rollback();
					LOGGER.log(Level.ERROR, "Exception in TattooStyleDAO: " + e.getMessage());
				}
				transactionManager.endTransaction();
			}else {
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_TATTOO_STYLE_ALREADY_EXISTS);
			}			
		}else {
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_TATTOO_STYLE_DATA_INVALID);			
		}
		
		return styleBean;
	}
	
//	private String makeWorningMessage(String messagePath) {
//		String message = null;
//		if(messageManager != null) {
//			message = messageManager.getProperty(messagePath);
//		}
//		return message;
//	}

	@Override
	public String getWorningMessage() {		
		String message = worningMessage == null ? "" : worningMessage;		
		worningMessage = null;
		return message;
	}
}
