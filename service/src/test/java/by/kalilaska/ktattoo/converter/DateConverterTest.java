package by.kalilaska.ktattoo.converter;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class DateConverterTest {

	@Test
	public void testConvertNullToDate() {
		String source = null;
		Assert.assertNull("method should return null", DateConverter.convertStringToDate(source));
	}
	
	@Test
	public void testConvertEmptyToDate() {
		String source = "";
		Assert.assertNull("method should return null", DateConverter.convertStringToDate(source));
	}
	
	@Test
	public void testConvertInvalidToDate() {
		String source = "2017:05:05_17-15";
		Assert.assertNull("method should return null, input in yyyy-mm-dd hh:mm format", DateConverter.convertStringToDate(source));
	}
	
	@Test
	public void testConvertStringToDate() {
		String source = "2017-05-05 17:15";
		GregorianCalendar calendar = new GregorianCalendar(2017, 04, 05, 17, 15);		
		Date result = calendar.getTime();
		Date converted = DateConverter.convertStringToDate(source);		
		boolean condition = converted.equals(result);
		Assert.assertTrue("method should return valid date", condition);
	}
	
	@Test
	public void testConvertStringToDateTwo() {
		String source = "2017-13-05 17:15";
		Assert.assertNull("method should return null, month should be less than 11", DateConverter.convertStringToDate(source));
	}
}
