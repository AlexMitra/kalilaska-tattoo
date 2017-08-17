package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;

public interface AccountService extends BaseService{
	
	String getWrongMessage();
	
	AccountBean findAccountByName(String name);
	List<AccountBean> findAccountByNameOrEmail(String name, String email);
	List<AccountBean> findAll();
	boolean forbideAccountById(Integer id);
	boolean allowAccountById(Integer id);
	AccountBean create(String name, String email, String password);
	boolean updateAccount(AccountBean account);
	boolean deleteAccountById(Integer id);
}
