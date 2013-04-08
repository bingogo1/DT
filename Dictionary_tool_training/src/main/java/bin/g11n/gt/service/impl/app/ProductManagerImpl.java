/**
 * 
 */
package bin.g11n.gt.service.impl.app;

import java.util.List;

import bin.g11n.gt.dao.app.ProductDao;
import bin.g11n.gt.model.Product;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.app.ProductManager;
import bin.g11n.gt.service.impl.common.BaseManager;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * @author bguo
 *
 */
public class ProductManagerImpl extends BaseManager implements ProductManager {
	
	private ProductDao productDao;

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public List<Product> findAllProduct() {

		return productDao.findAllProduct();
	}

	/* (non-Javadoc)
	 * @see bin.g11n.gt.service.app.ProductManager#searchProductList(bin.g11n.gt.model.Product, int, int)
	 */
	public Object[] searchProductList(Product searchCondition, int from,
			int size) {
		return this.productDao.searchProductList(searchCondition, from, size);
	}

	public Product save(Product product) {
		return this.productDao.save(product);
	}
	
	public boolean update(Product product)
	{
		Product prod = this.productDao.findProductById(product.getId());
		
		if (prod == null || StringUtil.isEmpty(product.getProductName()))
			return false;
		
		Product existedProd = this.productDao.findProductByNameAndVersion(product.getProductName().trim(), product.getProductVersion().trim());
		if (existedProd != null && !existedProd.getId().equals(prod.getId()))
			return false;
		
		prod.setProductDescription(product.getProductDescription());
		prod.setProductName(product.getProductName());
		prod.setProductVersion(product.getProductVersion());
		prod.setUpdateDatetime(DateUtil.getNewDate());
		prod.setUpdateUserId(SecurityUtil.getCurrentUserInfo());
		
		return true;
	}

	public Product findProductByNameAndVersion(String productName, String productVersion)
	{
		Product product = this.productDao.findProductByNameAndVersion(productName, productVersion);
		
		return product;
	}
}
