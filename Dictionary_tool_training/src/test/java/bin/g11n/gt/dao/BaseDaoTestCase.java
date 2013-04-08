package bin.g11n.gt.dao;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.beans.BeanUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.DateUtil;


/**
 * Base class for running DAO tests.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public abstract class BaseDaoTestCase extends AbstractTransactionalDataSourceSpringContextTests {
    protected transient final ILogger logger = G11nLogFactory.getLog(getClass());
    protected ResourceBundle rb;
    protected User testUser;
    protected Date newDate;
    

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String [] {"classpath*:/applicationContext-service.xml",
        				"classpath*:/applicationContext-resources.xml",
        				"classpath*:/applicationContext-dao.xml"};
    }
    
    public BaseDaoTestCase() {
        // Since a ResourceBundle is not required for each class, just
        // do a simple check to see if one exists
        String className = this.getClass().getName();

        try {
            rb = ResourceBundle.getBundle(className);
            testUser = SecurityUtil.getUsersByUserIdStr("1").get(0);
            newDate = DateUtil.getNewDate();
        } catch (MissingResourceException mre) {
            logger.log(ILogger.ELevel.WARNING, "No resource bundle found for: " + className);
        }
    }

    /**
     * Utility method to populate a javabean-style object with values
     * from a Properties file
     * @param obj the model object to populate
     * @return Object populated object
     * @throws Exception if BeanUtils fails to copy properly
     */
    protected Object populate(Object obj) throws Exception {
        // loop through all the beans methods and set its properties from
        // its .properties file
        Map<String, String> map = new HashMap<String, String>();

        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            map.put(key, rb.getString(key));
        }

        BeanUtils.copyProperties(map, obj);

        return obj;
    }
}
