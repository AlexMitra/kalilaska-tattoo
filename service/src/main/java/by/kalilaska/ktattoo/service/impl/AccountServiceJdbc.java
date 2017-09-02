package by.kalilaska.ktattoo.service.impl;


import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.AccountDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.encoder.MD5Encoder;
import by.kalilaska.ktattoo.entity.AccountEntity;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.serviceexception.MessageFileNotFoundServiceException;
import by.kalilaska.ktattoo.servicemanager.ServiceMessageManager;
import by.kalilaska.ktattoo.servicename.ServiceMessageNameList;
import by.kalilaska.ktattoo.servicetype.AccountRoleType;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class AccountServiceJdbc implements AccountService{
	private final static Logger LOGGER = LogManager.getLogger(AccountServiceJdbc.class);
	private ServiceMessageManager messageManager;
	private String wrongMessage;
	
	private TattooMasterService masterService;
	private AccountDAO accountDao;
	private TransactionManager transactionManager;
	private FormDataValidator validator;
	
	public AccountServiceJdbc() {		
		accountDao = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
		validator = new FormDataValidator();
		initManager();
	}
	
	public AccountServiceJdbc(TattooMasterService masterService) {
		this.masterService = masterService;
		accountDao = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
		validator = new FormDataValidator();
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
	public AccountBean findAccountByName(String name) {		
		transactionManager.beginTransaction(accountDao);
		
		AccountEntity accountEntity = null;
		AccountBean accountBean = null;
		
        try {
        	accountEntity = accountDao.findAccountByName(name);
            if(accountEntity != null) {
            	accountBean = convertEntityToBean(accountEntity);
            	accountBean.setPassword(accountEntity.getPassword());
            }
        	
        	transactionManager.commit();
        } catch (SQLDataException e) {        	
        	transactionManager.rollback();
        	LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
        }		
        transactionManager.endTransaction();        
        return accountBean;
	}
	

	@Override
	public List<AccountBean> findAccountByNameOrEmail(String name, String email) {
		transactionManager.beginTransaction(accountDao);
		
		List<AccountEntity> accountEntityList = null;
		List<AccountBean> accountBeanList = null;		
		AccountBean accountBean = null;
		
        try {
        	accountEntityList = accountDao.findAccountByNameOrEmail(name, email);
            if(accountEntityList != null && !accountEntityList.isEmpty()) {
            	accountBeanList = new LinkedList<>();
            	for (AccountEntity accountEntity : accountEntityList) {
            		accountBean = convertEntityToBean(accountEntity);
            		accountBeanList.add(accountBean);
				}
            }        	
        	transactionManager.commit();
        } catch (SQLDataException e) {        	
        	transactionManager.rollback();
        	LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
        }		
        transactionManager.endTransaction();
        
        return accountBeanList;
	}
	
	@Override
	public List<AccountBean> findAccountByNameOrEmailOrPass(String name, String email, String pass) {
		transactionManager.beginTransaction(accountDao);
		
		List<AccountEntity> accountEntityList = null;
		List<AccountBean> accountBeanList = null;		
		AccountBean accountBean = null;
		
        try {
        	accountEntityList = accountDao.findAccountByNameOrEmailOrPass(name, email, pass);
            if(accountEntityList != null && !accountEntityList.isEmpty()) {
            	accountBeanList = new LinkedList<>();
            	for (AccountEntity accountEntity : accountEntityList) {
            		
            		accountBean = convertEntityToBean(accountEntity);
            		accountBean.setPassword(accountEntity.getPassword());
            		accountBeanList.add(accountBean);
				}
            }        	
        	transactionManager.commit();
        } catch (SQLDataException e) {        	
        	transactionManager.rollback();
        	LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
        }		
        transactionManager.endTransaction();
        
        return accountBeanList;
	}
	
	@Override
	public List<AccountBean> findAccountByPhotoUrl(String photoUrl) {
		transactionManager.beginTransaction(accountDao);
		
		List<AccountEntity> accountEntityList = null;
		List<AccountBean> accountBeanList = null;		
		AccountBean accountBean = null;
		
        try {
        	accountEntityList = accountDao.findAccountByPhotoUrl(photoUrl);
            if(accountEntityList != null && !accountEntityList.isEmpty()) {
            	accountBeanList = new LinkedList<>();
            	for (AccountEntity accountEntity : accountEntityList) {

            		accountBean = convertEntityToBean(accountEntity);
            		accountBeanList.add(accountBean);
				}
            }        	
        	transactionManager.commit();
        } catch (SQLDataException e) {        	
        	transactionManager.rollback();
        	LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
        }		
        transactionManager.endTransaction();
        
        return accountBeanList;
	}

	@Override
	public List<AccountBean> findAll() {
		transactionManager.beginTransaction(accountDao);
		
		LinkedList<AccountBean> accountBeanList = null;		
		AccountBean accountBean = null;
		
		try {
			List<AccountEntity> accountEntityList = accountDao.findAll();
			
			if(accountEntityList != null && !accountEntityList.isEmpty()) {
				accountBeanList = new LinkedList<>();
				
				for (AccountEntity accountEntity : accountEntityList) {

					accountBean = convertEntityToBean(accountEntity);
					accountBeanList.add(accountBean);
				}
			}
			transactionManager.commit();
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
		}		

		transactionManager.endTransaction();
        
        return accountBeanList;
	}

	@Override
	public boolean forbideAccountById(Integer id) {
		boolean result = false;
		transactionManager.beginTransaction(accountDao);
		try {			
			result = accountDao.forbideAccountById(id);			
			transactionManager.commit();
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
		}		
		transactionManager.endTransaction();
		
		return result;
	}

	@Override
	public boolean allowAccountById(Integer id) {		
		boolean result = false;
		transactionManager.beginTransaction(accountDao);
		try {
			result = accountDao.allowAccountById(id);
			transactionManager.commit();
		}catch(SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		
		return result;
	}

	@Override
	public AccountBean create(String name, String email, String password) {
		AccountBean accountBean = null;
		AccountEntity accountEntity = new AccountEntity(name, email, password);		
		transactionManager.beginTransaction(accountDao);
		try {			
			accountEntity = accountDao.create(accountEntity);
			
			if(accountEntity != null) {
				accountBean = convertEntityToBean(accountEntity);
			}
			transactionManager.commit();			
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();
		return accountBean;
	}

	@Override
	public boolean deleteAccountById(Integer id) {
		boolean result = false;
		transactionManager.beginTransaction(accountDao);
		try {			
			result = accountDao.delete(id);			
			transactionManager.commit();
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
		}		
		transactionManager.endTransaction();
		
		return result;
	}

	@Override
	public boolean updateAccount(AccountBean account) {
		boolean accountUpdated = false;
		boolean masterDeleted = false;
		wrongMessage = null;
		
		int id = account.getId();
		String name = account.getName();
		String email = account.getEmail();
		String phone = account.getPhone();
		String role = account.getRole();		
		
		if(name != null && validator.validate(name, DataType.NAME) 
				&& email != null && validator.validate(email, DataType.EMAIL) 
				&& phone != null && (phone.length() == 0 || validator.validate(phone, DataType.PHONE)) 
				&& role != null) {			
			List<AccountBean> nameEmailCheckList = findAccountByNameOrEmail(account.getName(), account.getEmail());
			
			if(nameEmailCheckList != null && matchAccountIdWithList(id, nameEmailCheckList) == false) {
				wrongMessage = makeWrongMessage(ServiceMessageNameList.EDIT_ACCOUNT_DATA_ALREADY_EXISTS);				
			} else {
				AccountEntity accountEntity = new AccountEntity();
				accountEntity.setId(id);
				accountEntity.setName(name);
				accountEntity.setEmail(email);
				if(phone.length() > 0) {
					accountEntity.setPhone(phone);
				}				
				accountEntity.setRole(role);				
				AccountRoleType roleType = AccountRoleType.valueOf(role.toUpperCase());
				
				transactionManager.beginTransaction(accountDao, masterService.getDao());
				try {
					if(roleType != AccountRoleType.MASTER && masterService != null) {						
						masterDeleted = masterService.deleteMasterById(id);
						accountUpdated = masterDeleted;
					}
					accountUpdated = accountDao.update(accountEntity);
					if(roleType == AccountRoleType.MASTER && masterService != null) {						
						TattooMasterBean masterBean = masterService.create(id, null);
							
						accountUpdated = masterBean != null ? true : false;
					}
					transactionManager.commit();
				}catch (SQLDataException e) {
					transactionManager.rollback();
					LOGGER.log(Level.ERROR, "exception in AccountDAO or TattooMasterDAO: " + e.getMessage());
				}				
				transactionManager.endTransaction();
			}
		}else {
			wrongMessage = makeWrongMessage(ServiceMessageNameList.EDIT_ACCOUNT_DATA_INVALID);			
		}
		return accountUpdated;
	}
	
	private boolean matchAccountIdWithList(int id, List<AccountBean> accountCheckList) {
		boolean result = true;
		if(accountCheckList != null) {
			for (AccountBean accountBean : accountCheckList) {
				if(id != accountBean.getId()) {
					result = false;
				}
			}
		}		
		return result;
	}
	
	@Override
	public boolean updateProfile(AccountBean account) {
		boolean result = false;
		boolean updateWithPass = false;
		wrongMessage = null;
		
		int id = account.getId();
		String name = account.getName();
		String email = account.getEmail();
		String phone = account.getPhone();
		String pass = account.getPassword();
		String confirmPass = account.getConfirmPassword();
		String role = account.getRole();
		
		if(name != null && validator.validate(name, DataType.NAME) 
				&& email != null && validator.validate(email, DataType.EMAIL)) {
			
			List<AccountBean> accountCheckList = null;
			
			if(pass != null && validator.validate(pass, DataType.PASS) 
					&& confirmPass != null && pass.equals(confirmPass)) {
				
				updateWithPass = true;
				pass = MD5Encoder.encode(pass);
				accountCheckList = findAccountByNameOrEmailOrPass(name, email, pass);
			}else {
				accountCheckList = findAccountByNameOrEmail(name, email);
			}
			
			if(accountCheckList != null && matchAccountIdWithList(id, accountCheckList) == false) {
				wrongMessage = makeWrongMessage(ServiceMessageNameList.EDIT_PROFILE_DATA_ALREADY_EXISTS);								
			} else {
				AccountEntity accountEntity = new AccountEntity();
				accountEntity.setId(id);
				accountEntity.setName(name);
				accountEntity.setEmail(email);
				accountEntity.setRole(role);
				if(phone != null && validator.validate(phone, DataType.PHONE)) {
					accountEntity.setPhone(phone);
				}
				
				transactionManager.beginTransaction(accountDao);
				if(updateWithPass) {
					accountEntity.setPassword(pass);
					try {			
						result = accountDao.updateWithPass(accountEntity);
						transactionManager.commit();
					}catch (SQLDataException e) {
						transactionManager.rollback();
						LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
					}
				}else {
					try {			
						result = accountDao.update(accountEntity);
						transactionManager.commit();
					}catch (SQLDataException e) {
						transactionManager.rollback();
						LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
					}
				}				
				transactionManager.endTransaction();
			}
		}else {
			wrongMessage = makeWrongMessage(ServiceMessageNameList.EDIT_ACCOUNT_DATA_INVALID);			
		}
		return result;
	}
	
	@Override
	public boolean updatePhotoUrl(AccountBean account) {
		boolean updated = false;
		wrongMessage = null;
		int id = account.getId();
		String photoUrl = account.getPhotoURL();
		List<AccountBean> photoCheckList = null;
		
		if(photoUrl != null && !photoUrl.isEmpty()) {
			photoCheckList = findAccountByPhotoUrl(photoUrl);
			if(photoCheckList == null || photoCheckList.isEmpty()) {
				AccountEntity accountEntity = new AccountEntity();
				accountEntity.setId(id);
				accountEntity.setPhotoURL(photoUrl);
				
				transactionManager.beginTransaction(accountDao);
				try {			
					updated = accountDao.updatePhotoUrl(accountEntity);
					transactionManager.commit();
				}catch (SQLDataException e) {
					transactionManager.rollback();
					LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
				}
				transactionManager.endTransaction();
			}else {
				wrongMessage = makeWrongMessage(ServiceMessageNameList.UPDATE_AVATAR_DATA_ALREADY_EXISTS);				
			}
		}else {			
			wrongMessage = makeWrongMessage(ServiceMessageNameList.UPDATE_AVATAR_DATA_INVALID);
		}
		return updated;
	}
	
	@Override
	public boolean deletePhotoUrl(Integer id) {
		boolean deleted = false;
		wrongMessage = null;		
		
		if(id != null) {
			transactionManager.beginTransaction(accountDao);
			try {			
				deleted = accountDao.deletePhotoUrl(id);
				transactionManager.commit();
			}catch (SQLDataException e) {
				transactionManager.rollback();
				LOGGER.log(Level.ERROR, "exception in AccountDAO: " + e.getMessage());
			}
			transactionManager.endTransaction();
		}else {			
			wrongMessage = makeWrongMessage(ServiceMessageNameList.DELETE_AVATAR_DATA_INVALID);
		}
		
		return deleted;
	}
	
	private String makeWrongMessage(String messagePath) {
		String message = null;
		if(messageManager != null) {
			message = messageManager.getProperty(messagePath);
		}
		return message;
	}

	@Override
	public String getWrongMessage() {		
		return wrongMessage;
	}
}
