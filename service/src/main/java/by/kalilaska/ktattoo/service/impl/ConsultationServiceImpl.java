package by.kalilaska.ktattoo.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.converter.DateConverter;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.ConsultationDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.ConsultationEntity;
import by.kalilaska.ktattoo.service.ConsultationService;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;

public class ConsultationServiceImpl implements ConsultationService {
	private final static Logger LOGGER = LogManager.getLogger(ConsultationServiceImpl.class);
	private ConsultationDAO consultationDao;
	private TransactionManager transactionManager;
	private SeanceService seanceService;
	private String worningMessage;

	public ConsultationServiceImpl() {		
		consultationDao = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
	}
	
	public ConsultationServiceImpl(SeanceService seanceService) {		
		consultationDao = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
		this.seanceService = seanceService;
	}
	
	@Override
	public List<ConsultationBean> findAllApprovedConsultationsByClientId(int id) {
		List<ConsultationEntity> consultationEntityList = null;
		List<ConsultationBean> consultationBeanList = null;
		ConsultationBean consultationBean = null;
		
		transactionManager.beginTransaction(consultationDao);
		try {
			consultationEntityList = consultationDao.findAllApprovedConsultationsByClientId(id);
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
	public List<ConsultationBean> findAllUnapprovedConsultationsByMasterId(int id) {
		List<ConsultationBean> consultationBeanList = null;
		ConsultationBean consultationBean = null;
		
		transactionManager.beginTransaction(consultationDao);
		try {
			List<ConsultationEntity> consultationEntityList = 
					consultationDao.findAllUnapprovedConsultationsByMasterId(id);
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
	public boolean approveAllConsultationByMasterId(int masterId) {
		boolean approved = false;
		
		transactionManager.beginTransaction(consultationDao);
		try {
			approved = consultationDao.approveAllConsultationByMasterId(masterId);
			transactionManager.commit();
		} catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.APPROVE_CONSULTATION_CAN_NOT_APPROVE);			
		}		
		if(!approved) {
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.APPROVE_CONSULTATION_ALL_ALREADY_APPROVED);			
		}
		transactionManager.endTransaction();
		return approved;
	}
	

	@Override
	public boolean approveConsultationById(String idStr) {
		boolean approved = false;
		if(idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			transactionManager.beginTransaction(consultationDao);
			try {
				approved = consultationDao.approveConsultationById(id);
				transactionManager.commit();
			} catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "exception in ConsultationDAO: " + e.getMessage());
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.APPROVE_CONSULTATION_CAN_NOT_APPROVE);
			}			
			if(!approved) {
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.APPROVE_CONSULTATION_ALL_ALREADY_APPROVED);			
			}
			transactionManager.endTransaction();
		}
		return approved;
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
			SeanceBean masterBusyInSeance = null;
			SeanceBean clientBusyInSeance = null;
			if(seanceService != null) {
				masterBusyInSeance = seanceService.findSeanceByMasterIdAndDate(masterId, dateStart);
				clientBusyInSeance = seanceService.findSeanceByClientIdAndDate(clientId, dateStart);
			}
			
			if(masterBusyCheck == null && clientBusyCheck == null && 
					masterBusyInSeance == null && clientBusyInSeance == null) {
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
				if(clientBusyCheck != null || clientBusyInSeance != null) {					
					worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_CONSULTATION_CLIENT_ALREADY_BUSY);
				}
				if(masterBusyCheck != null || masterBusyInSeance != null) {					
					worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_CONSULTATION_MASTER_ALREADY_BUSY);
				}
			}
		}else {			
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_CONSULTATION_DATA_INVALID);
		}		
		return consultationBean;
	}

	@Override
	public String getWorningMessage() {
		String message = worningMessage == null ? "" : worningMessage;		
		worningMessage = null;
		return message;
	}
}
