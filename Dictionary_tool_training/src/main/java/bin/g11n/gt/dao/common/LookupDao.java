package bin.g11n.gt.dao.common;

import java.util.List;

import bin.g11n.gt.model.Role;


/**
 * Lookup Data Access Object (GenericDao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface LookupDao extends UniversalDao {
    //~ Methods ================================================================

    /**
     * Returns all Roles ordered by name
     */
    public List<Role> getRoles();
}
