package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.RoleBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.RoleDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.RoleEntity;
import by.kalilaska.ktattoo.service.DaoFactory;
import by.kalilaska.ktattoo.service.RoleService;

public class RoleServiceImpl implements RoleService {
	private final static Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class);
	
	private RoleDAO roleDao;
	private TransactionManager transactionManager;

	public RoleServiceImpl() {		
		roleDao = DaoFactory.createDao(this.getClass());
		transactionManager = new TransactionManager();
	}	

	public RoleDAO getRoleDao() {
		return roleDao;
	}

	@Override
	public List<RoleBean> findAll() {
		transactionManager.beginTransaction(roleDao);
		List<RoleEntity> roleEntityList = null;
		List<RoleBean> roleBeanList = null;
		RoleBean roleBean = null;
		try {			
			roleEntityList = roleDao.findAll();
			transactionManager.commit();
			if(roleEntityList != null && !roleEntityList.isEmpty()) {
				roleBeanList = new LinkedList<>();
				for (RoleEntity roleEntity : roleEntityList) {
					roleBean = convertEntityToBean(roleEntity);
					roleBeanList.add(roleBean);
				}					
			}			
		}catch (SQLDataException e) {
			transactionManager.rollback();
			LOGGER.log(Level.ERROR, "Exception in RoleDAO: " + e.getMessage());
		}
		transactionManager.endTransaction();		
		return roleBeanList;
	}
}
