package by.kalilaska.ktattoo.service;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;

public interface RegistrationService {
	AbstractPersonalAreaViewBean registerAccount(String name, String email, String password, String confirmPassword);
	String getWorningMessage();
}
