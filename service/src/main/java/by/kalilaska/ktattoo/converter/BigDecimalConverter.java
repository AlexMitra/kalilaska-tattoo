package by.kalilaska.ktattoo.converter;

import java.math.BigDecimal;

import by.kalilaska.ktattoo.servicetype.DataType;
import by.kalilaska.ktattoo.validator.FormDataValidator;

public class BigDecimalConverter {

	public static BigDecimal convertStringToBigDecimal(String numStr) {
		BigDecimal result = null;		
		if(numStr != null && FormDataValidator.validate(numStr, DataType.COST)) {
			result = new BigDecimal(numStr);
		}
		return result;
	}
}
