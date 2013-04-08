package bin.g11n.gt.service;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.util.ConvertUtil;



public abstract class BaseManagerTestCase extends AbstractDependencyInjectionSpringContextTests {
    //~ Static fields/initializers =============================================

    protected transient final ILogger logger = G11nLogFactory.getLog(getClass());
    protected static ResourceBundle rb = null;

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        
        return new String[] {"classpath*:/action-servlet.xml",
        		            "classpath*:/applicationContext-service.xml",
                             "classpath*:/applicationContext-resources.xml",
                             "classpath*:/applicationContext-dao.xml",
                         	 "classpath*:/security.xml"
                         	};
    }

    //~ Constructors ===========================================================

    public BaseManagerTestCase() {
        // Since a ResourceBundle is not required for each class, just
        // do a simple check to see if one exists
        String className = this.getClass().getName();

        try {
            rb = ResourceBundle.getBundle(className);
        } catch (MissingResourceException mre) {
            //log.warn("No resource bundle found for: " + className);
        }
    }

    //~ Methods ================================================================

    /**
     * Utility method to populate a javabean-style object with values
     * from a Properties file
     *
     * @param obj the model object to populate
     * @return Object populated object
     * @throws Exception if BeanUtils fails to copy properly
     */
    protected Object populate(Object obj) throws Exception {
        // loop through all the beans methods and set its properties from
        // its .properties file
        Map map = ConvertUtil.convertBundleToMap(rb);

        BeanUtils.copyProperties(obj, map);

        return obj;
    }
}

