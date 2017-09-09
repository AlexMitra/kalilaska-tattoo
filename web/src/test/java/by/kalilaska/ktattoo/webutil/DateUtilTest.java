package by.kalilaska.ktattoo.webutil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DateUtilTest {
	
	private static DateUtil dateUtil;
	private static GregorianCalendar calendar = new GregorianCalendar();
	private static GregorianCalendar tomorrow = new GregorianCalendar();
	private Date date;
	private FollowDayType dayType;	
	
	public DateUtilTest(Date date, FollowDayType dayType) {
		this.date = date;
		this.dayType = dayType;
	}
	
	@BeforeClass
	public static void init() {
		dateUtil = new DateUtil();		
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> testData(){
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
		
		Object[][] data = new Object[][]{{date1, dayType1}, {date2, dayType2}, 
			{date4, dayType4}, {date5, dayType5}};
		return Arrays.asList(data);
	}

	@Test
	public void testGetFollowDayType() {
		boolean condition = dateUtil.getFollowDayType(date) == dayType;
		Assert.assertTrue(condition);		
	}
	
	@AfterClass
	public static void checkOut() {
		dateUtil = null;
	}
}
