package by.kalilaska.ktattoo.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.converter.BigDecimalConverter;
import by.kalilaska.ktattoo.converter.DateConverter;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.SeanceDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.SeanceEntity;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.service.SeanceService;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicename.ServiceNameList;

public class SeanceServiceImpl implements SeanceService {
	private final static Logger LOGGER = LogManager.getLogger(SeanceServiceImpl.class);
	private SeanceDAO seanceDao;
	private TransactionManager transactionManager;
//	private ServiceMessageManager messageManager;
	private String worningMessage;

	public SeanceServiceImpl() {		
		seanceDao = DaoFactory.createDao(this.getClass());
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
	public List<SeanceBean> findAllApprovedSeancesByClientId(int id) {
		List<SeanceEntity> seanceEntityList = null;
		List<SeanceBean> seanceBeanList = null;
		SeanceBean seanceBean = null;
		
		transactionManager.beginTransaction(seanceDao);
		try {
			seanceEntityList = seanceDao.findAllApprovedSeancesByClientId(id);
			transactionManager.commit();
			if(seanceEntityList != null) {
				seanceBeanList = new LinkedList<>();
				for (SeanceEntity seanceEntity : seanceEntityList) {

					seanceBean = convertEntityToBean(seanceEntity);
					seanceBeanList.add(seanceBean);
				}
			}
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();		
		return seanceBeanList;
	}

	@Override
	public List<SeanceBean> findAllSeancesByMasterId(int id) {
		List<SeanceEntity> seanceEntityList = null;
		List<SeanceBean> seanceBeanList = null;
		SeanceBean seanceBean = null;
		
		transactionManager.beginTransaction(seanceDao);
		try {
			seanceEntityList = seanceDao.findAllSeancesByMasterId(id);
			transactionManager.commit();
			if(seanceEntityList != null) {
				seanceBeanList = new LinkedList<>();
				for (SeanceEntity seanceEntity : seanceEntityList) {

					seanceBean = convertEntityToBean(seanceEntity);
					seanceBeanList.add(seanceBean);
				}
			}
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();		
		return seanceBeanList;
	}
	

	@Override
	public List<SeanceBean> findAllUnapprovedSeancesByMasterId(int id) {
		List<SeanceEntity> seanceEntityList = null;
		List<SeanceBean> seanceBeanList = null;
		SeanceBean seanceBean = null;
		
		transactionManager.beginTransaction(seanceDao);
		try {
			seanceEntityList = seanceDao.findAllUnapprovedSeancesByMasterId(id);
			transactionManager.commit();
			if(seanceEntityList != null) {
				seanceBeanList = new LinkedList<>();
				for (SeanceEntity seanceEntity : seanceEntityList) {

					seanceBean = convertEntityToBean(seanceEntity);
					seanceBeanList.add(seanceBean);
				}
			}
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();		
		return seanceBeanList;
	}

	@Override
	public SeanceBean findSeanceByMasterIdAndDate(int id, Date date) {		
		SeanceBean seanceBean = null;
		if(date != null) {
			transactionManager.beginTransaction(seanceDao);
			try {
				SeanceEntity seanceEntity = seanceDao.findSeanceByMasterIdAndDate(id, date);
				transactionManager.commit();
				if(seanceEntity != null) {
					seanceBean = convertEntityToBean(seanceEntity);
				}
			}catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
			}
			transactionManager.endTransaction();
		}		
		return seanceBean;
	}

	@Override
	public SeanceBean findSeanceByClientIdAndDate(int id, Date date) {
		SeanceBean seanceBean = null;
		if(date != null) {
			transactionManager.beginTransaction(seanceDao);
			try {
				SeanceEntity seanceEntity = seanceDao.findSeanceByClientIdAndDate(id, date);
				transactionManager.commit();
				if(seanceEntity != null) {
					seanceBean = convertEntityToBean(seanceEntity);
				}
			}catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
			}
			transactionManager.endTransaction();
		}		
		return seanceBean;
	}
	
	@Override
	public boolean approveSeanceById(String idStr, String costStr) {
		boolean approved = false;
		BigDecimal cost = BigDecimalConverter.convertStringToBigDecimal(costStr);
		
		if(idStr != null && !idStr.isEmpty() && cost != null 
				&& cost.doubleValue() > ServiceNameList.SEANCE_MINIMUM_PRICE) {
			
			int seanceId = Integer.parseInt(idStr);
			transactionManager.beginTransaction(seanceDao);
			try {
				approved = seanceDao.approveSeanceById(seanceId, cost);				
				transactionManager.commit();
			} catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
				worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.APPROVE_SEANCE_CAN_NOT_APPROVE);
			}
			transactionManager.endTransaction();
		}else {
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.APPROVE_SEANCE_DATA_INVALID);
		}		
		return approved;
	}

	@Override
	public SeanceBean create(int clientId, String masterIdStr, String clientName, String masterName, String dateStr,
			String durationStr) {		
		SeanceBean seanceBean = null;
		
		Date dateStart = DateConverter.convertStringToDate(dateStr);		
		if(masterIdStr != null && clientName != null && masterName != null && durationStr != null) {
			int masterId = Integer.parseInt(masterIdStr);
			
			SeanceBean masterBusyCheck = findSeanceByMasterIdAndDate(masterId, dateStart);
			SeanceBean clientBusyCheck = findSeanceByClientIdAndDate(clientId, dateStart);
			
			if(masterBusyCheck == null && clientBusyCheck == null) {
				byte duration = Byte.parseByte(durationStr);
				
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(dateStart);
				calendar.add(Calendar.HOUR_OF_DAY, duration);
				Date dateEnd = calendar.getTime();
				
				SeanceEntity seanceEntity = new SeanceEntity();
				seanceEntity.setClientId(clientId);
				seanceEntity.setClientName(clientName);
				seanceEntity.setMasterId(masterId);
				seanceEntity.setMasterName(masterName);
				seanceEntity.setDateStart(dateStart);
				seanceEntity.setDateEnd(dateEnd);
				seanceEntity.setDuration(duration);
				
				transactionManager.beginTransaction(seanceDao);
				try {
					SeanceEntity created = seanceDao.create(seanceEntity);
					transactionManager.commit();
					
					if(created != null) {
						seanceBean = convertEntityToBean(created);
					}
				} catch (SQLDataException e) {
					transactionManager.rollback();
					LOGGER.log(Level.ERROR, "Exception in SeanceDAO: " + e.getMessage());
				}
				transactionManager.endTransaction();
				
			}else {
				if(clientBusyCheck != null) {
					worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_SEANCE_CLIENT_ALREADY_BUSY);
				}
				if(masterBusyCheck != null) {
					worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_SEANCE_MASTER_ALREADY_BUSY);
				}
			}
		}else {
			worningMessage = ServiceMessageManager.getMessage(ServiceMessageNameList.CREATE_SEANCE_DATA_INVALID);
		}
		
		return seanceBean;
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
		String message = worningMessage;		
		worningMessage = null;
		return message;
	}
}
