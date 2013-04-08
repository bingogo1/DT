package bin.g11n.gt.dao;

import java.util.List;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.dao.common.LookupDao;


/**
 * This class tests the current LookupDao implementation class
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class LookupDaoTest extends BaseDaoTestCase {
    private LookupDao dao;
    
    public void setLookupDao(LookupDao dao) {
        this.dao = dao;
    }

    public void testGetRoles() {
        List roles = dao.getRoles();
        logger.log(ILogger.ELevel.DEBUG, roles.toString());
        assertTrue(roles.size() > 0);
    }
}
