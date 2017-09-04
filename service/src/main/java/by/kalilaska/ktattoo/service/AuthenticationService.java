package by.kalilaska.ktattoo.service;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;

public interface AuthenticationService {
	
	AbstractPersonalAreaViewBean checkAccount(String name, String password);
	String getWorningMessage();
}
