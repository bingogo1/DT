package bin.g11n.gt.security.cache.info;

import bin.g11n.gt.model.Role;


/**
 * RoleByNameCache.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface RoleByNameCache{

	public Role getRoleByRoleNameCache(String roleName);
	
	public void putRoleInCache(Role role);
	
	public void removeRoleFromCache(String roleName);
}
