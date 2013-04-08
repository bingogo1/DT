package bin.g11n.gt.dao.impl.app;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.type.Type;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.app.ProductDao;
import bin.g11n.gt.dao.impl.common.GenericDaoHibernate;
import bin.g11n.gt.model.Product;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * 
 * @author bguo
 * @version $Revision: 1.2 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ProductDaoHibernate extends GenericDaoHibernate<Product,Long> implements ProductDao {

	public ProductDaoHibernate() {
		super(Product.class);

	}

	/**
	 *
	 * @return  List
	 */
	public List findAllProduct() {
	
		return getHibernateTemplate().find("from Product product " +
				" where product.deleteFlg=? ", Constants.DELETE_FLG_NORMAL);
	}
	
	/**
	 * 
	 *
	 * @param id
	 * @return  Product
	 */
	public Product findProductById(Long id) {
		List productList=this.getHibernateTemplate().find("from Product product where product.deleteFlg=? " +
				"and product.id=? ", new Object[]{Constants.DELETE_FLG_NORMAL, id});
		if(productList.isEmpty()){
			return null;
		}
		else return (Product) productList.get(0);
		
	}
	
	/**
	 *
	 * @param productName
	 * @param productVersion
	 * @return  Product
	 */
	public Product findProductByNameAndVersion(String productName, String productVersion) {
		List productList=this.getHibernateTemplate().find("from Product product " +
				"where product.productName=? " +
				" and product.productVersion=? " +
				" and product.deleteFlg=? ", 
				new Object[]{productName, productVersion, Constants.DELETE_FLG_NORMAL});
		if(productList.isEmpty()){
			return null;
		}
		else return (Product) productList.get(0);
		
	}
	
	
	/**
	 *
	 * @param productName
	 * @return  Product
	 */
	public Product findProductByName(String productName) {
		String hql ="SELECT product FROM Product product " +
					"WHERE product.productName=? " +
					" AND product.deleteFlg=?";
		Query query = this.getSession().createQuery(hql).
				setString(0, productName).setString(1, Constants.DELETE_FLG_NORMAL);
		List productList = query.list();
		if(productList.isEmpty()){
			return null;
		}
		else return (Product) productList.get(0);
	}
	
	@Override
	public Product save(Product product)
	{
		String hql ="SELECT product FROM Product product " +
						"WHERE product.productName=? and product.productVersion=? and product.productCenterId=? and product.deleteFlg=?";
		Query query = this.getSession().createQuery(hql)
							.setString(0, product.getProductName())
							.setString(1, product.getProductVersion())
							.setParameter(2, product.getProductCenterId())
							.setParameter(3, Constants.DELETE_FLG_NORMAL);
		
		Object obj = query.uniqueResult();
		if (obj == null)
			return super.save(product);
		else
		{
			Product prod = (Product) obj;
			prod.setProductDescription(product.getProductDescription());
			prod.setUpdateDatetime(DateUtil.getNewDate());
			prod.setUpdateUserId(SecurityUtil.getCurrentUserInfo());
			
			this.getHibernateTemplate().update(prod);
			return prod;
		}
	}

	public Object[] searchProductList(Product searchCondition, int from,
			int size) {
		List<Object> paramsList = new ArrayList<Object>();
		List<Type> parmTypeList = new ArrayList<Type>();
		StringBuffer strSql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		strSql.append("From Product pro" 
		        + " LEFT JOIN FETCH pro.productCenterId AS p_center"
				+ " where pro.deleteFlg<> ? ");
		countSql.append("SELECT COUNT(pro.id) ");
		countSql.append("From Product pro" 
		        + " LEFT JOIN pro.productCenterId AS p_center"
				+ " where pro.deleteFlg<> ? ");
		paramsList.add(Constants.DELETE_FLG_DELETED);
		parmTypeList.add((Type) Hibernate.STRING);
		if (StringUtil.isNotBlank(searchCondition.getProductName())) {
			strSql.append(" and pro.productName like ? ");
			countSql.append(" and pro.productName like ? ");
			paramsList.add(searchCondition.getProductName() + "%");
			parmTypeList.add((Type) Hibernate.STRING);
		}
		if (StringUtil.isNotBlank(searchCondition.getProductCenterId().getCenterName())) {
			strSql.append(" and p_center.id = ? ");
			countSql.append(" and p_center.id = ? ");
			paramsList.add(Long.parseLong(searchCondition.getProductCenterId().getCenterName()));
			parmTypeList.add((Type) Hibernate.LONG);
		}
//		strSql.append("ORDER BY pro.productName, pro.productVersion ");
		return new Object[]{getSession().createQuery(countSql.toString()).setParameters(
				paramsList.toArray(), parmTypeList.toArray(new Type[] {})).uniqueResult(),
				getSession().createQuery(strSql.toString()).setParameters(
				paramsList.toArray(), parmTypeList.toArray(new Type[] {}))
				.setFirstResult(from).setMaxResults(size).list()};
	}
	
}