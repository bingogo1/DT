package bin.g11n.gt.service.impl.app;

import java.util.List;

import bin.g11n.gt.dao.app.ProductCenterDao;
import bin.g11n.gt.model.ProductCenter;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.app.ProductCenterManager;
import bin.g11n.gt.service.impl.common.BaseManager;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * LanguageManagerImpl.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ProductCenterManagerImpl extends BaseManager implements ProductCenterManager {
	private ProductCenterDao productCenterDao;

	public List<ProductCenter> findAllPCenter() {
		return productCenterDao.findAllProductCenter();
	}
	
	public boolean update(ProductCenter productCenter)
	{
		ProductCenter pCenter = this.productCenterDao.findProductCenterById(productCenter.getId());
		
		if (pCenter == null || StringUtil.isEmpty(productCenter.getCenterName()))
			return false;
		
		ProductCenter existedPCenter = this.productCenterDao.findProductCenterByName(productCenter.getCenterName().trim());
		if (existedPCenter != null && !existedPCenter.getId().equals(pCenter.getId()))
			return false;
		
		pCenter.setCenterName(productCenter.getCenterName());
		pCenter.setCenterDescription(productCenter.getCenterDescription());
		pCenter.setUpdateDatetime(DateUtil.getNewDate());
		pCenter.setUpdateUserId(SecurityUtil.getCurrentUserInfo());
		
		return true;
	}

	public void setProductCenterDao(ProductCenterDao productCenterDao) {
		this.productCenterDao = productCenterDao;
	}

	public ProductCenter findPCenterById(Long id) {
		return this.productCenterDao.findProductCenterById(id);
	}

	public ProductCenter findPCenterByPCenterName(String languageName) {
		return this.productCenterDao.findProductCenterByName(languageName);
	}

	public Object[] searchPCenterList(ProductCenter searchCondition, int from,
			int size) {
		// TODO Auto-generated method stub
		return this.productCenterDao.searchPCenterList(searchCondition, from, size);
	}

	public ProductCenter save(ProductCenter productCenter) {
		return this.productCenterDao.save(productCenter);
	}

}
