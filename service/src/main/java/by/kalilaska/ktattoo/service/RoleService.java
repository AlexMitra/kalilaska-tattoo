package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.RoleBean;
import by.kalilaska.ktattoo.dao.impl.RoleDAO;
import by.kalilaska.ktattoo.entity.RoleEntity;

public interface RoleService extends BaseService<RoleBean, RoleEntity>{
	
	default RoleBean convertEntityToBean(RoleEntity entity) {
		RoleBean roleBean = null;
		if(entity != null) {
			roleBean = new RoleBean(
					entity.getId(), 
					entity.getName());
		}
		return roleBean;
	}
	
	List<RoleBean> findAll();	
	RoleDAO getRoleDao();
}
