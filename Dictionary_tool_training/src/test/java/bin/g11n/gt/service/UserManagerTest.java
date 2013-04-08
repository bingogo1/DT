package bin.g11n.gt.service;

import java.util.HashSet;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.common.RoleManager;
import bin.g11n.gt.service.common.UserManager;
import bin.g11n.gt.util.StringUtil;


public class UserManagerTest extends BaseManagerTestCase {
    //~ Instance fields ========================================================

    private UserManager mgr = null;
    private RoleManager roleManager = null;
    protected transient final ILogger logger = G11nLogFactory.getLog(getClass());
    private User user;
    
    public void setUserManager(UserManager userManager) {
        this.mgr = userManager;
    }
    
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public void testGetUser() throws Exception {
        user = mgr.getUserByUsername("gb");
        assertNotNull(user);
        
        logger.log(ILogger.ELevel.DEBUG, user.toString());
        assertEquals(1, user.getRoles().size());
    }

//    public void testAddAndRemoveUser() throws Exception {
//        user = mgr.getUserByUsername("gb");
//    	SecurityUtil.getCurrentUserInfo();
//    	logger.log(ILogger.ELevel.DEBUG, "saving user with updated phone number: " + user);
//        User newUser = (User)StringUtil.copy(user);
//        newUser.setId(null);
//        newUser.setUsername("gbtest");
//        newUser.setEmail("bing@hp.com");
//        newUser.setRoles(new HashSet<Role>());
//        mgr.addUser(newUser, SecurityUtil.getCurrentUserInfo());
//        assertEquals("gbtest", newUser.getUsername());
//
//        logger.log(ILogger.ELevel.DEBUG, "removing user...");
//
//        mgr.removeUser(newUser.getId().toString());
//
//        try {
//        	newUser = mgr.getUserByUsername("gbtest");
//        	assertNull("good", newUser);
//        } catch (Exception e) {
//        	logger.log(ILogger.ELevel.DEBUG, e.toString());
//            assertNotNull(e);
//        }
//    }

}
