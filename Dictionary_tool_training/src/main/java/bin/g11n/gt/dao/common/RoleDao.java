package bin.g11n.gt.dao.common;

import java.util.List;
import java.util.Set;

import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface RoleDao extends GenericDao<Role, Long> {
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return role populated role object
     */
    public Role getRoleByName(String rolename);
    
    /**
     * Gets role list based on rolename list
     * @param rolenames the rolename list
     * @return roleList populated role object list
     */
    public List<Role> getRolesByNames(List<String> rolenames);
    
    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    public void removeRole(String rolename);
    
    
    //added by bguo
    public abstract List getRoles();

    public abstract Role getRoleById(final Long id);

	/**
	 * find roles by function.
	 * it is used to get role Set when the function is from different session.
	 * Note: these roles only include the system maintenance roles defined in the z_role_function
	 *  
	 * getRolesByFunction 
	 *
	 * @return  List
	 */
	public abstract Set<Role> getRolesByFunction(Function function);

	public abstract Role addRole(final Role role, final User createOp);

    public abstract Role updateRole(final Role role, final User updateOp);

    public abstract void deleteRole(final Role role, final User deleteOp);

    public abstract void removeRole(final Role role);

    Object[] searchRoleList(Role searchCondition, int from, int size);
    
    public Role findRoleById(Long id);
}
