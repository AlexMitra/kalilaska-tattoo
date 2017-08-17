package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.RoleBean;
import by.kalilaska.ktattoo.dao.impl.RoleDAO;

public interface RoleService {
	List<String> findRoleNamesByAccountId(int id);
	List<RoleBean> findAll();
	
	RoleDAO getRoleDao();
}
