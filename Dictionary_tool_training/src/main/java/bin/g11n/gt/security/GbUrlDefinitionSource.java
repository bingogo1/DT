package bin.g11n.gt.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.ConfigAttributeEditor;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource;
import org.springframework.util.Assert;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.RoleConstants;
import bin.g11n.gt.dao.common.FunctionDao;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.cache.AuthorityBasedFunctionCache;
import bin.g11n.gt.security.cache.FunctionsByUserCache;


/**
 * get role list by function from DB so that the voter can work with the role
 * list.
 * 
 * GbUrlDefinitionSource.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GbUrlDefinitionSource extends
		AbstractFilterInvocationDefinitionSource {

	private FunctionDao functionDao;

	// contains the pair of <roles, functionname>
	private AuthorityBasedFunctionCache authorityFunctionCache;

	// contains the granted functions of current user.
	private FunctionsByUserCache functionsByUserCache;

	/**
	 * check whether the url need be control the permit. these url need not be
	 * controlled the permit: *.js, *.css, *.png, *.gif .etc Just the *.action
	 * and *.jsp will be controlled.
	 * 
	 * isNeedControlPermit
	 * 
	 * @param reqFunctionName
	 * @return boolean
	 */
	private boolean isNeedControlPermit(String reqFunctionName) {
		try {
			boolean isNeedFlg = false;
			String lowerParm = reqFunctionName.toLowerCase();
			//just only control the security of the url with "xxx.action" and "xxx.jsp" 
			if (lowerParm.lastIndexOf(".action") != -1
					|| lowerParm.lastIndexOf(".jsp") != -1) {
				if (lowerParm.lastIndexOf("login.jsp") != -1) {
					//The login.jsp needs not to control the security.
					isNeedFlg = false;
				}else {
					//xxx.action file or other xxx.jsp will be control the security.
					isNeedFlg = true;
				}
				
			}
			return isNeedFlg;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * convert the function name in the url to meet the patterns: xxx.action and xxx!xxx.action.
	 * 
	 * convertFunctionName
	 * 
	 * @param originalFunctionName
	 * @return boolean
	 */
	private String convertFunctionName(String originalFunctionName) {
		try {
			String target = originalFunctionName;
			//just only control the security of the url with "xxx.action" and "xxx.jsp" 
			if (target.lastIndexOf(".action") != -1) {
				if (target.indexOf("-") != -1 ) {
					int begin = target.indexOf("-");
					int end = target.indexOf(".");
					//If the name contains a parameter beginning with "-", get rid of it.
					if (target.indexOf("!") != -1 ) {
						end = target.indexOf("!");;
					}
					target = target.substring(0, begin) + target.substring(end);
				}
			}
			return target;
		} catch (Exception ex) {
			return originalFunctionName;
		}
	}

	/**
	 * get granted authorities( here are function names ) of the current user
	 * and put them into the cache. if the current user is invalid, return empty
	 * list, or else return the granted function list of it.
	 * 
	 * setGrantedFunctionsInCache
	 * 
	 * @return List<String> function name list
	 */
	private List<GrantedAuthority> setGrantedFunctionsInCache(Authentication currentUser) {
		List<GrantedAuthority> grantedFunctionNameList = new ArrayList<GrantedAuthority>();
		// granted functions of current user from cache
		GrantedAuthority[] grantedFunctionArray = functionsByUserCache
				.getFunctionFromCache(currentUser.getName());
		Iterator grantedFunctionIte = null;
		if (grantedFunctionArray != null) {
			grantedFunctionIte = Arrays.asList(grantedFunctionArray).iterator();
			while (grantedFunctionIte.hasNext()) {
				// if the granted function list has been put into the
				// functionsByUserCache, return it.
				grantedFunctionNameList.add((GrantedAuthority) grantedFunctionIte.next());
			}
		} else {
			// if the the granted function list had not been put into the
			// functionsByUserCache, put it now.
			// Get all granted functions except for the repeated roles of the user
			Iterator grantedFunctionOfUserIte = null;
			Function tmpGrantedFunction = null;
				// get granted functions of this role from z_role_function table
				grantedFunctionOfUserIte = SecurityUtil.getFunctionsByUser((User)currentUser.getPrincipal()).iterator();
				// put every function name into the return list
				while (grantedFunctionOfUserIte.hasNext()) {
					tmpGrantedFunction = (Function) grantedFunctionOfUserIte
							.next();
					//don't add duplicated functions into list
					if (!isContainFunction(grantedFunctionNameList, tmpGrantedFunction
							.getName())) {
						grantedFunctionNameList.add(new GrantedAuthorityImpl(tmpGrantedFunction
								.getName()));
					}
				}
			// put granted function into cache
			functionsByUserCache.putFuncitonInCache(currentUser.getName(),
					grantedFunctionNameList.toArray(new GrantedAuthority[0]));
		}
		return grantedFunctionNameList;
	}

	/**
	 * isContainFunction 
	 *
	 * @param grantedFunctionsOfUser
	 * @param functionName
	 * @return  boolean
	 */
	private boolean isContainFunction(List<GrantedAuthority> grantedFunctionsOfUser, String functionName) {
		boolean containThisFunctionFlg = false;
		Iterator grantedFunctionsOfUserIte = grantedFunctionsOfUser.iterator();
		while (grantedFunctionsOfUserIte.hasNext()) {
			if (functionName.equals(((GrantedAuthority)grantedFunctionsOfUserIte.next()).toString())) {
				containThisFunctionFlg = true;
				break;
			}
		}
		return containThisFunctionFlg;

	}
	/**
	 * get the granted roles string delimited with comma for the same function.
	 * 
	 * lookupAttributes
	 * 
	 * @param urlPath
	 *            http or https url path request
	 * @return ConfigAttributeDefinition the roles string in it.
	 */
	public ConfigAttributeDefinition lookupAttributes(String urlPath) {
		Assert.notNull(urlPath,
				"lookupAttrubutes in the GbUrlDefinitionSource is null");
		// get function name by URL with this pattern: /pursuitSearch.action
		GrantedAuthority[] grantedRolesOfFunction = null;
		// get request function name from URL request. it is case
		// sensitive. 
		//(Sample: xxx-01.action, xxx-01!update.action, xxx.action, xxxx!update.action and so on)
		String originalFunctionName = urlPath.substring(urlPath
				.lastIndexOf(Constants.RESOURCE_PATH_PREFIX) + 1);
		// these urlPath resource need not control the permit: *.css, *.js, .etc
		if (!isNeedControlPermit(originalFunctionName)) {
			return null;
		}
		//Because the function name just only use 2 legal format: xxx.action and xxx!xxxx.action, 
		//convert the originalFunctionName so that it can meet the patterns: xxx.action and xxx!xx.action. 
		
		String reqFunctionName = convertFunctionName(originalFunctionName);
		//if the url format is "projectHOme!update.action" or "xxxxx-xx.action", m 
		//get current user session info
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();

		List<GrantedAuthority> grantedFunctionsOfUser = new ArrayList<GrantedAuthority>();
		Collection roles = null;
		if (null == currentUser 
				|| null == currentUser.getAuthorities()
				|| currentUser.getAuthorities().length == 0
				|| "anonymous".equals(currentUser.getName())) {
			//if the user is anonymous user, just return "ROLE_SUPER_ADMIN," so that it can not access this url.
			;
		}else {
			// if it is authorized user, set cache info
			grantedFunctionsOfUser = setGrantedFunctionsInCache(currentUser);
			// get function by function name from functionsByUserCache
			Function secureObject = null;
			if (!isContainFunction(grantedFunctionsOfUser, reqFunctionName)) {
				//if the function does not exist in the cache, 
				//check whether this function has been defined in z_function.
				secureObject = (Function) functionDao.getFunctionByName(reqFunctionName);
				//if it does not exist in z_function, don't control its access permit.
				//or else it means that the current user has no permit of it.
				if (secureObject == null) {
					// it didn't defined in z_function, return null, don't check its permit.
					return null;
				}
			} else {
				roles =  Arrays.asList(currentUser.getAuthorities());
				// if this function exists in the functionsByUserCache, put its roles into authorityFunctionCache.
				// if there are roles of this function in authorityFunctionCache, get
				// it, or else get it from database.
				grantedRolesOfFunction = authorityFunctionCache
						.getAuthorityFromCache(reqFunctionName);
				if (grantedRolesOfFunction == null || grantedRolesOfFunction.length == 0) {
						authorityFunctionCache.putAuthorityInCache(reqFunctionName, currentUser.getAuthorities());
				}
			}
		}
		
		//put the req function 
		ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
		StringBuffer rolesStr = new StringBuffer();
		GrantedAuthority role = null;
		if (roles != null && !roles.isEmpty()) {
			// assemble role string with delimitor of comma
			for (Iterator it = roles.iterator(); it.hasNext();) {
				role = (GrantedAuthority) it.next();
				rolesStr.append(role.getAuthority()).append(",");
			}
		} else {
			// if none has been granted for the function, set a string into the
			// result so that the vote can work.
			rolesStr.append(RoleConstants.ROLE_ADMIN).append(",");
		}

		configAttrEditor.setAsText(rolesStr.toString().substring(0,
				rolesStr.length() - 1));
		ConfigAttributeDefinition configAttrDef = (ConfigAttributeDefinition) configAttrEditor
				.getValue();
		return configAttrDef;

	}

	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	// getter and setter

	/**
	 * the Getter of authorityFunctionCache
	 * 
	 * @return Returns the authorityFunctionCache.
	 */
	public AuthorityBasedFunctionCache getAuthorityFunctionCache() {
		return authorityFunctionCache;
	}

	/**
	 * the Setter of authorityFunctionCache
	 * 
	 * @param authorityFunctionCache
	 *            The authorityFunctionCache to set.
	 */
	public void setAuthorityFunctionCache(
			AuthorityBasedFunctionCache authorityFunctionCache) {
		this.authorityFunctionCache = authorityFunctionCache;
	}

	/**
	 * the Setter of functionDao
	 * 
	 * @param functionDao
	 *            The functionDao to set.
	 */
	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	/**
	 * the Getter of functionsByUserCache
	 * 
	 * @return Returns the functionsByUserCache.
	 */
	public FunctionsByUserCache getFunctionsByUserCache() {
		return functionsByUserCache;
	}

	/**
	 * the Setter of functionsByUserCache
	 * 
	 * @param functionsByUserCache
	 *            The functionsByUserCache to set.
	 */
	public void setFunctionsByUserCache(
			FunctionsByUserCache functionsByUserCache) {
		this.functionsByUserCache = functionsByUserCache;
	}

}
