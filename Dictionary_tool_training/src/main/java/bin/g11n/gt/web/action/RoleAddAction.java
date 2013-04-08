/**
 * 
 */
package bin.g11n.gt.web.action;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.service.common.RoleManager;
import bin.g11n.gt.util.StringUtil;


/**
 * @author rwang
 *
 */
public class RoleAddAction extends BaseAction {
	private Role role;
	
	private RoleManager roleManager;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
    public String execute() {        
        return "input";
    }
    
    public String cancel() {
        if (!"list".equals(from)) {
            return SUCCESS;
        }
        return "cancel";
    }
    
    public String save() {
    	if (role == null || StringUtil.isBlank(role.getName()))
			return Constants.RESULT_ERROR;
    	
		role.setDeleteFlg("0");
		
		if (roleManager.save(role) != null)
		{
			logger.log(ILogger.ELevel.INFO, "Save role " + role.getName() + " successfully");
			addActionMessage(getText("saveSuccessfully"));
			return "success";
		}
		else
			return Constants.RESULT_ERROR;
    }
}
