package by.kalilaska.ktattoo.webutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import by.kalilaska.ktattoo.webtype.LanguageType;

public class DateUtil {
	private final static String EN_DATE_PATTERN = "dd MMMM 'at' HH:mm";
	private final static String BE_DATE_PATTERN = "dd MMMM 'ў' HH:mm";
	
	private GregorianCalendar currentDate = new GregorianCalendar();
	
	public String writeDate(Date date) {
		String datePattern = EN_DATE_PATTERN;
		if(LanguageType.getCurrentLocale() == LanguageType.BE) {
			datePattern = BE_DATE_PATTERN;			
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				datePattern, new Locale(LanguageType.getCurrentLanguage()));
		
		return dateFormat.format(date);
	}
	
	public FollowDayType getFollowDayType(Date date) {
		FollowDayType dayType = FollowDayType.FUTURE;
		
		GregorianCalendar comparedDate = new GregorianCalendar();
		comparedDate.setTime(date);
		
		if(currentDate.before(comparedDate) && 
				comparedDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)) {
			dayType = FollowDayType.TODAY;
		}
		
		if(currentDate.before(comparedDate) && 
				(comparedDate.get(Calendar.DAY_OF_YEAR) - currentDate.get(Calendar.DAY_OF_YEAR)) == 1) {
			dayType = FollowDayType.TOMORROW;
		}
		
		return dayType;
	}
	
	public boolean isBefore(Date date) {
//		System.out.println("current day: " + currentDate.get(Calendar.DAY_OF_YEAR));
//		GregorianCalendar comparedDate = new GregorianCalendar();
//		comparedDate.setTime(date);
//		System.out.println("compared day: " + comparedDate.get(Calendar.DAY_OF_YEAR));
		return currentDate.getTime().before(date);
	}

}
