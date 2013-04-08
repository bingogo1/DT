package bin.g11n.gt.dao.impl.common;

import java.util.List;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.LookupDao;
import bin.g11n.gt.model.Role;


/**
 * Hibernate implementation of LookupDao.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class LookupDaoHibernate extends UniversalDaoHibernate implements LookupDao {

    /**
     * get normal role list from db
     * 
     * getRoles
     * 
     * @author bguo
     *
     * @return  List<Role>
     */
	@SuppressWarnings("unchecked")
    public List<Role> getRoles() {
		logger.log(ILogger.ELevel.DEBUG, "retrieving all role names...");

        return getHibernateTemplate().find(
        		"from Role role where role.deleteFlg='" + 
        		Constants.DELETE_FLG_NORMAL + 
        		"' ");
    }
}
