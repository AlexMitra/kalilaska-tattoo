package by.kalilaska.ktattoo.service.impl;


import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.AccountDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.AccountEntity;
import by.kalilaska.ktattoo.service.AccountService;
import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class AccountServiceJdbc implements AccountService{
	
	private String wrongMessage;
	
	private AccountDAO accountDao;
	private TransactionManager transactionManager;
	private FormDataValidator validator;
	
	public AccountServiceJdbc() {
		accountDao = new AccountDAO();
		transactionManager = new TransactionManager();
		validator = new FormDataValidator();
	}

	@Override
	public AccountBean findAccountByName(String name) {		
		transactionManager.beginTransaction(accountDao);
		
		AccountEntity accountEntity = null;
		AccountBean accountBean = null;
		
        try {
        	accountEntity = accountDao.findAccountByName(name);
            if(accountEntity != null) {        	
            	accountBean = new AccountBean(accountEntity.getId(), 
            			accountEntity.getName(), 
            			accountEntity.getEmail(), 
            			accountEntity.getPassword(), 
            			accountEntity.getPhone(), 
            			accountEntity.getPhotoURL(), 
            			accountEntity.isAllowed(), 
            			accountEntity.getRole());
            	//List<String> roleNameList = roleService.findRoleNamesByAccountId(accountEntity.getId());
            	//accountBean.setRoleNames(roleNameList);
            }
        	
        	transactionManager.commit();
        } catch (DaoSQLException e) {        	
        	transactionManager.rollback();
           // log
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
            		accountBean = new AccountBean(accountEntity.getId(), 
            				accountEntity.getName(), 
            				accountEntity.getEmail(), 
            				accountEntity.getPassword(), 
            				accountEntity.getPhone(), 
            				accountEntity.getPhotoURL(), 
            				accountEntity.isAllowed(), 
            				accountEntity.getRole());
            		accountBeanList.add(accountBean);
				}
            }        	
        	transactionManager.commit();
        } catch (DaoSQLException e) {        	
        	transactionManager.rollback();
           // log
        }		
        transactionManager.endTransaction();
        
        return accountBeanList;
	}

	@Override
	public List<AccountBean> findAll() {
		transactionManager.beginTransaction(accountDao);
		
		LinkedList<AccountBean> accountBeanList = null;
		//AccountEntity accountEntity = null;
		AccountBean accountBean = null;
		
		try {
			List<AccountEntity> accountEntityList = accountDao.findAll();
			
			if(accountEntityList != null && !accountEntityList.isEmpty()) {
				accountBeanList = new LinkedList<>();
				
				for (AccountEntity accountEntity : accountEntityList) {
					accountBean = new AccountBean(accountEntity.getId(), 
							accountEntity.getName(), 
							accountEntity.getEmail(), 
							accountEntity.getPhone(), 
							accountEntity.getPhotoURL(), 
							accountEntity.isAllowed(), 
							accountEntity.getRole());
					accountBeanList.add(accountBean);
				}
			}
			transactionManager.commit();
		}catch (DaoSQLException e) {
			transactionManager.rollback();
	           // log
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
		}catch (DaoSQLException e) {
			transactionManager.rollback();
	           // log
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
		}catch(DaoSQLException e) {
			transactionManager.rollback();
	           // log
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
				accountBean = new AccountBean(accountEntity.getId(), 
						accountEntity.getName(), 
						accountEntity.getEmail(), 
						accountEntity.getPassword(), 
						accountEntity.getPhone(), 
						accountEntity.getPhotoURL(), 
						accountEntity.isAllowed(), 
						accountEntity.getRole());
			}
			transactionManager.commit();			
		}catch (DaoSQLException e) {
			transactionManager.rollback();
	           // log
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
		}catch (DaoSQLException e) {
			transactionManager.rollback();
	           // log
		}		
		transactionManager.endTransaction();
		
		return result;
	}

	@Override
	public boolean updateAccount(AccountBean account) {
		boolean result = false;
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
			if(nameEmailCheckList == null || nameEmailCheckList.size() == 0) {
				
				AccountEntity accountEntity = new AccountEntity();
				accountEntity.setId(id);
				accountEntity.setName(name);
				accountEntity.setEmail(email);
				if(phone.length() > 0) {
					accountEntity.setPhone(phone);
				}				
				accountEntity.setRole(role);
				
				transactionManager.beginTransaction(accountDao);				
				try {			
					result = accountDao.update(accountEntity);
					transactionManager.commit();
				}catch (DaoSQLException e) {
					transactionManager.rollback();
			           // log
				}
				transactionManager.endTransaction();
				
			} else {
				wrongMessage = "name or email already registered in system";
			}
		}else {
			wrongMessage = "name or email or phone is not valid";
		}
		return result;
	}

	@Override
	public String getWrongMessage() {		
		return wrongMessage;
	}

}
