package bin.g11n.gt.security.cache;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import bin.g11n.gt.security.cache.info.GrantedFunction;



/**
 * EhCacheBasedFunctionCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class EhCacheBasedFunctionCache implements FunctionCache,InitializingBean{

	private Cache cache;

    //~ Methods ================================================================

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }

	public GrantedFunction[] getFunctionFromCache(String roleName) {
		  Element element = null;
	        try {
	            element = cache.get(roleName);
	        } catch (CacheException cacheException) {
	            throw new DataRetrievalFailureException("Cache failure: "
	                + cacheException.getMessage());
	        }

	        if (element == null) {
	            return null;
	        } else {
	            return (GrantedFunction[]) element.getValue();
	        }
	}

	public void putFuncitonInCache(String rolename,GrantedFunction[] grantedFunction) {
		Element element = new Element(rolename,(Serializable)grantedFunction);
        cache.put(element);
	}

	public void removeFunctionFromCache(String roleName) {
		cache.remove(roleName);
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
	}

}
