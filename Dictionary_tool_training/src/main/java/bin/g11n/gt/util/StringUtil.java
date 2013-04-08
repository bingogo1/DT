package bin.g11n.gt.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.LabelValue;



/**
 * String Utility Class This is used to encode passwords programmatically
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class StringUtil extends StringUtils{
    //~ Static fields/initializers =============================================

    private final static Log log = LogFactory.getLog(StringUtil.class);

    //~ Methods ================================================================

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            log.error("Exception: " + e);

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (byte anEncodedPassword : encodedPassword) {
            if ((anEncodedPassword & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(anEncodedPassword & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords
     * as cookies.
     *
     * This is weak encoding in that anyone can use the decodeString
     * routine to reverse the encoding.
     *
     * @param str
     * @return String
     */
    public static String encodeString(String str)  {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encodeBuffer(str.getBytes()).trim();
    }

    /**
     * Decode a string using Base64 encoding.
     *
     * @param str
     * @return String
     */
    public static String decodeString(String str) {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        try {
            return new String(dec.decodeBuffer(str));
        } catch (IOException io) {
        	throw new RuntimeException(io.getMessage(), io.getCause());
        }
    }
    
    /**
     * Align the line content left.
     *
     * @param String,int
     * @return String
     */
    public static String setLeft(String content,int length){
		int count= content.length();
		StringBuffer line = new StringBuffer();
		if(count > length){
			return content;
		}
		else{
			line.append(content);
			for(int i=0;i<length - count - 1;i++){
				line.append(" ");
			}
		}
		return line.toString();
	}
	
    /**
     * Align the line content center.
     *
     * @param string,int
     * @return String
     */
	public static String setCenter(String content,int length){
		int count= content.length();
		StringBuffer line = new StringBuffer();
		if(count > length){
			return content;
		}
		else{
			int mid = (length - count)/2;
			for(int i=0;i<(length - count);i++){
				if(i==mid){
					line.append(content);
				}
				else{
					line.append(" ");
				}
			}
		}
		return line.toString();
	}
	
	/**
     * Align the line content right.
     *
     * @param string,int
     * @return String
     */
	public static String setRight(String content,int length){
		int count= content.length();
		StringBuffer line = new StringBuffer();
		if(count > length){
			return content;
		}
		else{
			int end = length - count - 1;
			for(int i=0;i<(length - count);i++){
				if(i==end){
					line.append(content);
				}
				else{
					line.append(" ");
				}
			}
		}
		return line.toString();
	}
	
	/**
	 * isBlank to test if the string is blank. 
	 *
	 * @param str
	 * @return  boolean
	 */
	public static boolean isBlank(String str) {
		if ((str==null)||(str.equals(""))) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/** just used for the object with the same structure
	 * 
	 * copy 
	 * @param obj
	 * @return  Object
	 */
	public static synchronized Object copy(Object obj) {
	    Object newObject = null;
	    
	    try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(obj);
	        byte buf[] = baos.toByteArray();
	        
	        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	        ObjectInputStream ois = new ObjectInputStream(bais);
	        newObject = ois.readObject();
	        ois.close();

	    } catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());

	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
	    }
	    return newObject;
	}

    /**
     * <p>Copy property values from the origin bean to the destination bean
     * for all cases where the property names are the same.  For each
     * property, a conversion is attempted as necessary.  All combinations of
     * standard JavaBeans and DynaBeans as origin and destination are
     * supported.  Properties that exist in the origin bean, but do not exist
     * in the destination bean (or are read-only in the destination bean) are
     * silently ignored.</p>
     *
     * <p>If the origin "bean" is actually a <code>Map</code>, it is assumed
     * to contain String-valued <strong>simple</strong> property names as the keys, pointing at
     * the corresponding property values that will be converted (if necessary)
     * and set in the destination bean. <strong>Note</strong> that this method
     * is intended to perform a "shallow copy" of the properties and so complex
     * properties (for example, nested ones) will not be copied.</p>
     *
	 * copyObject 
     * @param target Destination bean whose properties are modified
     * @param source Origin bean whose properties are retrieved
	 */
	public static void copyObject(Object target, Object source){
		try {
			BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * copy all elements of source drop down list into the target new drop down list.
	 * 
	 * copyDropDownList 
	 * @param sourceList
	 *            source list
	 * @return list   
	 * 			  target list
	 * @throws GtException
	 */
	public static List copyDropDownList(List sourceList)
			throws GtException {
		List<LabelValue> targetList = new ArrayList<LabelValue>();
		if (sourceList != null) {
			for (int i = 0; i < sourceList.size(); i++) {
				LabelValue obj = new LabelValue();
				StringUtil.copyObject(obj, sourceList.get(i));
				targetList.add((LabelValue) obj);
			}
		}
		return targetList;
	}

}
