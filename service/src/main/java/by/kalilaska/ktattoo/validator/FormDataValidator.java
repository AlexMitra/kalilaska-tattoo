package by.kalilaska.ktattoo.validator;

import java.util.HashMap;

import by.kalilaska.ktattoo.servicetype.DataType;

public class FormDataValidator {
	
	private final static String REGEX_FOR_NAME = "(([A-Za-zА-Яа-яё]+)([\\wА-Яа-яё]+)?([ A-Za-zА-Яа-яё]+)?){5,30}";
	private final static String REGEX_FOR_PASS = "([A-Za-z0-9]){3,30}";	
	private final static String REGEX_FOR_EMAIL = "[\\w-]+(\\.[\\w-]+)*@[\\w-]+\\.[\\w]+";
	private final static String REGEX_FOR_PHONE = "([\\d]){9}";
	private final static String REGEX_FOR_DATE = "[\\d]{4}[\\-][\\d]{2}[\\-][\\d]{2}[\\p{Blank}][\\d]{2}:[\\d]{2}";
	private final static String REGEX_FOR_COST = "[\\d]{1,2}([\\.]([\\d]{1,2})?)?";
	
	private static final HashMap<DataType, String> regexMap = new HashMap<>();
	
	static {
		regexMap.put(DataType.NAME, REGEX_FOR_NAME);
		regexMap.put(DataType.PASS, REGEX_FOR_PASS);
		regexMap.put(DataType.EMAIL, REGEX_FOR_EMAIL);
		regexMap.put(DataType.PHONE, REGEX_FOR_PHONE);
		regexMap.put(DataType.DATE, REGEX_FOR_DATE);
		regexMap.put(DataType.COST, REGEX_FOR_COST);
	}
	
	public static boolean validate(String data, DataType dataType) {		
		boolean result = false;
		if(data != null && dataType != null) {
			String regex = regexMap.get(dataType);			
			if(regex != null) {
				result = data.matches(regex);				
			}
		}		
		return result;		
	}
}
