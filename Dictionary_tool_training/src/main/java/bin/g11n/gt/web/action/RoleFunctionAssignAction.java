package bin.g11n.gt.web.action;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.LabelValue;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.common.FunctionManager;


/**
 * Functions assign to user action
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/04/16
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class RoleFunctionAssignAction extends BaseAction {

	/* serialVersionUID */
	private static final long serialVersionUID = 2234333L;

	private FunctionManager functionManager = null;
	private List<Long> unassignFunctionID = new ArrayList<Long>();
	private List<Long> assignedFunctionID = new ArrayList<Long>();

	private Role role;

	private List<LabelValue> roleDropdownList = new ArrayList<LabelValue>();
	//Function Name
	private List<LabelValue> unassignFunctionList = new ArrayList<LabelValue>();
	//Function Name
	private List<LabelValue> assignedFunctionList = new ArrayList<LabelValue>();
	Set<Function> existedGrantedFunctions = new HashSet<Function>();
	boolean showAssignAreaFlg;

	private void setGrantedDropdownList(){
		Iterator<Function> ite = existedGrantedFunctions.iterator();
		Function assignedFunction = null;
		while (ite.hasNext()){
			assignedFunction = ite.next();
			//Add the assigned product into assigned product dropdown list.
			assignedFunctionList.add(new LabelValue(
					assignedFunction.getDescription(),
					String.valueOf(assignedFunction.getId())));
		}
		//Since Function count is not too large, search all of them here.
		//Note: The records with all_access_flg= false.
		List<Function> allGrantableFunctionList = functionManager.getFunctionsWithAllAccessFalse();
		//Get unsigned Function dropdown list.
		for (Function function : allGrantableFunctionList){
			boolean assignedFlg = false;
			//Get unassigned Function list.
			//Initiate used variables again.
			ite = existedGrantedFunctions.iterator();
			assignedFunction = null;
			while (ite.hasNext()){
				assignedFunction = ite.next();
				//Exclude it from unassigned Function list if the id is the same.
				if (assignedFunction.getId().equals(function.getId())){
					assignedFlg = true;
					break;
				}
			}
			if (!assignedFlg){
				unassignFunctionList.add(new LabelValue( 
						function.getDescription(), 
						String.valueOf(function.getId())));
			}
		}
		
	}
	
	/**
	 * Check assign conditions 
	 *
	 * @return  String
	 */
	public String assignCheck() {
		if (role.getId() == null){
			String msg = getBundleInstance().getString("assign.function.error.role.required");
			addActionError(msg);
		}else {
			showAssignAreaFlg = true;
			//Reuse search function. Set max size unlimited.
			int maxListSize = Integer.MAX_VALUE;
			//load assigned Function from DB "Z_R_ROLE_FUNCTION".
			existedGrantedFunctions = functionManager.getGrantableFunctionsByRoleId(role.getId());
			//set assigned Function dropdown list.
			setGrantedDropdownList();
		}
		
		return SUCCESS;
	}

	/**
	 * assign 
	 *
	 * @return  String
	 */
	public String assign() throws GtException{
		if (assignedFunctionID != null){
			//UPdate assigned reviewer list in database.
			existedGrantedFunctions = roleManager.assignFunction(role.getId(), assignedFunctionID);
			//set assigned Function dropdown list.
			setGrantedDropdownList();
		}
		showAssignAreaFlg = true;
		return SUCCESS;
	}

	/**
	 * prepare 
	 *
	 * @throws GtException  void
	 */
	@SuppressWarnings("unchecked")
	public void prepare() throws GtException {
		//Set assign condition dropdown list.
		List<User> roleList = roleManager.getAllRoles();
		Role roleTmp = null;
		Iterator roleIter = roleList.iterator();
		while (roleIter.hasNext()) {
			roleTmp = (Role) roleIter.next();
			roleDropdownList.add(new LabelValue(roleTmp.getDescription(), String.valueOf(roleTmp.getId())));
		}
		
	}


	public List<Long> getUnassignFunctionID() {
		return unassignFunctionID;
	}

	public void setUnassignFunctionID(List<Long> unassignFunctionID) {
		this.unassignFunctionID = unassignFunctionID;
	}

	public List<Long> getAssignedFunctionID() {
		return assignedFunctionID;
	}

	public void setAssignedFunctionID(List<Long> assignedFunctionID) {
		this.assignedFunctionID = assignedFunctionID;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<LabelValue> getRoleDropdownList() {
		return roleDropdownList;
	}

	public void setRoleDropdownList(List<LabelValue> roleDropdownList) {
		this.roleDropdownList = roleDropdownList;
	}

	public List<LabelValue> getUnassignFunctionList() {
		return unassignFunctionList;
	}

	public void setUnassignFunctionList(List<LabelValue> unassignFunctionList) {
		this.unassignFunctionList = unassignFunctionList;
	}

	public List<LabelValue> getAssignedFunctionList() {
		return assignedFunctionList;
	}

	public void setAssignedFunctionList(List<LabelValue> assignedFunctionList) {
		this.assignedFunctionList = assignedFunctionList;
	}

	public Set<Function> getExistedGrantedFunctions() {
		return existedGrantedFunctions;
	}

	public void setExistedGrantedFunctions(Set<Function> existedGrantedFunctions) {
		this.existedGrantedFunctions = existedGrantedFunctions;
	}

	public boolean isShowAssignAreaFlg() {
		return showAssignAreaFlg;
	}

	public void setShowAssignAreaFlg(boolean showAssignAreaFlg) {
		this.showAssignAreaFlg = showAssignAreaFlg;
	}

	public void setFunctionManager(FunctionManager functionManager) {
		this.functionManager = functionManager;
	}


}
