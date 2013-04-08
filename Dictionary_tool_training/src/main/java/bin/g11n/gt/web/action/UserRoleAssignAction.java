package bin.g11n.gt.web.action;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.LabelValue;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;


/**
 * Roles assign to user action
 *
 * @author bguo
 * @version $Revision: 1.2 $ $Date 2009/04/16
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UserRoleAssignAction extends BaseAction {

	/* serialVersionUID */
	private static final long serialVersionUID = 222342L;

	private List<Long> unassignRoleID = new ArrayList<Long>();
	private List<Long> assignedRoleID = new ArrayList<Long>();

	private User user;

	private List<LabelValue> userDropdownList = new ArrayList<LabelValue>();
	//Role Name
	private List<LabelValue> unassignRoleList = new ArrayList<LabelValue>();
	//Role Name
	private List<LabelValue> assignedRoleList = new ArrayList<LabelValue>();
	Set<Role> existedGrantedRoles = new HashSet<Role>();
	boolean showAssignAreaFlg;

	private void setGrantedDropdownList(){
		Iterator<Role> ite = existedGrantedRoles.iterator();
		Role assignedRole = null;
		while (ite.hasNext()){
			assignedRole = ite.next();
			//Add the assigned product into assigned product dropdown list.
			assignedRoleList.add(new LabelValue(
					assignedRole.getDescription(),
					String.valueOf(assignedRole.getId())));
		}
		//Since role count is not too large, search all of them here.
		List<Role> allRoleList = roleManager.getAllRoles();
		//Get unsigned role dropdown list.
		for (Role role : allRoleList){
			boolean assignedFlg = false;
			//Get unassigned Role list.
			//Initiate used variables again.
			ite = existedGrantedRoles.iterator();
			assignedRole = null;
			while (ite.hasNext()){
				assignedRole = ite.next();
				//Exclude it from unassigned Role list if the id is the same.
				if (assignedRole.getId().equals(role.getId())){
					assignedFlg = true;
					break;
				}
			}
			if (!assignedFlg){
				unassignRoleList.add(new LabelValue( 
						role.getDescription(), 
						String.valueOf(role.getId())));
			}
		}
		
	}
	
	/**
	 * Check assign conditions 
	 *
	 * @return  String
	 */
	public String assignCheck() {
		if (user.getId() == null){
			String msg = getBundleInstance().getString("assign.role.error.user.required");
			addActionError(msg);
		}else {
			showAssignAreaFlg = true;
			//Reuse search function. Set max size unlimited.
			int maxListSize = Integer.MAX_VALUE;
			//load assigned role from DB.
			existedGrantedRoles = SecurityUtil.getGrantedRolesByUser(user);
			//set assigned role dropdown list.
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
		if (assignedRoleID != null){
			//UPdate assigned reviewer list in database.
			existedGrantedRoles = userManager.assignRole(user.getId(), assignedRoleID);
			//set assigned role dropdown list.
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
		List<User> userList = userManager.findValidDropdownUserList();
		User userTmp = null;
		Iterator userIter = userList.iterator();
		while (userIter.hasNext()) {
			userTmp = (User) userIter.next();
			userDropdownList.add(new LabelValue(userTmp.getFullName(), String.valueOf(userTmp.getId())));
		}
		
	}


	public List<Long> getUnassignRoleID() {
		return unassignRoleID;
	}

	public void setUnassignRoleID(List<Long> unassignRoleID) {
		this.unassignRoleID = unassignRoleID;
	}

	public List<Long> getAssignedRoleID() {
		return assignedRoleID;
	}

	public void setAssignedRoleID(List<Long> assignedRoleID) {
		this.assignedRoleID = assignedRoleID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<LabelValue> getUnassignRoleList() {
		return unassignRoleList;
	}

	public void setUnassignRoleList(List<LabelValue> unassignRoleList) {
		this.unassignRoleList = unassignRoleList;
	}

	public List<LabelValue> getAssignedRoleList() {
		return assignedRoleList;
	}

	public void setAssignedRoleList(List<LabelValue> assignedRoleList) {
		this.assignedRoleList = assignedRoleList;
	}

	public Set<Role> getExistedGrantedRoles() {
		return existedGrantedRoles;
	}

	public void setExistedGrantedRoles(Set<Role> existedGrantedRoles) {
		this.existedGrantedRoles = existedGrantedRoles;
	}

	public List<LabelValue> getUserDropdownList() {
		return userDropdownList;
	}

	public void setUserDropdownList(List<LabelValue> userDropdownList) {
		this.userDropdownList = userDropdownList;
	}

	public boolean isShowAssignAreaFlg() {
		return showAssignAreaFlg;
	}

	public void setShowAssignAreaFlg(boolean showAssignAreaFlg) {
		this.showAssignAreaFlg = showAssignAreaFlg;
	}


}
