package bin.g11n.gt.service;

import java.util.List;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import bin.g11n.gt.model.User;
import bin.g11n.gt.service.common.GenericManager;


/**
 * @author mraible
 */
public class GenericManagerTest extends AbstractTransactionalDataSourceSpringContextTests {
    GenericManager<User, Long> userManager = null;

    public void setUserManager(GenericManager<User, Long> genericManager) {
        this.userManager = genericManager;
    }
    
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String[] {"action-servlet.xml",
	            "classpath*:/applicationContext-service.xml",
                 "classpath*:/applicationContext-resources.xml",
                 "classpath*:/applicationContext-dao.xml",
             	 "classpath*:/security.xml"
             	};
    }

    public void testGetUsers() {
        List<User> users = userManager.getAll();
        assertTrue(users.size() > 0);
    }

    public void testGetUser() {
        User user = userManager.get(1L);
        assertEquals("tomcat", user.getUsername());
    }
}
