package bin.g11n.gt.dao.app;

import java.util.List;

import bin.g11n.gt.dao.common.GenericDao;
import bin.g11n.gt.model.Product;

/**
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface ProductDao extends GenericDao<Product, Long> {
	List findAllProduct();

	Product findProductById(Long id);
	Product findProductByNameAndVersion(String productName, String productVersion);
	Product findProductByName(String productName);
	Object[] searchProductList(Product searchCondition, int from, int size);
}
