package bin.g11n.gt.service.impl.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bin.g11n.cil.CILHelper;
import bin.g11n.cil.CILProducer;
import bin.g11n.cil.bundle.IG11nResourceBundle;
import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.UniversalDao;
import bin.g11n.gt.model.User;
import bin.g11n.gt.service.common.Manager;


/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BaseManager implements Manager {
    protected transient final ILogger logger = G11nLogFactory.getLog(getClass());
    protected IG11nResourceBundle resources;
    protected UniversalDao dao = null;
	/* currentUser the current user */
    protected User currentUser = null;

    protected Date currentTime = null;
    
    /**
     * @see Manager#setDao(UniversalDao)
     */
    public void setDao(UniversalDao dao) {
        this.dao = dao;
    }
    
    /**
     * @Manager#get(java.lang.Class, java.io.Serializable)
     */
    public Object get(Class clazz, Serializable id) {
        return dao.get(clazz, id);
    }
    
    /**
     * @see Manager#getAll(java.lang.Class)
     */
    public List getAll(Class clazz) {
        return dao.getAll(clazz);
    }
    
    /**
     * @see Manager#remove(java.lang.Class, java.io.Serializable)
     */
    public void remove(Class clazz, Serializable id) {
        dao.remove(clazz, id);
    }
    
    /**
     * @see Manager#save(java.lang.Object)
     */
    public Object save(Object o) {
        return dao.save(o);
    }
    
	protected IG11nResourceBundle getBundleInstance() {
		if (resources == null) {
			// Create and set up the window.
			CILHelper cILHelper = new CILHelper((new CILProducer())
					.createInfra());
			// Get interface
			resources = cILHelper.getResourceBundle();
			// Instantiates the bundle class with 1 parameter of baseName.
			resources = resources.getBundle(Constants.BUNDLE_KEY);
		}
		return resources;
	}

}
