package by.kalilaska.ktattoo.converter;

import java.util.Date;
import java.util.GregorianCalendar;

import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class DateConverter {
	private final static int DATE_PART = 0;
	private final static int TIME_PART = 1;
	private final static int YEAR_PART = 0;
	private final static int MONTH_PART = 1;
	private final static int DAY_PART = 2;
	private final static int HOUR_PART = 0;
	private final static int MINUTE_PART = 1;
	
	private final static String DATE_TIME_DELIMITER = "\\p{Blank}";
	private final static String DATE_DELIMITER = "-";
	private final static String TIME_DELIMITER = ":";
	
	private final static int MAX_MONTH_VAL = 11;
	private final static int MAX_DAY_VAL = 31;
	private final static int MAX_HOUR_VAL = 24;
	private final static int MAX_MINUTE_VAL = 60;

	public static Date convertStringToDate(String dateTimeStr) {
		Date date = null;
		
		if(dateTimeStr != null && FormDataValidator.validate(dateTimeStr, DataType.DATE)) {
			
			String dateTimeArr[] = dateTimeStr.split(DATE_TIME_DELIMITER);
			
			String datePart = dateTimeArr[DATE_PART];
			String timePart = dateTimeArr[TIME_PART];
			
			String dateArr[] = datePart.split(DATE_DELIMITER);
			String timeArr[] = timePart.split(TIME_DELIMITER);
			
			int year = Integer.parseInt(dateArr[YEAR_PART]);
			int month = Integer.parseInt(dateArr[MONTH_PART])-1;
			int day = Integer.parseInt(dateArr[DAY_PART]);
			int hour = Integer.parseInt(timeArr[HOUR_PART]);
			int minute = Integer.parseInt(timeArr[MINUTE_PART]);
			
			if(month > MAX_MONTH_VAL || day > MAX_DAY_VAL || hour > MAX_HOUR_VAL || minute > MAX_MINUTE_VAL) {
				return null;
			}
			
			GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);
			date = calendar.getTime();
		}
		
		return date;
	}

}
