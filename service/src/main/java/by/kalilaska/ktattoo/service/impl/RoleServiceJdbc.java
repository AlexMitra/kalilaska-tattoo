package by.kalilaska.ktattoo.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.RoleBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.RoleDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.RoleEntity;
import by.kalilaska.ktattoo.service.RoleService;

public class RoleServiceJdbc implements RoleService {
	
	private RoleDAO roleDao;
	private TransactionManager transactionManager;

	public RoleServiceJdbc() {
		roleDao = new RoleDAO();
		transactionManager = new TransactionManager();
	}	

	public RoleDAO getRoleDao() {
		return roleDao;
	}

	@Override
	public List<String> findRoleNamesByAccountId(int id) {
		List<RoleEntity> roleEntityList = null;
		List<String> roleNameList = null;
		
		try {
			roleEntityList = roleDao.findRolesByAccountId(id);
			if(roleEntityList != null && !roleEntityList.isEmpty()) {
				roleNameList = new ArrayList<>();
				for (RoleEntity roleEntity : roleEntityList) {					
					roleNameList.add(roleEntity.getName());					
				}
				
			}
		}catch (DaoSQLException e) {
			// LOG
		}
		return roleNameList;
	}

	@Override
	public List<RoleBean> findAll() {
		transactionManager.beginTransaction(roleDao);
		List<RoleEntity> roleEntityList = null;
		List<RoleBean> roleBeanList = null;
		RoleBean roleBean = null;
		try {			
			roleEntityList = roleDao.findAll();
			
			if(roleEntityList != null && !roleEntityList.isEmpty()) {
				roleBeanList = new LinkedList<>();
				for (RoleEntity roleEntity : roleEntityList) {
					roleBean = new RoleBean(roleEntity.getId(), 
							roleEntity.getName());
					roleBeanList.add(roleBean);
				}					
			}			
			transactionManager.commit();
		}catch (DaoSQLException e) {
			transactionManager.rollback();
	           // log
		}
		transactionManager.endTransaction();		
		return roleBeanList;
	}

}
