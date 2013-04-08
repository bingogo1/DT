package bin.g11n.gt.dao.app;

import java.util.List;

import bin.g11n.gt.dao.common.GenericDao;
import bin.g11n.gt.model.ProductCenter;

/**
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface ProductCenterDao extends GenericDao<ProductCenter, Long> {
	List findAllProductCenter();
	public ProductCenter save(ProductCenter productCenter);
	ProductCenter findProductCenterById(Long id);
	ProductCenter findProductCenterByName(String productCenterName);
	Object[] searchPCenterList(ProductCenter searchCondition, int from, int size);
}
