package bin.g11n.gt.dao.common;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

/**
 * IBaseDao.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface IBaseDao {

	

	public int getCountByCriteria(final DetachedCriteria detachedCriteria);

	public List findAllByCriteria(final DetachedCriteria detachedCriteria);

	public Criteria getCriteriaByDetachedCriteria(final DetachedCriteria detachedCriteria);

	public Criteria getCriteria(Class clazz) throws DataAccessException;

	public Object getByPk(Class clazz, Integer id) throws DataAccessException;

	public Object getByPk(Class clazz, Long id) throws DataAccessException;

	public Object getByPk(Class clazz, String id) throws DataAccessException;

	public void create(Object entity) throws DataAccessException;

	public void update(Object entity) throws DataAccessException;

	public void delete(Object entity) throws DataAccessException;

	public void deleteAll(Class clazz) throws DataAccessException;

	public void deleteAll(Collection entities) throws DataAccessException;

	public Object loadByKey(Class clazz, String keyName, Object keyValue)
			throws DataAccessException;

    public List find(final String query);
}