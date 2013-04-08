package bin.g11n.gt.service.impl.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.FunctionDao;
import bin.g11n.gt.dao.common.RoleDao;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.common.RoleManager;
import bin.g11n.gt.util.StringUtil;


/**
 * Implementation of RoleManager interface.</p>
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class RoleManagerImpl extends BaseManager implements RoleManager {
    private RoleDao roleDao;
    private FunctionDao functionDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public List<Role> getRoles(Role role) {
        return roleDao.getAll();
    }

    public Role getRole(String rolename) {
        return roleDao.getRoleByName(rolename);
    }

    public Role saveRole(Role role) {
        return roleDao.save(role);
    }

    public void removeRole(String rolename) {
    	roleDao.removeRole(rolename);
    }
    
    
    //added by bguo 
	/**
	 * getRoleById 
	 *
	 * @param id
	 * @return  Role
	 */
	public Role getRoleById(final Long id) {
		
		return roleDao.getRoleById(id);
	}
	
	/**
	 * find all roles what have not been deleted logically
	 * getAllRoles 
	 *
	 * @return  List
	 */
	public List getAllRoles() {
		return roleDao.getRoles();
	}
	
	/**
	 * addRole 
	 *
	 * @param role
	 * @param createOp
	 * @return  Role
	 */
	public Role addRole(final Role role, final User createOp) {
		return roleDao.addRole(role, createOp);
	}
	
	/**
	 * updateRole 
	 *
	 * @param role
	 * @param updateOp
	 * @return  Role
	 */
	public Role updateRole(final Role role, final User updateOp) {
		return roleDao.updateRole(role, updateOp);
	}
	
	public boolean update(Role role)
	{
		Role dbRole = this.roleDao.findRoleById(role.getId());
		
		if (dbRole == null || StringUtil.isBlank(role.getName()))
			return false;
		
		Role existedRole = this.roleDao.getRoleByName(role.getName().trim());
		if (existedRole != null && !existedRole.getId().equals(dbRole.getId()))
			return false;
		
		dbRole.setShortName(role.getShortName());
		dbRole.setName(role.getName());
		dbRole.setDescription(role.getDescription());
		
		updateRole(dbRole, SecurityUtil.getCurrentUserInfo());
		
		return true;
	}
	
	/**
	 * logically delete the object
	 * deleteRole 
	 *
	 * @param role  void
	 */
	public void deleteRole(final Role role, final User deleteOp) {
		roleDao.deleteRole(role, deleteOp);
	}
	

	/**
	 * remove the object form physical database
	 * removeRole 
	 *
	 * @param role  void
	 */
	public void removeRole(final Role role) {
		roleDao.removeRole(role);
	}
	
	/**
	 * Assign Function by the specified role.
	 * 
	 * @param targetRoleId
	 * @param assignedFunctionID
	 * @return
	 */
	public Set<Function> assignFunction(Long targetRoleId, List<Long> assignedFunctionID){
		Role targetRole = roleDao.get(targetRoleId);
		//Get granted roles of the user.
		Set<Function> existedFunctionRecords = targetRole.getFunctions();
		List<Long> toAddFunctionList = assignedFunctionID;
		Set<Function> finalAssignedFunctions = new HashSet<Function>();
		//Remove some existed Functions since the assignedFunctionID does not contain them.
		Iterator<Function> ite = existedFunctionRecords.iterator();
		List<Function> toRemoveFunctionList = new ArrayList<Function>();
		Function existedFunction = null;
		while (ite.hasNext()){
			existedFunction = ite.next();
			boolean hasExistedFlg = false;
			for (Long functionId : assignedFunctionID){
				if (functionId.equals(existedFunction.getId())){
					hasExistedFlg = true;
					//add into existed granted Function list.
					finalAssignedFunctions.add(existedFunction);
					//Remove duplicated FunctionId.
					toAddFunctionList.remove(functionId);
					break;
				}
			}
			//Remove this existed record from DB.
			if (!hasExistedFlg){
				//This existed granted Function record needs to be removed.
				toRemoveFunctionList.add(existedFunction);
			}
		}
		//This existed granted Function record needs to be removed.
		for (Function removeFunctionId : toRemoveFunctionList){
			targetRole.getFunctions().remove(removeFunctionId);
		}

		//After finishing the existed record handling, Add the rest new Function records.
		Function newFunction = null;
		for (Long functionId : assignedFunctionID){
			newFunction = functionDao.get(functionId);
			targetRole.getFunctions().add(newFunction);

			//add into existed reviewer list.
			finalAssignedFunctions.add(newFunction);
		}
			return finalAssignedFunctions;

	}

	public Object[] searchRoleList(Role searchCondition, int from, int size) {
		try {
			return roleDao.searchRoleList(searchCondition, from, size);
		}catch (Exception ex){
			logger.log(ILogger.ELevel.WARNING, "Search condition paring error.");
			return null;
		}
	}
    
}
