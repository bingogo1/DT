package bin.g11n.gt.service.impl.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.exception.UserExistsException;
import bin.g11n.gt.dao.common.RoleDao;
import bin.g11n.gt.dao.common.UserDao;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.common.UserManager;
import bin.g11n.gt.util.DateUtil;



/**
 * Implementation of UserManager interface.</p>
 * 
 * UserManagerImpl.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UserManagerImpl extends BaseManager implements UserManager {
    private UserDao dao;
    private RoleDao roleDao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao the UserDao that communicates with the database
     */
    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }

    public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
     * @see UserManager#getUser(java.lang.String)
     */
    public User getUser(String userId) {
        return dao.get(new Long(userId));
    }

    /**
     * @see UserManager#getUsers(User)
     */
    public List getUsers(User user) {
        return dao.getUsers();
    }
    
	/* Find user list what includes only active, unlocked, undeleted users. 
	 * 
	 * @return List of user. 
	 */
	public List findValidDropdownUserList(){
		return dao.findValidDropdownUserList();
	}

    /**
     * @see UserManager#saveUser(User)
     */
    public User saveUser(User user) throws UserExistsException {
        // if new user, lowercase userId
        if (user.getVersion() == null) {
            user.setUsername(user.getUsername().toLowerCase());
        }
        
        try {
            return dao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * @see UserManager#removeUser(java.lang.String)
     */
    public void removeUser(String userId) {
    	logger.log(ILogger.ELevel.DEBUG, "removing user: " + userId);
        dao.remove(new Long(userId));
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User) dao.getUserByUserName(username);
    }
    
    //bguoStart added by bguo
    /**
     * get user list by user id array
     * 
     * getUsersByUserIds 
     *
     * @param userIdArray
     * @return  List<User>
     */
    public List<User> getUsersByUserIds(final Long[] userIdArray){
    	List<User> userList = new ArrayList<User>();
    	User user = null;
    	for (int i = 0; i < userIdArray.length; i ++){
    		user = dao.getUserById(userIdArray[i]);
    		if (user != null) {
    			userList.add(user);
    		}
    	}
    	return userList;
    }
    
	/**
	 * getUserById 
	 *
	 * @param id
	 * @return  User
	 */
	public User getUserById(final Long id) {
		
		return dao.findUserByUserId(id);
	}
	
	/**
	 * find all users what have not been deleted logically
	 * getAllUsers 
	 *
	 * @return  List
	 */
	public List getAllUsers() {
		return dao.getUsers();
	}
	
	/**
	 * get the data list from db by search condition.
	 * 
	 * @param searchCondition
	 * @param from
	 * @param size
	 * @return
	 */
	public Object[] searchUserList(User searchCondition, int from, int size){
		try {
			
			return dao.searchUserList(searchCondition, from, size);
		}catch (Exception ex){
			logger.log(ILogger.ELevel.WARNING, "Search condition paring error.");
			return null;
		}
	}
	
	/**
	 * addUser 
	 *
	 * @param user
	 * @param createOp
	 * @return  User
	 */
	public User addUser(final User user, final User createOp) {
		return dao.addUser(user, createOp);
	}
	
	/**
	 * updateUser 
	 *
	 * @param user
	 * @param updateOp
	 * @return  User
	 */
	public User updateUser(final User user, final User updateOp) {
		return dao.updateUser(user, updateOp);
	}
	
	/**
	 * logically delete the object
	 * deleteUser 
	 *
	 * @param user  void
	 */
	public void deleteUser(final User user, final User deleteOp) {
		dao.deleteUser(user, deleteOp);
	}
	
	/**
	 * remove the object form physical database
	 * removeUser 
	 *
	 * @param user  void
	 */
	public void removeUser(final User user) {
		dao.removeUser(user);
	}

	/**
	 * Assign Role by the specified user.
	 * 
	 * @param targetUserId
	 * @param assignedRoleID
	 * @return
	 */
	public Set<Role> assignRole(Long targetUserId, List<Long> assignedRoleID){
		User targetUser = dao.get(targetUserId);
		//Get granted roles of the user.
		Set<Role> existedRoleRecords = targetUser.getSysRoles();
		List<Long> toAddRoleList = assignedRoleID;
		Set<Role> finalAssignedRoles = new HashSet<Role>();
		//Remove some existed Roles since the assignedRoleID does not contain them.
		Iterator<Role> ite = existedRoleRecords.iterator();
		Role existedRole = null;
		while (ite.hasNext()){
			existedRole = ite.next();
			boolean hasExistedFlg = false;
			for (Long roleId : assignedRoleID){
				if (roleId.equals(existedRole.getId())){
					hasExistedFlg = true;
					//add into existed granted role list.
					finalAssignedRoles.add(existedRole);
					//Remove duplicated roleId.
					toAddRoleList.remove(roleId);
					break;
				}
			}
			//Remove this existed record from DB.
			if (!hasExistedFlg){
				//This existed granted role record needs to be removed.
				targetUser.getSysRoles().remove(existedRole);
			}
		}
		//After finishing the existed record handling, Add the rest new role records.
		Role newRole = null;
		for (Long roleId : assignedRoleID){
			newRole = roleDao.get(roleId);
			targetUser.getSysRoles().add(newRole);

			//add into existed reviewer list.
			finalAssignedRoles.add(newRole);
		}
			return finalAssignedRoles;

	}
}
