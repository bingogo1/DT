package bin.g11n.gt.security.cache.info;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataRetrievalFailureException;

import bin.g11n.gt.model.Role;

/**
 * EhCacheRoleByName.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class EhCacheRoleByName implements RoleByNameCache {
	private static final Log logger = LogFactory.getLog(EhCacheRoleByName.class);
	private Cache cache;

    //~ Methods ================================================================

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }

	public Role getRoleByRoleNameCache(String roleName) {
		 Element element = null;
         logger.debug("get Authority by functionname from cache with ");
	        try {
	            element = cache.get(roleName);
	        } catch (CacheException cacheException) {
	            throw new DataRetrievalFailureException("Cache failure: "
	                + cacheException.getMessage());
	        }

	        if (logger.isDebugEnabled()) {
	            logger.debug("Cache hit: " + (element != null) + "; username: "
	                + roleName);
	        }

	        if (element == null) {
	            return null;
	        } else {
	            return (Role) element.getValue();
	        }
	}

	public void putRoleInCache(Role role) {
		logger.debug("put Authority by functionname from cache  ");
		Element element = new Element(role.getName(),(Serializable)role);

        if (logger.isDebugEnabled()) {
            logger.debug("Cache put: " + element.getKey());
        }

        cache.put(element);
	}

	public void removeRoleFromCache(String roleName) {
		
	}
	
}
