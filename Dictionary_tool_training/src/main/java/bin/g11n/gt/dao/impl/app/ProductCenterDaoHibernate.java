package bin.g11n.gt.dao.impl.app;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.type.Type;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.app.ProductCenterDao;
import bin.g11n.gt.dao.impl.common.GenericDaoHibernate;
import bin.g11n.gt.model.ProductCenter;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ProductCenterDaoHibernate extends GenericDaoHibernate<ProductCenter,Long> implements ProductCenterDao {

	public ProductCenterDaoHibernate() {
		super(ProductCenter.class);

	}

	/**
	 *
	 * @return  List
	 */
	public List findAllProductCenter() {
	
		return getHibernateTemplate().find("from ProductCenter productCenter " +
				" where productCenter.deleteFlg=? ", Constants.DELETE_FLG_NORMAL);
	}
	
	/**
	 * 
	 *
	 * @param id
	 * @return  ProductCenter
	 */
	public ProductCenter findProductCenterById(Long id) {
		List productCenterList=this.getHibernateTemplate().find("from ProductCenter productCenter where productCenter.deleteFlg=? " +
				"and productCenter.id=? ", new Object[]{Constants.DELETE_FLG_NORMAL, id});
		if(productCenterList.isEmpty()){
			return null;
		}
		else return (ProductCenter) productCenterList.get(0);
		
	}
	
	/**
	 *
	 * @param productCenterName
	 * @return  ProductCenter
	 */
	public ProductCenter findProductCenterByName(String productCenterName) {
		String hql ="SELECT productCenter FROM ProductCenter productCenter " +
					"WHERE productCenter.centerName=? " +
					" AND productCenter.deleteFlg=? ";
		Query query = this.getSession().createQuery(hql).
				setString(0, productCenterName).setString(1, Constants.DELETE_FLG_NORMAL);
		List productCenterList = query.list();
		if(productCenterList.isEmpty()){
			return null;
		}
		else return (ProductCenter) productCenterList.get(0);
	}
	
	@Override
	public ProductCenter save(ProductCenter productCenter)
	{
		String hql ="SELECT productCenter FROM ProductCenter productCenter WHERE productCenter.deleteFlg=? " +
				"and productCenter.centerName=?";
		Query query = this.getSession().createQuery(hql)
						.setParameters(new Object[]{Constants.DELETE_FLG_NORMAL, productCenter.getCenterName()}, 
								new Type[]{Hibernate.STRING, Hibernate.STRING});
		
		Object tmpPCenter = query.uniqueResult();
		
		if (tmpPCenter == null)
			return super.save(productCenter);
		else
		{
			ProductCenter prodCenter = (ProductCenter) tmpPCenter;
			prodCenter.setCenterDescription(productCenter.getCenterDescription());
			prodCenter.setUpdateDatetime(DateUtil.getNewDate());
			prodCenter.setUpdateUserId(SecurityUtil.getCurrentUserInfo());
			
			this.getHibernateTemplate().update(prodCenter);
			return prodCenter;
		}
	}

	public Object[] searchPCenterList(ProductCenter searchCondition, int from,
			int size) {
		List<Object> paramsList = new ArrayList<Object>();
		List<Type> parmTypeList = new ArrayList<Type>();
		StringBuffer strSql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		
		strSql.append("From ProductCenter pCenter where pCenter.deleteFlg <> ? ");
		countSql.append("SELECT COUNT(pCenter.id) ");
		countSql.append("From ProductCenter pCenter where pCenter.deleteFlg <> ? ");
		
		paramsList.add(Constants.DELETE_FLG_DELETED);
		parmTypeList.add((Type) Hibernate.STRING);
		
		if (StringUtil.isNotBlank(searchCondition.getCenterName())) {
			strSql.append(" and pCenter.centerName like ? ");
			countSql.append(" and pCenter.centerName like ? ");
			paramsList.add(searchCondition.getCenterName() + "%");
			parmTypeList.add((Type) Hibernate.STRING);
		}
		
//		strSql.append("ORDER BY pCenter.id ");
		return new Object[]{getSession().createQuery(countSql.toString()).setParameters(
				paramsList.toArray(), parmTypeList.toArray(new Type[] {})).uniqueResult(),
				getSession().createQuery(strSql.toString()).setParameters(
				paramsList.toArray(), parmTypeList.toArray(new Type[] {}))
				.setFirstResult(from).setMaxResults(size).list()};
	}
	
}