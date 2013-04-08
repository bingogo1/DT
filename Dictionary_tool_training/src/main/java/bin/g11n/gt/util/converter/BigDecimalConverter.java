package bin.g11n.gt.util.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Map;

import ognl.OgnlContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BigDecimalConverter extends StrutsTypeConverter{
	
	protected final DecimalFormat formatter = new DecimalFormat("");
	private static final Log logger = LogFactory.getLog(DateConverter.class);
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values.length > 0 && values[0] != null && values[0].trim().length() > 0) {
            try {
                return new BigDecimal(formatter.parse(values[0]).toString());
            }
            catch(ParseException e) {
            	String fieldName = (String)((OgnlContext)context).getValues().get("conversion.property.fullName");
            	ActionContext ac = ActionContext.getContext();
            	ac.getConversionErrors().put(fieldName, null);
                logger.error("error converting value ["+values[0]+"] to Date ", e);
            }
        }
        return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
        if (o instanceof BigDecimal) {
        	return o.toString();
        }
        return "";
	}	
}
