package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.AccountBean;
import by.kalilaska.ktattoo.entity.AccountEntity;

public interface AccountService extends BaseService<AccountBean, AccountEntity>{
	
	default AccountBean convertEntityToBean(AccountEntity entity) {
		AccountBean accountBean = null;
		if(entity != null) {
			accountBean = new AccountBean(entity.getId(), 
					entity.getName(), 
					entity.getEmail(),         			
					entity.getPhone(), 
					entity.getPhotoURL(), 
					entity.isAllowed(), 
					entity.getRole());
		}
		return accountBean;
	}
	
	String getWrongMessage();
	
	AccountBean findAccountByName(String name);
	List<AccountBean> findAccountByNameOrEmail(String name, String email);
	List<AccountBean> findAccountByNameOrEmailOrPass(String name, String email, String pass);
	List<AccountBean> findAccountByPhotoUrl(String photoUrl);
	List<AccountBean> findAll();
	boolean forbideAccountById(Integer id);
	boolean allowAccountById(Integer id);
	AccountBean create(String name, String email, String password);
	boolean updateAccount(AccountBean account);
	boolean updateProfile(AccountBean account);
	boolean updatePhotoUrl(AccountBean account);
	boolean deletePhotoUrl(Integer id);
	boolean deleteAccountById(Integer id);
}
