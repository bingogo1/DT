package bin.g11n.gt.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.ILogger.ELevel;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.RoleConstants;
import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.dao.common.FunctionDao;
import bin.g11n.gt.dao.common.RoleDao;
import bin.g11n.gt.dao.common.UserDao;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.service.common.UserManager;
import bin.g11n.gt.util.MessageUtil;
import bin.g11n.gt.util.StringUtil;
import bin.g11n.gt.web.taglib.auth.AppContext;


/**
 * SecurityUtil.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history (C) Copyright bingogo1@hotmail.com, LP - All Rights Reserved.
 */
public class SecurityUtil {
	private static UserManager userManager;

	private static UserDao userDao;

	private static RoleDao roleDao;

	private static FunctionDao functionDao;

	private static transient final ILogger logger = G11nLogFactory
			.getLog(SecurityUtil.class);

	/**
	 * get current user information from the session. If called by the junit
	 * class, return a user with id 1 temporarily
	 * 
	 * getCurrentUserInfo
	 * 
	 * @return User
	 */
	public static User getCurrentUserInfo() {
		try {
			boolean isValidUser = false;
			SecurityContext sc = SecurityContextHolder.getContext();
			if (sc != null
					&& SecurityContextHolder.getContext().getAuthentication() != null) {
				// call this method from action or DWR or action
				isValidUser = true;
			}
			if (isValidUser) {
				return (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
			} else {
				// for test case
				return SecurityUtil.getUserInfoByUserName("gb");
				// new GtException(messageText);
				// return null;
			}
		} catch (Exception e) {
			String messageText = MessageUtil.getInstance()
			.getText("errors.timeout");
			logger.log(ELevel.ERROR, messageText);
			new GtException(messageText);
			return null;
		}
	}

	/**
	 * get default role of system who has functions with allAccessFlg=1.
	 * 
	 * getDefaultRole
	 * 
	 * @return Role
	 */
	private static Role getDefaultRole() {
		Role defaultRole = new Role();
		defaultRole.setName(RoleConstants.ROLE_DEFAULT);
		defaultRole.setDeleteFlg(Constants.DELETE_FLG_NORMAL);
		defaultRole
				.setDescription("This role is default role who has all functions with allAccessFlg=1");

		// get functions with allAccessFlg=1.
		Set<Function> functions = new HashSet<Function>();

		if (functionDao == null) {
			functionDao = (FunctionDao) AppContext.getInstance()
					.getAppContext().getBean("functionDao");
		}
		List<Function> commonFunctions = functionDao
				.getFunctionsByFlag(Constants.ALL_ACCESS_FLG__BOOLEAN_ENABLED);
		for (int i = 0; i < commonFunctions.size(); i++) {
			functions.add(commonFunctions.get(i));
		}
		defaultRole.setFunctions(functions);

		return defaultRole;
	}

	/**
	 * Get all functions except for the repeated object of the specified role
	 * list
	 * 
	 * getFunctionsByRoleList
	 * 
	 * @param roleList
	 *            the role list
	 * @return List<Role> the function list of the role list
	 */
	private static List<Function> getFunctionsByRoleList(Set<Role> roleList) {
		List<Function> functionList = new ArrayList<Function>();
		if (roleList != null && roleList.size() > 0) {
			Iterator roleIte = roleList.iterator();
			Set<Function> functionListOfRole = null;
			Function toBeAddedFunction = null;
			boolean canAddFlg = true;
			while (roleIte.hasNext()) {
				functionListOfRole = ((Role) roleIte.next()).getFunctions();
				if (functionListOfRole != null) {
					// get functions of one role
					Iterator functionIte = functionListOfRole.iterator();
					while (functionIte.hasNext()) {
						// init the local loop variables
						toBeAddedFunction = (Function) functionIte.next();
						canAddFlg = true;
						for (int j = 0; j < functionList.size(); j++) {
							// don't add repeated object into list
							if (toBeAddedFunction.getId().equals(
									((Function) functionList.get(j)).getId())) {
								canAddFlg = false;
								break;
							}
						}
						if (canAddFlg) {
							functionList.add(toBeAddedFunction);
						}
					}
				}
			}
		}
		return functionList;
	}

	/**
	 * Get all granted functions except for the repeated roles of the user
	 * 
	 * getFunctionsByUser
	 * 
	 * @param user
	 *            the user who has this functions
	 * @return List<Function> the granted function list of the user
	 */
	public static List<Function> getFunctionsByUser(User user) {
		Set<Role> roleList = null;
		// get all role list of user. If user is current login user, get roles
		// from session.
		User currentUser = getCurrentUserInfo();
		if (currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
			// if the parameter is the current user, get roles from the
			// cache
			roleList = user.getRoles();
		} else {
			roleList = getRolesByUser(user);
		}
		// get all function list of the all role list.
		List<Function> grantedFunctionList = getFunctionsByRoleList(roleList);
		return grantedFunctionList;
	}

	/**
	 * Get all roles of the function
	 * 
	 * getRolesByFunction
	 * 
	 * @param function
	 *            the function what has been granted to this roles
	 * @return Set<Role> the role Set the function has been granted to
	 */
	public static Set<Role> getRolesByFunction(Function function) {

		return function.getRoles();
	}

	/**
	 * Get all roles except for the repeated roles of the user Note: this method
	 * just only can be called by user.java and securitUtil, don't call it in
	 * your business program!
	 * 
	 * getRolesByUser
	 * 
	 * @param user
	 *            the user who has this roles
	 * @return Set<Role> the role Set of the user
	 */
	public static Set<Role> getRolesByUser(User user) {
		// user info from session.
		if (userDao == null) {
			userDao = (UserDao) AppContext.getInstance().getAppContext()
					.getBean("userDao");
		}
		// Get system maintenance roles defined in the R_USER_ROLE directly
		// belong to the user.
		Set<Role> roleList = userDao.getSysRolesByUser(user.getId());
		if (roleList == null) {
			roleList = new HashSet<Role>();
		}
		roleList.add(getDefaultRole());
		return roleList;
	}

	/**
	 * Get all roles except for the repeated roles of the user Note: this method
	 * just only can be called by user.java and securitUtil, don't call it in
	 * your business program!
	 * 
	 * getRolesByUser
	 * 
	 * @param user
	 *            the user who has this roles
	 * @return Set<Role> the role Set of the user
	 */
	public static Set<Role> getGrantedRolesByUser(User user) {
		// user info from session.
		if (userDao == null) {
			userDao = (UserDao) AppContext.getInstance().getAppContext()
					.getBean("userDao");
		}
		// Get system maintenance roles defined in the R_USER_ROLE directly
		// belong to the user.
		return userDao.getSysRolesByUser(user.getId());
	}

	/**
	 * Get user information from z_user with the specified user login name.
	 * 
	 * getUserInfoByUserName
	 * 
	 * @param userName
	 *            user login name
	 * @return User user object
	 */
	public static User getUserInfoByUserName(String userName) {
		if (userDao == null) {
			userDao = (UserDao) AppContext.getInstance().getAppContext()
					.getBean("userDao");
		}
		return (User) userDao.loadUserByUsername(userName);
	}

	/**
	 * Get all users of the role
	 * 
	 * getUsersByRole
	 * 
	 * @param role
	 *            the role object
	 * @return Set<User> user list who have the specified role.
	 */
	public static Set<User> getUsersByRole(Role role) {
		// Get system maintenance users defined in the R_USER_ROLE directly
		// belong to the role.
		Set<User> userList = role.getSysUsers();
		if (userList == null) {
			userList = new HashSet<User>();
		}
		return userList;
	}

	/**
	 * get user list by query DB with the specified user id numeric string
	 * splitted with comma like this format: ",12,2,22," "2". Wrong format: "d"
	 * "d,23".
	 * 
	 * getUsersByUserIdStr
	 * 
	 * @param usersStrSplitComma
	 *            user id string split with comma
	 * @return List<User>
	 */
	public static List<User> getUsersByUserIdStr(String usersStrSplitComma) {
		List<User> userList = new ArrayList<User>();
		String[] userIdStrArray = usersStrSplitComma.split(Constants.COMMA);
		// convert string array into Long array
		Long[] userLongArray = new Long[] {};
		if (userIdStrArray != null && userIdStrArray.length > 0) {
			for (int i = 0, j = 0; i < userIdStrArray.length; i++) {
				if (StringUtil.isNumeric(userIdStrArray[i])) {
					userLongArray[j] = new Long(userIdStrArray[i]);
					j++;
				}
			}
		}
		// if this method is called from the Junit test class, don't get user
		// info from session.
		if (userManager == null) {
			userManager = (UserManager) AppContext.getInstance()
					.getAppContext().getBean("userManager");
		}
		userList = userManager.getUsersByUserIds(userLongArray);
		return userList;
	}

	/**
	 * Check whether the specified user has a specified role.
	 * 
	 * isSpecifiedRole
	 * 
	 * @param userName
	 *            user Name
	 * @param roleName
	 *            role name string
	 * @return boolean
	 */
	public static boolean isSpecifiedRole(String userName, String roleName) {
		boolean flg = false;
		Iterator<Role> roleList = null;
		// Get current user info from system cache.
		User currentUser = getCurrentUserInfo();
		if (currentUser.getUsername().equalsIgnoreCase(userName)) {
			// if the parameter is the current user, get roles from the
			// cache
			roleList = currentUser.getRoles().iterator();
		} else {
			// get role list from the DB.
			if (userDao == null) {
				userDao = (UserDao) AppContext.getInstance().getAppContext()
						.getBean("userDao");
			}
			User parmUser = userDao.getUserByUserName(userName);

			roleList = getRolesByUser(parmUser).iterator();
		}
		// Check the user's role list. return true if he is specified role.
		String tmpRoleName = null;
		while (roleList.hasNext()) {
			tmpRoleName = roleList.next().getName();
			if (tmpRoleName.equals(roleName)) {
				flg = true;
				break;
			}
		}
		return flg;
	}

	/**
	 * Constructor return_type
	 */
	public SecurityUtil() {
		super();
	}

	/**
	 * check whether the specified role is belong to the user.
	 * 
	 * hasRoleByUser
	 * 
	 * @param user
	 *            the user object
	 * @param roleName
	 *            role name
	 * @return boolean true:has this role; false:has not this role.
	 */
	public boolean hasRoleByUser(User user, String roleName) {
		Set<Role> roleList = null;
		// get all role list of user. If user is current login user, get roles
		// from session.
		User currentUser = getCurrentUserInfo();
		if (currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
			// if the parameter is the current user, get roles from the
			// cache
			roleList = user.getRoles();
		} else {
			roleList = getRolesByUser(user);
		}
		if (roleList != null) {
			Iterator roleIte = roleList.iterator();
			Role role = null;
			while (roleIte.hasNext()) {
				role = (Role) roleIte.next();
				if (role.getName().equals(roleName)) {
					return true;
				}
			}

		}
		return false;
	}

	public void setFunctionDao(FunctionDao functionDao) {
		SecurityUtil.functionDao = functionDao;
	}

	/**
	 * the Setter of roleDao
	 * 
	 * @param roleDao
	 *            The roleDao to set.
	 */
	public void setRoleDao(RoleDao roleDao) {
		SecurityUtil.roleDao = roleDao;
	}

	/**
	 * the Setter of userDao
	 * 
	 * @param userDao
	 *            The userDao to set.
	 */
	public void setUserDao(UserDao userDao) {
		SecurityUtil.userDao = userDao;
	}

	/**
	 * the Setter of userManager
	 * 
	 * @param userManager
	 *            The userManager to set.
	 */
	public void setUserManager(UserManager userManager) {
		SecurityUtil.userManager = userManager;
	}

}