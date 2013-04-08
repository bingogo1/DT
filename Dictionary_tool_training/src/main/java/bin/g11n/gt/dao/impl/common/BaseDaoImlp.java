package bin.g11n.gt.dao.impl.common;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.dao.DataAccessException;

import bin.g11n.cil.CILHelper;
import bin.g11n.cil.CILProducer;
import bin.g11n.cil.bundle.IG11nResourceBundle;
import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.AbstractDao;
import bin.g11n.gt.dao.common.IBaseDao;


/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BaseDaoImlp extends AbstractDao implements IBaseDao{
	protected transient final ILogger logger = G11nLogFactory.getLog(BaseDaoImlp.class);
    protected IG11nResourceBundle resources;


	/**
	 * 
	 * @param entity
	 * @return criteria 
	 * @throws DataAccessException
	 */
	public Criteria getCriteria(Class entity) throws DataAccessException {
		
		return super.getCriteria(entity);
	}

	/**
	 * 
	 * @param entity
	 * @param id as Integer
	 * @return Object
	 * @throws DataAccessException
	 */
	public Object getByPk(Class entity, Integer id) throws DataAccessException {
		
		Object obj = null;
		try {
			obj = super.getByPk(entity,id);
			
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "finding" + entity.getName() + " instance to database is failed", exception);

		}
		return obj;

	}

	/**
	 * 
	 * @param entity
	 * @param id as Long
	 * @return Object
	 * @throws DataAccessException
	 */
	public Object getByPk(Class entity, Long id) throws DataAccessException {
		
		Object obj = null;
		try {
			obj = super.getByPk(entity,id);
	
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "finding" + entity.getName() + " instance to database is failed", exception);
            throw exception;
		}
		return obj;
	}

	/**
	 * 
	 * @param entity
	 * @param id as String
	 * @return Object
	 * @throws DataAccessException
	 */
	public Object getByPk(Class entity, String id) throws DataAccessException {
		
		Object obj = null;
		try {
			obj = super.getByPk(entity,id);
			
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "finding" + entity.getName() + " instance to database is failed", exception);
			throw exception;
		}
		return obj;
	}


	/**
	 * create a date into DB
	 * @param entity
	 * @throws DataAccessException
	 */
	public void create(Object entity) throws DataAccessException {
		try {
			//getHibernateTemplate().save(entity);
			super.save(entity);
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "creating " + entity.getClass().getName() + " instance to database is failed",
					exception);
			throw exception;
		}

	}
	/**
	 * update a date from DB
	 * @param entity
	 * @throws DataAccessException
	 */
	public void update(Object entity) throws DataAccessException {
		
		try {
			
			super.update(entity);
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "updating " + entity.getClass().getName() + " instance to database is failed",
					exception);
			throw exception;
		}
	}
	/**
	 * delete a date from DB physically
	 * @param entity
	 * @throws DataAccessException
	 */
	public void delete(Object entity) throws DataAccessException {
		
		try {
			
			super.delete(entity);
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "deleting " + entity.getClass().getName() + " instance from database is failed",
					exception);
			throw exception;
		}

	}

	/**
	 * delete all dates from DB physically
	 * @param entity
	 * @throws DataAccessException
	 */
	public void deleteAll(Class entity) throws DataAccessException {
		
		try {
			List result = getHibernateTemplate().loadAll(entity);
			getHibernateTemplate().deleteAll(result);
		
		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "deleting " + entity.getClass().getName() + " all instances from database is failed",
					exception);
			throw exception;
		}
	}

	/**
	 * delete all Collection from DB physically
	 * @param Collection entities
	 * @throws DataAccessException
	 */
	public void deleteAll(Collection entities) throws DataAccessException {
		
		try {
			getHibernateTemplate().deleteAll(entities);

		} catch (DataAccessException exception) {
			logger.log(ILogger.ELevel.ERROR, "deleting instances from database is failed",
					exception);
			throw exception;
		}
	}
	
	protected IG11nResourceBundle getBundleInstance() {
		if (resources == null) {
			// Create and set up the window.
			CILHelper cILHelper = new CILHelper((new CILProducer())
					.createInfra());
			// Get interface
			resources = cILHelper.getResourceBundle();
			// Instantiates the bundle class with 1 parameter of baseName.
			resources = resources.getBundle(Constants.BUNDLE_KEY);
		}
		return resources;
	}



}
