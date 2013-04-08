package bin.g11n.gt.dao;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.RoleConstants;
import bin.g11n.gt.dao.common.RoleDao;
import bin.g11n.gt.model.Role;


/**
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class RoleDaoTest extends BaseDaoTestCase {
    private RoleDao dao;

    public void setRoleDao(RoleDao dao) {
        this.dao = dao;
    }

    public void testGetRoleInvalid() throws Exception {
        Role role = dao.getRoleByName("badrolename");
        assertNull(role);
    }

    public void testGetRole() throws Exception {
        Role role = dao.getRoleByName(RoleConstants.USER_ROLE);
        assertNotNull(role);
    }

    public void testUpdateRole() throws Exception {
        Role role = dao.getRoleByName("user");
        logger.log(ILogger.ELevel.DEBUG, role.toString());
        role.setDescription("test descr");

        dao.save(role);
        assertEquals(role.getDescription(), "test descr");
    }

    public void testAddAndRemoveRole() throws Exception {
        Role role = new Role();
        role.setName("testRole");
        role.setDescription("new role descr");
        role.setDeleteFlg("0");
        
        dao.save(role);
        setComplete(); // change behavior from rollback to commit
        endTransaction();

        startNewTransaction();
	    role = dao.getRoleByName("testrole");
        assertNotNull(role.getDescription());

        dao.removeRole("testrole");
        setComplete();
        endTransaction(); // deletes role from database

        role = dao.getRoleByName("testrole");
        assertNull(role);
    }
}
