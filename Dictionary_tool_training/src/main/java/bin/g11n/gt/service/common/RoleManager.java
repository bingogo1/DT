package bin.g11n.gt.service.common;

import java.util.List;
import java.util.Set;

import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * RoleManager.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface RoleManager extends Manager {
    public List getRoles(Role role);
    public Role getRole(String rolename);
    public Role saveRole(Role role);
    public void removeRole(String rolename);
    
    public List getAllRoles();

    public abstract Role getRoleById(final Long id);

    public abstract Role addRole(final Role role, final User createOp);

    public abstract Role updateRole(final Role role, final User updateOp);

    public abstract void deleteRole(final Role role, final User deleteOp);

    public abstract void removeRole(final Role role);

	/**
	 * Assign Function by the specified role.
	 * 
	 * @param targetRoleId
	 * @param assignedFunctionID
	 * @return
	 */
	public Set<Function> assignFunction(Long targetRoleId, List<Long> assignedFunctionID);

	/**
	 * get the data list from db by search condition.
	 * 
	 * @param searchCondition
	 * @param from
	 * @param size
	 * @return
	 */
    public Object[] searchRoleList(Role searchCondition, int from, int size);
    
    /**
     * Service method for DWR call
     * 
     * @param role
     * @return
     */
    public boolean update(Role role);
}
