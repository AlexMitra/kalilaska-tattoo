package by.kalilaska.ktattoo.validator;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import by.kalilaska.ktattoo.servicetype.DataType;

@RunWith(Parameterized.class)
public class FormDataValidatorTest {
	
	private String formData;
	private DataType type;
	private boolean condition;

	public FormDataValidatorTest(String formData, DataType type, boolean condition) {		
		this.formData = formData;
		this.type = type;
		this.condition = condition;
	}
	
	@Parameterized.Parameters
    public static Collection<Object[]> testData(){
    	String formData1 = null;
    	DataType type1 = null;
    	boolean condition1 = false;
    	
    	String formData2 = "";
    	DataType type2 = DataType.NAME;
    	boolean condition2 = false;
    	
    	String formData3 = "25Vader";
    	DataType type3 = DataType.NAME;
    	boolean condition3 = false;
    	
    	String formData4 = "Vader";
    	DataType type4 = DataType.NAME;
    	boolean condition4 = true;
    	
    	String formData5 = "Vader25";
    	DataType type5 = DataType.NAME;
    	boolean condition5 = true;
    	
    	String formData6 = "Женя Вегас";
    	DataType type6 = DataType.NAME;
    	boolean condition6 = true;
    	
    	String formData7 = "Женя_Вегас";
    	DataType type7 = DataType.NAME;
    	boolean condition7 = true;
    	
    	String formData8 = "77777777";
    	DataType type8 = DataType.NAME;
    	boolean condition8 = false;
    	
    	String formData9 = "Ole";
    	DataType type9 = DataType.NAME;
    	boolean condition9 = false;
    	
    	String formData10 = "22";
    	DataType type10 = DataType.PASS;
    	boolean condition10 = false;
    	
    	String formData11 = "222";
    	DataType type11 = DataType.PASS;
    	boolean condition11 = true;
    	
    	String formData12 = "222Ole";
    	DataType type12 = DataType.PASS;
    	boolean condition12 = true;
    	
    	String formData13 = "222_Ole";
    	DataType type13 = DataType.PASS;
    	boolean condition13 = false;
    	
    	String formData14 = "vader@tut.by";
    	DataType type14 = DataType.EMAIL;
    	boolean condition14 = true;
    	
    	String formData15 = "vader(darth)@tut.by";
    	DataType type15 = DataType.EMAIL;
    	boolean condition15 = false;
    	
    	String formData16 = "vader-darth@tut.by";
    	DataType type16 = DataType.EMAIL;
    	boolean condition16 = true;
    	
    	String formData17 = "vader_darth@tut.by";
    	DataType type17 = DataType.EMAIL;
    	boolean condition17 = true;
    	
    	String formData18 = "29639l747";
    	DataType type18 = DataType.PHONE;
    	boolean condition18 = false;
    	
    	String formData19 = "296391747";
    	DataType type19 = DataType.PHONE;
    	boolean condition19 = true;
    	
    	String formData20 = "296091747";
    	DataType type20 = DataType.PHONE;
    	boolean condition20 = true;
    	
    	String formData21 = "296o91747";
    	DataType type21 = DataType.PHONE;
    	boolean condition21 = false;
    	
        Object[][] data = new Object[][]{{formData1, type1, condition1},
            {formData2, type2, condition2}, {formData3, type3, condition3},
            {formData4, type4, condition4}, {formData5, type5, condition5}, 
            {formData6, type6, condition6}, {formData7, type7, condition7}, 
            {formData8, type8, condition8}, {formData9, type9, condition9}, 
            {formData10, type10, condition10}, {formData11, type11, condition11}, 
            {formData12, type12, condition12}, {formData13, type13, condition13}, 
            {formData14, type14, condition14}, {formData15, type15, condition15}, 
            {formData16, type16, condition16}, {formData17, type17, condition17}, 
            {formData18, type18, condition18}, {formData19, type19, condition19}, 
            {formData20, type20, condition20}, {formData21, type21, condition21}};            
    	return Arrays.asList(data);
    } 

	@Test
	public void testValidate() {		
		Assert.assertTrue(FormDataValidator.validate(formData, type) == condition);
	}

}