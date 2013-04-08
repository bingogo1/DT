/**
 * 
 */
package bin.g11n.gt.web.action.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.LabelValue;
import bin.g11n.gt.model.ProductCenter;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.app.ProductCenterManager;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.ListUtil;
import bin.g11n.gt.util.StringUtil;
import bin.g11n.gt.web.action.BaseAction;


/**
 * @author rwang
 *
 */
public class ProductCenterMgmtAction extends BaseAction
{
	private ProductCenterManager productCenterManager;
	private List onePageSearchResultList;
	
	private ProductCenter productCenter;

	public ProductCenterManager getProductCenterManager() {
		return productCenterManager;
	}

	public void setProductCenterManager(ProductCenterManager productCenterManager) {
		this.productCenterManager = productCenterManager;
	}

	public List getOnePageSearchResultList() {
		return onePageSearchResultList;
	}

	public void setOnePageSearchResultList(List onePageSearchResultList) {
		this.onePageSearchResultList = onePageSearchResultList;
	}

	public ProductCenter getProductCenter() {
		return productCenter;
	}

	public void setProductCenter(ProductCenter productCenter) {
		this.productCenter = productCenter;
	}

	/**
	 * Search from database.
	 *
	 * @return
	 * @throws Exception  String
	 */
	public String search() {
		if (productCenter != null){
			int pageIndex = 1;
			String toBePageIndex = ServletActionContext.getRequest().getParameter("page");
			if (StringUtil.isNumeric(toBePageIndex)){
				//if click page change button, set current page number.
				pageIndex = Integer.parseInt(toBePageIndex);
			}
			currentPage = pageIndex;
			from = Integer.toString((currentPage - 1) * pageSize);
			Object[] searchResult = productCenterManager.searchPCenterList(productCenter, Integer.parseInt(from), pageSize);
			if (searchResult != null){
				totalSize = Integer.parseInt(((Long)searchResult[0]).toString());
				onePageSearchResultList = (List)searchResult[1];
			}else {
				onePageSearchResultList = new ArrayList<ProductCenter>();
			}
			paginatedList = ListUtil.makePagingList(onePageSearchResultList, pageSize, totalSize.intValue(), currentPage);
		}

		return Constants.RESULT_SEARCH;
	}
	
	public String edit()
	{
		return Constants.RESULT_ADD;
	}
	
	public String save()
	{
		if (productCenter == null || 
				StringUtil.isBlank(productCenter.getCenterName())) {
			addActionError(getText("errors.requiredField", new String[]{"Center Name"}));
			return Constants.RESULT_ERROR;
		}
		
		if (this.productCenterManager.findPCenterByPCenterName(productCenter.getCenterName().trim()) != null) {
			addActionError(getText("errors.field.existed", new String[]{"Center Name"}));
			return Constants.RESULT_ERROR;
		}
		
		User user = SecurityUtil.getCurrentUserInfo();
		Date date = DateUtil.getCurrentCalendar().getTime();
		
		productCenter.setCreateUserId(user);
		productCenter.setUpdateUserId(user);
		productCenter.setCreateDatetime(date);
		productCenter.setUpdateDatetime(date);
		productCenter.setDeleteFlg("0");
		
		if (productCenterManager.save(productCenter) != null)
		{
			logger.log(ILogger.ELevel.INFO, "Save productCenter " + productCenter.getCenterName() + " successfully");
			addActionMessage(getText("saveSuccessfully"));
			return search();
		}
		else
			return Constants.RESULT_ERROR;
	}
	
	public String cancel()
	{
		return Constants.RESULT_SEARCH;
	}
	
//	@Override
//	public void prepare() throws GtException
//	{
//    	if (language == null)
//    		language = new Language();
//    }
	
}
