package bin.g11n.gt.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.User;
import bin.g11n.gt.util.ListUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * @author bguo
 * @version $Revision: 1.2 $ $Date 2009/04/14
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UserSearchAction extends BaseAction {
    private static final long serialVersionUID = 52115191L;

	private User searchCondition;
	private List onePageSearchResultList;


	
	public User getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(User searchCondition) {
		this.searchCondition = searchCondition;
	}

	public List getOnePageSearchResultList() {
		return onePageSearchResultList;
	}

	public void setOnePageSearchResultList(List onePageSearchResultList) {
		this.onePageSearchResultList = onePageSearchResultList;
	}

	/**
	 * prepare 
	 *
	 * @throws GtException  void
	 */
	@SuppressWarnings("unchecked")
	public void prepare()throws GtException {
		super.prepare();
	}

	/**
	 * Search from database.
	 *
	 * @return
	 * @throws Exception  String
	 */
	public String search() throws GtException {
		if (searchCondition != null){
			int pageIndex = 1;
			String toBePageIndex = ServletActionContext.getRequest().getParameter("page");
			if (StringUtil.isNumeric(toBePageIndex)){
				//if click page change button, set current page number.
				pageIndex = Integer.parseInt(toBePageIndex);
			}
			currentPage = pageIndex;
			from = Integer.toString((currentPage - 1) * pageSize);
			Object[] searchResult = userManager.searchUserList(searchCondition, Integer.parseInt(from), pageSize);
			if (searchResult != null){
				totalSize = Integer.parseInt(((Long)searchResult[0]).toString());
				onePageSearchResultList = (List)searchResult[1];
			}else {
				onePageSearchResultList = new ArrayList<User>();
			}
			paginatedList = ListUtil.makePagingList(onePageSearchResultList, pageSize, totalSize.intValue(), currentPage);
		}
		return Constants.RESULT_SEARCH;
	}

}
