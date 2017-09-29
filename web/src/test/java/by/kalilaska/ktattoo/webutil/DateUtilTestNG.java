package by.kalilaska.ktattoo.webutil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DateUtilTestNG {
	
	private static DateUtil dateUtil;
	private static GregorianCalendar calendar;
	private static GregorianCalendar tomorrow;
	
	@BeforeClass
	public static void init() {
		dateUtil = new DateUtil();
		calendar = new GregorianCalendar();
		tomorrow = new GregorianCalendar();
	}
	
	@DataProvider(name="dateAndDateTypeData")
	public static Object[][] testData(){
		Date date1 = null;
		FollowDayType dayType1 = null;
		
		calendar.set(2017, 8, 5, 17, 07);
		Date date2 = calendar.getTime();
		FollowDayType dayType2 = FollowDayType.PAST;

		tomorrow.add(Calendar.DAY_OF_YEAR, 1);
		Date date4 = tomorrow.getTime();
		FollowDayType dayType4 = FollowDayType.TOMORROW;
		
		calendar.set(2018, 8, 5, 17, 07);
		Date date5 = calendar.getTime();
		FollowDayType dayType5 = FollowDayType.FUTURE;
		
		return new Object[][] {
			{date1, dayType1}, 
			{date2, dayType2}, 
			{date4, dayType4},
			{date5, dayType5}
		};
	}
	
	@Test(dataProvider="dateAndDateTypeData")	
	public void testGetFollowDayType(Date date, FollowDayType dayType) {
		boolean condition = dateUtil.getFollowDayType(date) == dayType;
		Assert.assertTrue(condition);		
	}
	
	@AfterClass
	public static void checkOut() {
		dateUtil = null;
	}
}
