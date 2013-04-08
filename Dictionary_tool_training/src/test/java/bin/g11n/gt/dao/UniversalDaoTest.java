package bin.g11n.gt.dao;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.ObjectRetrievalFailureException;

import bin.g11n.gt.dao.BaseDaoTestCase;
import bin.g11n.gt.dao.common.UniversalDao;
import bin.g11n.gt.model.User;


/**
 * This class tests the generic GenericDao and BaseDao implementation.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UniversalDaoTest extends BaseDaoTestCase {
    protected UniversalDao universalDao;

    /**
     * This method is used instead of setUniversalDao b/c setUniversalDao uses
     * autowire byType <code>setPopulateProtectedVariables(true)</code> can also
     * be used, but it's a little bit slower.
     */
    public void onSetUpBeforeTransaction() throws Exception {
        universalDao = (UniversalDao) applicationContext.getBean("universalDao");
    }

    public void onTearDownAfterTransaction() throws Exception {
        universalDao = null;
    }

    /**
     * Simple test to verify CRUD works.
     */
    public void testCRUD() {
        User user = new User();
        // set required fields
        user.setUsername("andrew");
        user.setPassword("bar");
        user.setFirstName("first");
        user.setLastName("last");
        user.setEmail("foo@bar.com");

        // create
        user = (User)universalDao.save(user);
        assertNotNull(user.getId());

        // retrieve
        user = (User) universalDao.get(User.class, user.getId());
        assertNotNull(user);
        assertEquals(user.getLastName(), "last");

        // update
//        user.getAddress().setCountry("USA");
        universalDao.save(user);
//        assertEquals(user.getAddress().getCountry(), "USA");

        // delete
        universalDao.remove(User.class, user.getId());
        try {
            universalDao.get(User.class, user.getId());
            fail("User 'foo' found in database");
        } catch (ObjectRetrievalFailureException e) {
            assertNotNull(e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) { // Spring 2.0 throws this one
            assertNotNull(e.getMessage());
        }
    }
}
