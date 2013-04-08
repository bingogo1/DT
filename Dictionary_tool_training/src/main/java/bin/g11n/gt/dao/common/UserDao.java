package bin.g11n.gt.dao.common;

import java.util.List;
import java.util.Set;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;

/**
 * User Data Access Object (GenericDao) interface.
 * UserDao.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Gets users information based on login name.
     * @param username the user's username
     * @return userDetails populated userDetails object
     * @throws org.acegisecurity.userdetails.UsernameNotFoundException thrown when user not found in database
     */
	@Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    public List<User> getUsers();

	Object[] searchUserList(User searchCondition, int from, int size);
	
	/**
     * Saves a user's information.
     * @param user the object to be saved
     */
	public User saveUser(User user);
    
    //added by bguo
    public abstract User getUserByUserName(final String loginName);

    public abstract User getUserById(final Long id);

    public abstract User addUser(final User user, final User createOp);

    public abstract User updateUser(final User user, final User updateOp);

    public abstract void deleteUser(final User user, final User deleteOp);

    public abstract void removeUser(final User user);
    
//    public int assignRoleToUser(Long roleId, Long targetUserId);
//    public int unassignRoleFromUser(Long roleId, Long targetUserId);

	/**
	 * Get system maintenance roles of the user
	 * 
	 * getSysRolesByUser 
	 *	
	 * @author bguo
	 * @param id user id
	 * @return  Set<Role> user's system maintenance role set.
	 */
	public abstract Set<Role> getSysRolesByUser(final Long id);
	
	/* Find user list what includes only active, unlocked, undeleted users. 
	 * 
	 * @return List of user. 
	 */
	List findValidDropdownUserList();
	
    /**
     * findUserByUserId 
     * @author czhang
     * used in PJM010
     * @param userId
     * @return  User
     */
    User findUserByUserId(Long userId);
}
