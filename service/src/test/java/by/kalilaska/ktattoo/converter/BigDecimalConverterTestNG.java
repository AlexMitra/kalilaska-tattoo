package by.kalilaska.ktattoo.converter;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BigDecimalConverterTestNG {
  
	@Test
	public void testConvertNullToBigDecimal() {
		String source = null;	  
		Assert.assertNull(BigDecimalConverter.convertStringToBigDecimal(source), "method should return null");
	}
  
	@Test
	public void testConvertEmptyStringToBigDecimal() {
		String source = "";
		Assert.assertNull(BigDecimalConverter.convertStringToBigDecimal(source), "method should return null");
	}
	
	@Test
	public void testConvertInvalidStringToBigDecimal() {
		String source = "l5";
		Assert.assertNull(BigDecimalConverter.convertStringToBigDecimal(source), "method should return null");
	}
	
	@Test
	public void testConvertStringToBigDecimalOne() {
		String source = "0.99";
		double result = 0.99;
		boolean condition = BigDecimalConverter.convertStringToBigDecimal(source).doubleValue() == result;
		Assert.assertTrue(condition, "condition should be true");
	}
	
	@Test
	public void testConvertStringToBigDecimalTwo() {
		String source = "-0.99";		
		Assert.assertNull(BigDecimalConverter.convertStringToBigDecimal(source), "price should be positive number, converter return null");		
	}
	
	@Test
	public void testConvertStringToBigDecimalThree() {
		String source = "0.01";
		double result = 0.01;
		boolean condition = BigDecimalConverter.convertStringToBigDecimal(source).doubleValue() == result;
		Assert.assertTrue(condition, "condition should be true");
	}
	
	@Test
	public void testConvertStringToBigDecimalFour() {
		String source = "99";
		double result = 99;
		boolean condition = BigDecimalConverter.convertStringToBigDecimal(source).doubleValue() == result;
		Assert.assertTrue(condition, "condition should be true");
	}

}
