/**
 * 
 */
package bin.g11n.gt.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.service.common.RoleManager;
import bin.g11n.gt.util.ListUtil;
import bin.g11n.gt.util.StringUtil;
import bin.g11n.gt.web.action.BaseAction;


/**
 * @author rwang
 *
 */
public class RoleMgmtAction extends BaseAction {
	private RoleManager roleManager;
	private List onePageSearchResultList;
	
	private Role role;

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public List getOnePageSearchResultList() {
		return onePageSearchResultList;
	}

	public void setOnePageSearchResultList(List onePageSearchResultList) {
		this.onePageSearchResultList = onePageSearchResultList;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String exceute()
	{
		return Constants.RESULT_SEARCH;
	}

	/**
	 * Search from database.
	 *
	 * @return
	 * @throws Exception  String
	 */
	public String search() {
		if (role != null){
			int pageIndex = 1;
			String toBePageIndex = ServletActionContext.getRequest().getParameter("page");
			if (StringUtil.isNumeric(toBePageIndex)){
				//if click page change button, set current page number.
				pageIndex = Integer.parseInt(toBePageIndex);
			}
			currentPage = pageIndex;
			from = Integer.toString((currentPage - 1) * pageSize);
			Object[] searchResult = roleManager.searchRoleList(role, Integer.parseInt(from), pageSize);
			if (searchResult != null){
				totalSize = Integer.parseInt(((Long)searchResult[0]).toString());
				onePageSearchResultList = (List)searchResult[1];
			}else {
				onePageSearchResultList = new ArrayList<Role>();
			}
			paginatedList = ListUtil.makePagingList(onePageSearchResultList, pageSize, totalSize.intValue(), currentPage);
		}

		return Constants.RESULT_SEARCH;
	}
	
	public String edit()
	{
		return INPUT;
	}
	
	public String cancel()
	{
		return Constants.RESULT_SEARCH;
	}
}
