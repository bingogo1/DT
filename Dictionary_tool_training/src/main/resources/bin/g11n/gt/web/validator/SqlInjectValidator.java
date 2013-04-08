package bin.g11n.gt.web.validator;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * check search condition field in case a user injects attacking SQL sub statement into it.
 * 
 * SqlInjectValidator.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class SqlInjectValidator  extends FieldValidatorSupport {

    /**
     * validate 
     *
     * @param object
     * @throws ValidationException  void
     */
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);

        // if there is no value - don't do comparison
        // if a value is required, a required validator should be added to the field
        if (value == null || value.toString().length() == 0) {
            return;
        }

        if (!(value.getClass().equals(String.class)) || !checkKeyOfSql(value)) {
            addFieldError(fieldName, object);
        }

    }
    
    /**
     * checkKeyOfSql 
     *
     * @param Object
     * @return  boolean false: illegal sql; true: legal sql
     */
    private boolean checkKeyOfSql(Object fieldValue) {
    	boolean flg = true;
    	try {
    		String str = fieldValue.toString();
		//only check string which is not blank.
    	if (StringUtils.isNotBlank(str)) {
    		//These string are forbidden in the search field.
    		String[] sqlKeys = new String[]{
    				" WHERE ", 
    				" OR ", 
    				" || ", 
    				" UNION ", 
    				" JOIN ", 
    				" FROM ", 
    				" DELETE ", 
    				" UPDATE ", 
    				" INSERT " 
    				};
    		for (int i = 0; i < sqlKeys.length; i ++) {
    			if (str.toUpperCase().indexOf(sqlKeys[i]) != -1) {
    				//found illegal char.
    				flg = false;
    				break;
    			}
    		}
    	}
    	}catch (Exception ex) {
    		//if sql condition is not string, return false.
    		return false;
    	}
    	return flg;
    }

}
