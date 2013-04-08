package bin.g11n.gt.security.cache;

import org.acegisecurity.GrantedAuthority;

/**
 * AuthorityBasedFunctionCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface AuthorityBasedFunctionCache {
	
	/**
	 * get all roles with the same function name
	 * getAuthorityFromCache 
	 *
	 * @param functionname
	 * @return  GrantedAuthority[]
	 */
	public GrantedAuthority[] getAuthorityFromCache(String functionname);

	/** put Authority by functionname from cache
	 * 
	 * putAuthorityInCache 
	 *
	 * @param functionname
	 * @param grantedAuthority  void
	 */
	public void putAuthorityInCache(String functionname, GrantedAuthority[] grantedAuthority );
	
	/** remove Authority by functionname from cache
	 * 
	 * removeAuthorityFromCache 
	 *
	 * @param functionname  void
	 */
	public void removeAuthorityFromCache(String functionname);
}
