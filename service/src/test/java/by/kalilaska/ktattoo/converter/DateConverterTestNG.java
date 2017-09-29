package by.kalilaska.ktattoo.converter;

import java.util.Date;
import java.util.GregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DateConverterTestNG {
	@Test
	public void testConvertNullToDate() {
		String source = null;
		Assert.assertNull(DateConverter.convertStringToDate(source), "method should return null");
	}
	
	@Test
	public void testConvertEmptyToDate() {
		String source = "";
		Assert.assertNull(DateConverter.convertStringToDate(source), "method should return null");
	}
	
	@Test
	public void testConvertInvalidToDate() {
		String source = "2017:05:05_17-15";
		Assert.assertNull(DateConverter.convertStringToDate(source), "method should return null, input in yyyy-mm-dd hh:mm format");
	}
	
	@Test
	public void testConvertStringToDate() {
		String source = "2017-05-05 17:15";
		GregorianCalendar calendar = new GregorianCalendar(2017, 04, 05, 17, 15);		
		Date result = calendar.getTime();
		Date converted = DateConverter.convertStringToDate(source);		
		boolean condition = converted.equals(result);
		Assert.assertTrue(condition, "method should return valid date");
	}
	
	@Test
	public void testConvertStringToDateTwo() {
		String source = "2017-13-05 17:15";
		Assert.assertNull(DateConverter.convertStringToDate(source), "method should return null, month should be less than 11");
	}
}
