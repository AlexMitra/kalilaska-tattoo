package by.kalilaska.ktattoo.converter;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class BigDecimalConverterTest {

	@Test
	public void testConvertNullToBigDecimal() {
		String source = null;
        Assert.assertNull("method should return null", 
        		BigDecimalConverter.convertStringToBigDecimal(source));
	}
	
	@Test
	public void testConvertEmptyStringToBigDecimal() {
		String source = "";
		Assert.assertNull("method should return null", 
				BigDecimalConverter.convertStringToBigDecimal(source));
	}
	
	@Test
	public void testConvertInvalidStringToBigDecimal() {
		String source = "l5";
		Assert.assertNull("method should return null", 
				BigDecimalConverter.convertStringToBigDecimal(source));
	}
	
	@Test
	public void testConvertStringToBigDecimalOne() {
		String source = "0.99";
		double result = 0.99;
		boolean condition = BigDecimalConverter.convertStringToBigDecimal(source).doubleValue() == result;
		assertTrue("condition should be true", condition);
	}
	
	@Test
	public void testConvertStringToBigDecimalTwo() {
		String source = "-0.99";		
		Assert.assertNull("price should be positive number, converter return null", 
				BigDecimalConverter.convertStringToBigDecimal(source));		
	}
	
	@Test
	public void testConvertStringToBigDecimalThree() {
		String source = "0.01";
		double result = 0.01;
		boolean condition = BigDecimalConverter.convertStringToBigDecimal(source).doubleValue() == result;
		assertTrue("condition should be true", condition);
	}
	
	@Test
	public void testConvertStringToBigDecimalFour() {
		String source = "99";
		double result = 99;
		boolean condition = BigDecimalConverter.convertStringToBigDecimal(source).doubleValue() == result;
		assertTrue("condition should be true", condition);
	}
}
