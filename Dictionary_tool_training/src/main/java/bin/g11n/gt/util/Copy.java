package bin.g11n.gt.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.ILogger.ELevel;
import bin.g11n.gt.security.ADCheck;


/**
 * Copy.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class Copy {
	private static transient final ILogger logger = G11nLogFactory.getLog(ADCheck.class);

	private static final int BUFFER_SIZE = 256;
	

	/**
	 * copy 
	 *
	 * @param obj
	 * @return  Object
	 */
	public static synchronized Object copy(Object obj) {
	    Object newObject = null;

	    try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE);
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(obj);
	        byte buf[] = baos.toByteArray();

	        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	        ObjectInputStream ois = new ObjectInputStream(bais);
	        newObject = ois.readObject();
	        ois.close();

	    } catch (IOException ioe) {
	    	logger.log(ELevel.FATAL, "IOException", ioe);

	    } catch (ClassNotFoundException cnfe) {
	    	logger.log(ELevel.FATAL, "ClassNotFoundException", cnfe);
	    }
	    return newObject;
	}
}