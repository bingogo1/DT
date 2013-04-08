package bin.g11n.gt.service.impl.common;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bin.g11n.gt.dao.common.GenericDao;
import bin.g11n.gt.service.common.GenericManager;


/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    protected final Log log = LogFactory.getLog(getClass());
    private GenericDao<T, PK> genericDao;

    public GenericManagerImpl(GenericDao<T, PK> genericDao) {
        this.genericDao = genericDao;
    }

    public List<T> getAll() {
        return genericDao.getAll();
    }

    public T get(PK id) {
        return genericDao.get(id);
    }

    public boolean exists(PK id) {
        return genericDao.exists(id);
    }

    public T save(T object) {
        return genericDao.save(object);
    }

    public void remove(PK id) {
        genericDao.remove(id);
    }
}
