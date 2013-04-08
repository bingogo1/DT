package bin.g11n.gt.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

/**
 * Checker.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */

public class Checker
{

    private static Log log = LogFactory.getLog("ExtendFieldChecks");

    public Checker()
    {
    }

    public static boolean isAlphabet(String value)
    {
        boolean flg = false;
        flg = GenericValidator.matchRegexp(value, "^[a-zA-Z]*$");
        return flg;
    }

    public static boolean isNumber(String value)
    {
        boolean flg = false;
        flg = GenericValidator.matchRegexp(value, "^[0-9]*$");
        return flg;
    }

    public static boolean isAlphaNum(String value)
    {
        boolean flg = false;
        flg = GenericValidator.matchRegexp(value, "^[0-9a-zA-Z]*$");
        return flg;
    }

    public static boolean isProperNoFormat(String value, int integerDigit, int decimalsDigit)
    {
        if(!NumberUtils.isNumber(value))
            return false;
        if(value.startsWith("-"))
            value = value.substring(1, value.length());
        String values[] = StringUtils.split(value, ".");
        if(!GenericValidator.maxLength(values[0], integerDigit))
            return false;
        return values.length != 2 || GenericValidator.maxLength(values[1], decimalsDigit);
    }

    public static boolean isProperDateFormat(String value, String dateFormat)
    {
        return isProperDateFormat(value, dateFormat, false);
    }

    public static boolean isProperDateFormat(String value, String dateFormat, boolean strict)
    {
        boolean flg = false;
        if(!isNumber(value))
            return flg;
        if((value != null) & (dateFormat != null) && dateFormat.length() > 0)
            flg = GenericValidator.isDate(value, dateFormat, strict);
        return flg;
    }

    public static boolean isNotDoubleByteChars(String value)
    {
        boolean flg = false;
        if(value != null && value.length() != value.getBytes().length)
            flg = false;
        else
            flg = true;
        return flg;
    }

    public static boolean isSingleByteChars(String value)
    {
        boolean flg = false;
        flg = isNotDoubleByteChars(value);
        if(value != null && flg)
        {
            char chars[] = value.toCharArray();
            for(int i = 0; i < chars.length; i++)
            {
                if('\uF8F0' > chars[i] || chars[i] > '\uFF9F')
                    continue;
                flg = false;
                break;
            }

        }
        return flg;
    }

    public static boolean isSingleByteNotSpaceChars(String value)
    {
        boolean flg = false;
        flg = isSingleByteChars(value);
        String space = " ";
        if(flg && value.indexOf(space) != -1)
            flg = false;
        return flg;
    }

    public static boolean isDoubleByteChars(String value)
    {
        boolean flg = false;
        try
        {
            if(value != null && value.length() * 2 == value.getBytes("SJIS").length)
                flg = true;
        }
        catch(UnsupportedEncodingException e)
        {
            log.error(new Exception(e));
        }
        return flg;
    }

}
