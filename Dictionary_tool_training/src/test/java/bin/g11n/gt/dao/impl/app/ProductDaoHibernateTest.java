package bin.g11n.gt.dao.impl.app;

import org.springframework.test.AbstractTransactionalSpringContextTests;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.app.ProductCenterDao;
import bin.g11n.gt.dao.app.ProductDao;
import bin.g11n.gt.model.Product;
import bin.g11n.gt.model.ProductCenter;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.DateUtil;


public class ProductDaoHibernateTest extends
		AbstractTransactionalSpringContextTests {
	private ProductDao productDao;
	private ProductCenterDao productCenterDao;
	
	protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        
        return new String[] {"classpath*:/action-servlet.xml",
        		            "classpath*:/applicationContext-service.xml",
                             "classpath*:/applicationContext-resources.xml",
                             "classpath*:/applicationContext-dao.xml",
                         	 "classpath*:/security.xml"
                         	};
    }
	
	public void onSetUp() {
		this.productDao = (ProductDao) applicationContext.getBean("productDao");
		this.productCenterDao = (ProductCenterDao) applicationContext.getBean("productCenterDao");
	}

	public void testFindAllProduct() {
		fail("Not yet implemented"); // TODO
	}

	public void testFindProductById() {
		fail("Not yet implemented"); // TODO
	}

	public void testFindProductByNameAndVersion() {
		fail("Not yet implemented"); // TODO
	}

	public void testFindProductByName() {
		fail("Not yet implemented"); // TODO
	}

	public void testSaveProduct() {
		ProductCenter p_center = this.productCenterDao.findProductCenterByName("BAC");
		
		Product product = new Product();
		
		product.setProductName("TT");
		product.setProductVersion("1.0");
		product.setProductCenterId(p_center);
		
		product.setCreateDatetime(DateUtil.getNewDate());
		product.setCreateUserId(SecurityUtil.getCurrentUserInfo());
		product.setProductDescription("");
		product.setUpdateDatetime(DateUtil.getNewDate());
		product.setUpdateUserId(SecurityUtil.getCurrentUserInfo());
		product.setDeleteFlg(Constants.DELETE_FLG_NORMAL);
		
		this.productDao.save(product);
		
		assertEquals(product.getProductName(), "TT");
		
		product.setProductName("BAC");
		product.setProductVersion("");
		product.setProductDescription("modify...");
		
		this.productDao.save(product);
		
		assertEquals(product.getProductName(), "BAC");
		assertEquals(product.getProductDescription(), "modify...");
	}

	public void testSearchProductList() {
		fail("Not yet implemented"); // TODO
	}

}
