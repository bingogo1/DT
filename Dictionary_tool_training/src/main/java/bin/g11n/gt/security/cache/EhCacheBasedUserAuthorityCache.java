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
 * EhCacheBasedUserAuthorityCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class EhCacheBasedUserAuthorityCache implements AuthorityBasedUserCache,InitializingBean{

	private Cache cache;

    //~ Methods ================================================================

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }
	
	public GrantedAuthority[] getAuthorityFromCache(String username) {
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

	public void putAuthorityInCache(String username,GrantedAuthority[] grantedAuthority) {
		Element element = new Element(username,(Serializable)grantedAuthority);
        cache.put(element);
	}


	
	public void removeAuthorityFromCache(String username) {
		cache.remove(username);	
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
	}



}
