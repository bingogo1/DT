package bin.g11n.gt.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.ILogger.ELevel;
import bin.g11n.gt.model.LabelValue;



/**
 * Utility class to convert one object to another.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public final class ConvertUtil {
    //~ Static fields/initializers =============================================

	private static transient final ILogger logger = G11nLogFactory.getLog(ConvertUtil.class);
	

    //~ Methods ================================================================

    /**
     * Method to convert a ResourceBundle to a Map object.
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map<String,String> convertBundleToMap(ResourceBundle rb) {
        Map<String,String> map = new HashMap<String, String>();

        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            map.put(key, rb.getString(key));
        }

        return map;
    }
    
    public static Map<String,String> convertListToMap(List<LabelValue> list) {
        Map<String,String> map = new LinkedHashMap<String, String>();

        for (LabelValue option : list) {
            map.put(option.getLabel(), option.getValue());
        }
        
        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties(ResourceBundle rb) {
        Properties props = new Properties();

        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            props.put(key, rb.getString(key));
        }

        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a
     * ResourceBundle
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject(Object obj, ResourceBundle rb) {
        try {
            Map<String,String> map = convertBundleToMap(rb);

            BeanUtils.copyProperties(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(ELevel.ERROR, "Exception occured populating object: " + e.getMessage());
        }

        return obj;
    }
}
