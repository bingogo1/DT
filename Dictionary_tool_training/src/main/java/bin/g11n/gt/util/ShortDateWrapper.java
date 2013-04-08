package bin.g11n.gt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ShortDateWrapper implements DisplaytagColumnDecorator {

	public Object decorate(Object columnValue, PageContext context, MediaTypeEnum media)
			throws DecoratorException {
		if (columnValue instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format((Date)columnValue);
		}
		return "";
	}

}
