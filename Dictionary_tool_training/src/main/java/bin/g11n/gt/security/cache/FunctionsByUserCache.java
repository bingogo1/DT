package bin.g11n.gt.security.cache;

import org.acegisecurity.GrantedAuthority;

/**
 * FunctionsByUserCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface FunctionsByUserCache {

	public GrantedAuthority[] getFunctionFromCache(String username);
	
	public void putFuncitonInCache(String username,GrantedAuthority[] grantedFunction);
	
	public void removeFunctionFromCache(String username);

}
