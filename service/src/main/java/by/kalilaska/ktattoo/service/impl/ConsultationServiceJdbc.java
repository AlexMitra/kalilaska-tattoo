package by.kalilaska.ktattoo.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.converter.DateConverter;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.ConsultationDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.ConsultationEntity;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.serviceexception.MessageFileNotFoundServiceException;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;

public class ConsultationServiceJdbc implements ConsultationService {
	private final static Logger LOGGER = LogManager.getLogger(ConsultationServiceJdbc.class);
	private ConsultationDAO consultationDao;
	private TransactionManager transactionManager;
	private ServiceMessageManager messageManager;
	private String worningMessage;

	public ConsultationServiceJdbc() {		
		consultationDao = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
		initManager();
	}
	
	private void initManager() {
		try {
			messageManager = new ServiceMessageManager();
		} catch (MessageFileNotFoundServiceException e) {
			LOGGER.log(Level.WARN, "can not init ServiceMessageManager: " + e.getMessage());
		}
	}

	@Override
	public List<ConsultationBean> findAllConsultationsByClientId(int id) {
		List<ConsultationEntity> consultationEntityList = null;
		List<ConsultationBean> consultationBeanList = null;
		ConsultationBean consultationBean = null;
		
		transactionManager.beginTransaction(consultationDao);
		try {
			consultationEntityList = consultationDao.findAllConsultationsByClientId(id);
			transactionManager.commit();
			if(consultationEntityList != null) {
				consultationBeanList = new LinkedList<>();
				for (ConsultationEntity consultationEntity : consultationEntityList) {

					consultationBean = convertEntityToBean(consultationEntity);
					consultationBeanList.add(consultationBean);
				}
			}
		}catch (SQLDataException e) {			
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		return consultationBeanList;
	}

	@Override
	public List<ConsultationBean> findAllConsultationsByMasterId(int id) {
		List<ConsultationBean> consultationBeanList = null;
		ConsultationBean consultationBean = null;
		
		transactionManager.beginTransaction(consultationDao);
		try {
			List<ConsultationEntity> consultationEntityList = 
					consultationDao.findAllConsultationsByMasterId(id);
			transactionManager.commit();
			if(consultationEntityList != null) {
				consultationBeanList = new LinkedList<>();
				for (ConsultationEntity consultationEntity : consultationEntityList) {
					consultationBean = convertEntityToBean(consultationEntity);
					consultationBeanList.add(consultationBean);
				}
			}
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		return consultationBeanList;
	}

	@Override
	public ConsultationBean findConsultationByMasterIdAndDate(int id, Date date) {
		ConsultationBean consultationBean = null;
		if(date != null) {
			transactionManager.beginTransaction(consultationDao);
			try {
				ConsultationEntity consultationEntity = consultationDao.findConsultationByMasterIdAndDate(id, date);
				transactionManager.commit();
				if(consultationEntity != null) {
					consultationBean = convertEntityToBean(consultationEntity);
				}
			} catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
			}
			transactionManager.endTransaction();
		}
		return consultationBean;
	}
	
	@Override
	public ConsultationBean findConsultationByClientIdAndDate(int id, Date date) {
		ConsultationBean consultationBean = null;
		if(date != null) {
			transactionManager.beginTransaction(consultationDao);
			try {
				ConsultationEntity consultationEntity = consultationDao.findConsultationByClientIdAndDate(id, date);
				transactionManager.commit();
				if(consultationEntity != null) {
					consultationBean = convertEntityToBean(consultationEntity);
				}
			} catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
			}
			transactionManager.endTransaction();
		}
		return consultationBean;
	}

	@Override
	public ConsultationBean create(int clientId, String masterIdStr, String clientName, String masterName, 
			String dateStr) {		
		ConsultationBean consultationBean = null;
		Date dateStart = DateConverter.convertStringToDate(dateStr);
		
		if(masterIdStr != null && masterName != null && dateStart != null) {			
			
			int masterId = Integer.parseInt(masterIdStr);			
			ConsultationBean masterBusyCheck = findConsultationByMasterIdAndDate(masterId, dateStart);
			ConsultationBean clientBusyCheck = findConsultationByClientIdAndDate(clientId, dateStart);
			
			if(masterBusyCheck == null && clientBusyCheck == null) {
				ConsultationEntity consultationEntity = new ConsultationEntity();
				consultationEntity.setClientId(clientId);
				consultationEntity.setClientName(clientName);
				consultationEntity.setMasterId(masterId);
				consultationEntity.setDateStart(dateStart);					
					
				transactionManager.beginTransaction(consultationDao);
				try {
					ConsultationEntity created = consultationDao.create(consultationEntity);
					transactionManager.commit();
						
					if(created != null) {
						consultationBean = convertEntityToBean(created);
					}
				} catch (SQLDataException e) {
					transactionManager.rollback();
					LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
				}
				transactionManager.endTransaction();
			}else {					
				if(clientBusyCheck != null) {
					worningMessage = makeWorningMessage(ServiceMessageNameList.CREATE_CONSULTATION_CLIENT_ALREADY_BUSY);
				}
				if(masterBusyCheck != null) {
					worningMessage = makeWorningMessage(ServiceMessageNameList.CREATE_CONSULTATION_MASTER_ALREADY_BUSY);
				}
			}
		}else {
			worningMessage = makeWorningMessage(ServiceMessageNameList.CREATE_CONSULTATION_DATA_INVALID);
		}		
		return consultationBean;
	}
	
	
	private String makeWorningMessage(String messagePath) {
		String message = null;
		if(messageManager != null) {
			message = messageManager.getProperty(messagePath);
		}
		return message;
	}

	@Override
	public String getWorningMessage() {
		String message = worningMessage == null ? "" : worningMessage;		
		worningMessage = null;
		return message;
	}
}
