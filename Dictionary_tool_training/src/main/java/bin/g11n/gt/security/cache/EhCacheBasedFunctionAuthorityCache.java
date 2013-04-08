package bin.g11n.gt.security.cache;

import java.io.Serializable;

import org.acegisecurity.GrantedAuthority;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.ILogger.ELevel;

/**
 * EhCacheBasedFunctionAuthorityCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class EhCacheBasedFunctionAuthorityCache implements AuthorityBasedFunctionCache,InitializingBean{
	
	private static transient final ILogger logger = G11nLogFactory.getLog(EhCacheBasedFunctionAuthorityCache.class);
	
	private Cache cache;

    //~ Methods ================================================================

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }

	/**
	 * get all roles with the same function name
	 * getAuthorityFromCache 
	 *
	 * @param functionname
	 * @return  GrantedAuthority[]
	 */
	public GrantedAuthority[] getAuthorityFromCache(String functionname) {
		 Element element = null;
           logger.log(ELevel.DEBUG, "get Authority by functionname from cache with ");
	        try {
	            element = cache.get(functionname);
	        } catch (CacheException cacheException) {
	            throw new DataRetrievalFailureException("Cache failure: "
	                + cacheException.getMessage());
	        }

	            logger.log(ELevel.DEBUG, "Cache hit: " + (element != null) + "; username: "
	                + functionname);

	        if (element == null) {
	            return null;
	        } else {
	            return (GrantedAuthority[]) element.getValue();
	        }
	}

	/** put Authority by functionname from cache
	 * 
	 * putAuthorityInCache 
	 *
	 * @param functionname
	 * @param grantedAuthority  void
	 */
	public void putAuthorityInCache(String functionname, GrantedAuthority[] grantedAuthority) {
		logger.log(ELevel.DEBUG, "put Authority by functionname from cache  ");
		Element element = new Element(functionname,(Serializable)grantedAuthority);

            logger.log(ELevel.DEBUG, "Cache put: " + element.getKey());

        cache.put(element);
		
	}

	/** remove Authority by functionname from cache
	 * 
	 * removeAuthorityFromCache 
	 *
	 * @param functionname  void
	 */
	public void removeAuthorityFromCache(String functionname) {
		logger.log(ELevel.DEBUG, "remove Authority by functionname from cache");
		cache.remove(functionname);	
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
	}

}
