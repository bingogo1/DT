package bin.g11n.gt.security.cache;

import bin.g11n.gt.security.cache.info.GrantedFunction;


/** functions by role
 * 
 * FunctionCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface FunctionCache{

	public GrantedFunction[] getFunctionFromCache(String roleName);
	
	public void putFuncitonInCache(String rolename,GrantedFunction[] grantedFunction);
	
	public void removeFunctionFromCache(String roleName);
}
