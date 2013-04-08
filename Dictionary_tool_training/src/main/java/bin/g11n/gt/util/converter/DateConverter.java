package bin.g11n.gt.util.converter;

import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.ActionContext;

import ognl.OgnlContext;

/**
 * This class is converts a java.util.Date to a String and a String to a
 * java.util.Date.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history (C) Copyright bingogo1@hotmail.com, LP - All Rights Reserved.
 */
public class DateConverter extends StrutsTypeConverter {

	private static final Log logger = LogFactory.getLog(DateConverter.class);

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values.length > 0 && values[0] != null
				&& values[0].trim().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			sdf.setLenient(false);
			try {
				return sdf.parse(values[0]);
			} catch (ParseException e) {
				String fieldName = (String) ((OgnlContext) context).getValues()
						.get("conversion.property.fullName");
				ActionContext ac = ActionContext.getContext();
				ac.getConversionErrors().put(fieldName, null);
				logger.error("error converting value [" + values[0]
						+ "] to Date ", e);
			}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format((Date) o);
		}
		return "";
	}

}
