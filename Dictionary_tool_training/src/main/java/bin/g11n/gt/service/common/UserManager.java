package bin.g11n.gt.service.common;

import java.util.List;
import java.util.Set;

import org.acegisecurity.userdetails.UsernameNotFoundException;

import bin.g11n.gt.common.exception.UserExistsException;
import bin.g11n.gt.dao.common.UserDao;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;



/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * UserManager.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface UserManager {

    public void setUserDao(UserDao userDao);

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    public User getUser(String userId);
    
    /**
     * Finds a user by their username.
     * @param username the user's username used to login
     * @return User a populated user object
     * @throws org.acegisecurity.userdetails.UsernameNotFoundException
     *         exception thrown when user not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     * @param user parameters to filter on
     * @return List
     */
    public List getUsers(User user);

	/* Find user list what includes only active, unlocked, undeleted users. 
	 * 
	 * @return List of user. 
	 */
	public List findValidDropdownUserList();
    
    /**
     * Saves a user's information
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     */
    public User saveUser(User user) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    public void removeUser(String userId);

    /**
     * get user list by user id array
     * 
     * getUsersByUserIds 
     *
     * @param userIdArray
     * @return  List<User>
     */
    public abstract List<User> getUsersByUserIds(final Long[] userIdArray);

    public abstract User getUserById(final Long id);

	/**
	 * get the data list from db by search condition.
	 * 
	 * @param searchCondition
	 * @param from
	 * @param size
	 * @return
	 */
	public Object[] searchUserList(User searchCondition, int from, int size);

	public abstract User addUser(final User user, final User createOp);

    public abstract User updateUser(final User user, final User updateOp);

    public abstract void deleteUser(final User user, final User deleteOp);

    public abstract void removeUser(final User user);
    
	/**
	 * Assign Role by the specified user.
	 * 
	 * @param targetUserId
	 * @param assignedRoleID
	 * @return
	 */
	public Set<Role> assignRole(Long targetUserId, List<Long> assignedRoleID);

}
