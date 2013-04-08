package bin.g11n.gt.security.cache;

import org.acegisecurity.GrantedAuthority;

/**
 * AuthorityBasedUserCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface AuthorityBasedUserCache {

	public GrantedAuthority[] getAuthorityFromCache(String username);
	

	public void putAuthorityInCache(String username, GrantedAuthority[] grantedAuthority );
	
	public void removeAuthorityFromCache(String username);
}
