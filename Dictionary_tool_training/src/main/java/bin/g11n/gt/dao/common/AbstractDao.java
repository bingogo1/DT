package bin.g11n.gt.dao.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bin.g11n.gt.common.Constants;

/**
 * AbstractDao.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public abstract class AbstractDao extends HibernateDaoSupport {

	private boolean cacheQueries = false;

	private String queryCacheRegion;

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	/**
	 * save the object of entity
	 * 
	 * @param entity
	 */
	public void save(final Object entity) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * update the object of entity
	 * 
	 * @param entity
	 */
	public void update(final Object entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * delete the object of entity
	 * 
	 * @param entity
	 */
	public void delete(final Object entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * load a Object from DB by id
	 * 
	 * @param entity
	 * @param id
	 * @return Object
	 */

	public Object loadByPriKey(final Class entity, final Serializable id) {
		return getHibernateTemplate().load(entity, id);
	}

	/**
	 * get a Object from DB by id
	 * 
	 * @param entity
	 * @param id
	 * @return Object
	 */
	public Object getByPk(final Class entity, final Serializable id) {
		return getHibernateTemplate().get(entity, id);
	}

	/**
	 * find all rows from the table
	 * 
	 * @param entity
	 * @return list
	 */
	public List findAll(final Class entity) {
		return getHibernateTemplate().find("from " + entity.getName() + " where deleteFlg=?", Constants.DELETE_FLG_NORMAL);
	}

	public List findByNamedQuery(final String namedQuery) {
		return getHibernateTemplate().findByNamedQuery(namedQuery);
	}

	public List findByNamedQuery(final String query, final Object parameter) {
		return getHibernateTemplate().findByNamedQuery(query, parameter);
	}

	public List findByNamedQuery(final String query, final Object[] parameters) {
		return getHibernateTemplate().findByNamedQuery(query, parameters);
	}

	public List find(final String query) {
		return getHibernateTemplate().find(query);
	}

	public List find(final String query, final Object parameter) {
		return getHibernateTemplate().find(query, parameter);
	}

	/**
	 * findAllByCriteria
	 * 
	 * @param detachedCriteria
	 * @return List
	 */
	public List findAllByCriteria(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		}, true);
	}

	/**
	 * getCountByCriteria
	 * 
	 * @param detachedCriteria
	 * @return int
	 */
	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria.setProjection(Projections.rowCount())
								.uniqueResult();
					}
				}, true);
		return count.intValue();
	}

	/**
	 * loadByKey
	 * 
	 * @param entity
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws DataAccessException
	 *             Object
	 */
	public Object loadByKey(Class entity, String keyName, Object keyValue)
			throws DataAccessException {

		List result = getHibernateTemplate().find(
				"from " + entity.getName() + " where " + keyName + " = ? and deleteFlg=? ",
				new Object[]{keyValue, Constants.DELETE_FLG_NORMAL});
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}

	}

	/**
	 * getCriteria
	 * 
	 * @param entity
	 * @return Criteria
	 */
	public Criteria getCriteria(final Class entity) {
		return (Criteria) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session.createCriteria(entity);
						return criteria;
					}
				}, true);

	}

	/**
	 * getCriteriaByDetachedCriteria
	 * 
	 * @param detachedCriteria
	 * @return Criteria
	 */
	public Criteria getCriteriaByDetachedCriteria(
			final DetachedCriteria detachedCriteria) {

		return (Criteria) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria;
					}
				}, true);

	}

}