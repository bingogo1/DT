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
import bin.g11n.gt.model.Product;
import bin.g11n.gt.model.ProductCenter;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.app.ProductCenterManager;
import bin.g11n.gt.service.app.ProductManager;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.ListUtil;
import bin.g11n.gt.util.StringUtil;
import bin.g11n.gt.web.action.BaseAction;


/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ProductMgmtAction extends BaseAction {

	private Product searchCondition;
	
	private List onePageSearchResultList;
	private List<LabelValue> productCenterDropdownList = new ArrayList<LabelValue>();

	private ProductManager productManager;
	private ProductCenterManager productCenterManager;



	public Product getSearchCondition() {
		return searchCondition;
	}



	public void setSearchCondition(Product searchCondition) {
		this.searchCondition = searchCondition;
	}



	public List<LabelValue> getProductCenterDropdownList() {
		return productCenterDropdownList;
	}



	public void setProductCenterDropdownList(
			List<LabelValue> productCenterDropdownList) {
		this.productCenterDropdownList = productCenterDropdownList;
	}



	public ProductManager getProductManager() {
		return productManager;
	}



	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}



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



	/**
	 * Initiate search page. Prepares dropdown lists.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String init() {
		//TODO set search condition dropdown list.
		List<ProductCenter> list = productCenterManager.findAllPCenter();
		ProductCenter productCenter = null;
		Iterator<ProductCenter> iter = list.iterator();
		while (iter.hasNext()) {
			productCenter = (ProductCenter) iter.next();
			productCenterDropdownList.add(
					new LabelValue(String.valueOf(productCenter.getId()), productCenter.getCenterName()));
		}
		
        return Constants.RESULT_SEARCH;
    }

	/**
     * This method is called to allow the action to prepare itself.
     *
	 * @throws Exception thrown if a system level exception occurs.
     */
	public void prepare()throws GtException {
		HttpServletRequest request = ServletActionContext.getRequest(); 
		HttpSession session = request.getSession();
		
		searchCondition = (Product) session.getAttribute("searchCondition");
		init();
		super.prepare();
	}
	
//	public String input(){
//		init();
//		return Constants.RESULT_OK;
//	}
	
	public String edit()
	{
		return Constants.RESULT_ADD;
	}
	
	public String cancel()
	{
		return Constants.RESULT_SEARCH;
	}
	
	public String save()
	{
		if (searchCondition == null || StringUtil.isBlank(searchCondition.getProductCenterId().getCenterName()) 
				|| StringUtil.isBlank(searchCondition.getProductName()) || StringUtil.isBlank(searchCondition.getProductVersion())) {
			return Constants.RESULT_ERROR;
		}
		
		if (this.productManager.findProductByNameAndVersion(
				searchCondition.getProductName().trim(), searchCondition.getProductVersion().trim()) != null) {
			addActionError(getText("errors.field.existed", new String[]{"Product and Version"}));
			return Constants.RESULT_ERROR;
		}
		
		User user = SecurityUtil.getCurrentUserInfo();
		ProductCenter p_center = this.productCenterManager.findPCenterById(
									Long.valueOf(searchCondition.getProductCenterId().getCenterName()));
		
		searchCondition.setCreateUserId(user);
		searchCondition.setUpdateUserId(user);
		Date date = DateUtil.getCurrentCalendar().getTime();
		searchCondition.setCreateDatetime(date);
		searchCondition.setUpdateDatetime(date);
		searchCondition.setProductCenterId(p_center);
    	
    	searchCondition.setDeleteFlg("0");
		
		if (productManager.save(searchCondition) != null)
		{
			logger.log(ILogger.ELevel.INFO, "Save product " + searchCondition.getProductName() + " successfully");
			addActionMessage(getText("saveSuccessfully"));
			return Constants.RESULT_SEARCH;
		}
		else
			return Constants.RESULT_ERROR;
	}

	/**
	 * Search from database.
	 *
	 * @return
	 * @throws Exception  String
	 */
	public String search() {
		if (searchCondition != null){
			int pageIndex = 1;
			String toBePageIndex = ServletActionContext.getRequest().getParameter("page");
			if (StringUtil.isNumeric(toBePageIndex)){
				//if click page change button, set current page number.
				pageIndex = Integer.parseInt(toBePageIndex);
			}
			currentPage = pageIndex;
			from = Integer.toString((currentPage - 1) * pageSize);
			Object[] searchResult = productManager.searchProductList(searchCondition, Integer.parseInt(from), pageSize);
			if (searchResult != null){
				totalSize = Integer.parseInt(((Long)searchResult[0]).toString());
				onePageSearchResultList = (List)searchResult[1];
			}else {
				onePageSearchResultList = new ArrayList<Product>();
			}
			paginatedList = ListUtil.makePagingList(onePageSearchResultList, pageSize, totalSize.intValue(), currentPage);
		}
//		//get display excelTable info
//		Object []obj = getHeadWidthTypeAlignStr();
//		headStr = (String) obj[0];
//		colsWidth = (String) obj[1];
//		editTypes = (String) obj[2];
//		colAlign = (String) obj[3];		
//		rendTypes = (String) obj[4];
//		// When tag variable is true , jsp page will display table 
//		this.setExcelTableFlag(true);
//		this.initDropDownList();
		return Constants.RESULT_SEARCH;
	}
	
	
}
