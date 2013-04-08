package bin.g11n.gt.security.cache;

import java.io.Serializable;

import org.acegisecurity.GrantedAuthority;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;



/**
 * EhCacheBasedFunctionCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class EhCacheByFunctionNameCache implements FunctionsByUserCache,InitializingBean{

	private Cache cache;

    //~ Methods ================================================================

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }

	/**
	 * get granted functions by user name
	 * 
	 * getFunctionFromCache 
	 *
	 * @param username
	 * @return  GrantedFunction[]
	 */
	public GrantedAuthority[] getFunctionFromCache(String username) {
		  Element element = null;
	        try {
	            element = cache.get(username);
	        } catch (CacheException cacheException) {
	            throw new DataRetrievalFailureException("Cache failure: "
	                + cacheException.getMessage());
	        }

	        if (element == null) {
	            return null;
	        } else {
	            return (GrantedAuthority[]) element.getValue();
	        }
	}

	/**
	 * put granted functions by user name
	 * 
	 * putFuncitonInCache 
	 *
	 * @param rolename
	 * @param grantedFunction  this object must extends Serializable
	 */
	public void putFuncitonInCache(String username,GrantedAuthority[] grantedFunction) {
		Element element = new Element(username,(Serializable)grantedFunction);
      cache.put(element);
	}

    /**
     * remove granted functions from cache by user name
     * 
     * removeFunctionFromCache 
     *
     * @param username  void
     */
    public void removeFunctionFromCache(String username) {
		cache.remove(username);
;
    }

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
	}
    


}
