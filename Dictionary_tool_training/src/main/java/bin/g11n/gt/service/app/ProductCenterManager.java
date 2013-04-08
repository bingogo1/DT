package  bin.g11n.gt.service.app;

import java.util.List;

import bin.g11n.gt.model.ProductCenter;
import bin.g11n.gt.service.common.Manager;


/**
 * 
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface ProductCenterManager extends Manager {
	List<ProductCenter> findAllPCenter();
	public boolean update(ProductCenter productCenter);
	public ProductCenter findPCenterById(Long id);
	ProductCenter findPCenterByPCenterName(String pCenterName);
	public ProductCenter save(ProductCenter productCenter);
	Object[] searchPCenterList(ProductCenter searchCondition, int from, int size);
}
