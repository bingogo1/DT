/**
 * 
 */
package bin.g11n.gt.service.app;

import java.util.List;

import bin.g11n.gt.model.Product;
import bin.g11n.gt.service.common.Manager;


/**
 * @author rwang
 *
 */
public interface ProductManager extends Manager {
	List<Product> findAllProduct();
	public Object[] searchProductList(Product searchCondition, int from, int size);
	public Product save(Product product);
	public boolean update(Product product);
	public Product findProductByNameAndVersion(String productName, String productVersion);
}
