package bin.g11n.gt.service.impl.common;

import java.util.ArrayList;
import java.util.List;

import bin.g11n.gt.dao.common.LookupDao;
import bin.g11n.gt.model.LabelValue;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.service.common.LookupManager;



/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class LookupManagerImpl extends BaseManager implements LookupManager {
    //~ Instance fields ========================================================

    private LookupDao dao;

    //~ Methods ================================================================

    public void setLookupDao(LookupDao dao) {
        super.dao = dao;
        this.dao = dao;
    }
    /**
     * @see LookupManager#getAllRoles()
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }
}
