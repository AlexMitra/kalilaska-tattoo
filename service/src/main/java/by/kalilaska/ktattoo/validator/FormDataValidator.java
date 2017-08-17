package by.kalilaska.ktattoo.validator;

import java.util.HashMap;

import by.kalilaska.ktattoo.servicetype.DataType;

public class FormDataValidator {
	//private final static String REGEX_FOR_NAME = "(([\\wА-Яа-яё])+(((\\p{Blank}){1})([A-Za-zА-Яа-яё])+)?){5,30}";
	private final static String REGEX_FOR_NAME = "([\\wА-Яа-яё]+([ A-Za-zА-Яа-яё]+)?){5,30}";
	private final static String REGEX_FOR_PASS = "([A-Za-z0-9]){3,30}";
	//private final static String REGEX_FOR_EMAIL = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
	private final static String REGEX_FOR_EMAIL = "[\\w-]+(\\.[\\w-]+)*@[\\w-]+\\.[\\w]+";
	private final static String REGEX_FOR_PHONE = "([\\d]){9}";
	
	private final HashMap<DataType, String> regexMap = new HashMap<>();
	
	{
		regexMap.put(DataType.NAME, REGEX_FOR_NAME);
		regexMap.put(DataType.PASS, REGEX_FOR_PASS);
		regexMap.put(DataType.EMAIL, REGEX_FOR_EMAIL);
		regexMap.put(DataType.PHONE, REGEX_FOR_PHONE);
	}
	
	public boolean validate(String data, DataType dataType) {
		boolean result = false;
		String regex = regexMap.get(dataType);
		if(regex != null && !regex.isEmpty()) {
			result = data.matches(regex);			
		}
		
		return result;		
	}
}
