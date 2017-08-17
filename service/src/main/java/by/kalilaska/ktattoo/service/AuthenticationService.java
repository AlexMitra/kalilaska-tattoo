package by.kalilaska.ktattoo.service;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;

public interface AuthenticationService extends BaseService {
	
	AbstractPersonalAreaViewBean checkAccount(String name, String password);
	String getWrongMessage();
}
