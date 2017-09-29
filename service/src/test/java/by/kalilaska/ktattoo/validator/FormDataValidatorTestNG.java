package by.kalilaska.ktattoo.validator;

import org.junit.Assert;
import org.testng.annotations.Test;

import by.kalilaska.ktattoo.servicetype.DataType;

public class FormDataValidatorTestNG {
	
	public static Object[][] testData(){
		return new Object[][] {
			{null, null, false},
			{"", DataType.NAME, false}, 
			{"25Vader", DataType.NAME, false}, 
			{"Vader", DataType.NAME, true}, 
			{"Vader25", DataType.NAME, true}, 
			{"Женя Вегас", DataType.NAME, true}, 
			{"Женя_Вегас", DataType.NAME, true}, 
			{"77777777", DataType.NAME, false}, 
			{"Ole", DataType.NAME, false}, 
			{"22", DataType.PASS, false}, 
			{"222", DataType.PASS, true}, 
			{"222Ole", DataType.PASS, true}, 
			{"222_Ole", DataType.PASS, false}, 
			{"vader@tut.by", DataType.EMAIL, true}, 
			{"vader(darth)@tut.by", DataType.EMAIL, false}, 
			{"vader-darth@tut.by", DataType.EMAIL, true}, 
			{"vader_darth@tut.by", DataType.EMAIL, true}, 
			{"29639l747", DataType.PHONE, false}, 
			{"296391747", DataType.PHONE, true}, 
			{"296091747", DataType.PHONE, true}, 
			{"296o91747", DataType.PHONE, false}
		};
	}

	@Test(dataProvider="FormDataAndDataTypeStorage")
	public void testValidate(String formData, DataType type, boolean condition) {		
		Assert.assertTrue(FormDataValidator.validate(formData, type) == condition);
	}

}
