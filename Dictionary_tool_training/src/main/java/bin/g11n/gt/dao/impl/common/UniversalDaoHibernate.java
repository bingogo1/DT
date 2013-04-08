package bin.g11n.gt.dao.impl.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.dao.common.UniversalDao;


/**
 * This class serves as the a class that can CRUD any object witout any
 * Spring configuration. The only downside is it does require casting
 * from Object to the object class.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UniversalDaoHibernate extends HibernateDaoSupport implements UniversalDao {
	protected static transient final ILogger logger = G11nLogFactory.getLog(UniversalDaoHibernate.class);

    /**
     * @see UniversalDao#save(java.lang.Object)
     */
    public Object save(Object o) {
        return getHibernateTemplate().merge(o);
    }

    /**
     * @see UniversalDao#get(java.lang.Class, java.io.Serializable)
     */
    public Object get(Class clazz, Serializable id) {
        Object o = getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * @see UniversalDao#getAll(java.lang.Class)
     */
    public List getAll(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see UniversalDao#remove(java.lang.Class, java.io.Serializable)
     */
    public void remove(Class clazz, Serializable id) {
        getHibernateTemplate().delete(get(clazz, id));
    }
}
