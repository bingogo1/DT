package bin.g11n.gt.web.taglib.auth;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * get bean proxy from the applicationConext files without by spring context.
 * Note: 
 *  1. The beans what will be called here must have been injected in the application context xml file.
 *  2. Don't add unnecessary application context files into the paths if you don't need them.
 *  
 * AppContext.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class AppContext {

	private static AppContext instance;
    /* paths*/
    private static String[] paths = {
    	"classpath:/com/hp/g11n/gt/web/taglib/auth/securityUtilContext.xml",
    	"classpath*:/applicationContext-resources.xml"};
	  private AbstractApplicationContext appContext;

	  public synchronized static AppContext getInstance() {
	    if (instance == null) {
	      instance = new AppContext();
	    }
//		URL relativePath = instance.getClass().getClassLoader().getResource("applicationContext-resources.xml");
	    return instance;
	  }

	  private AppContext() {
	    this.appContext = new ClassPathXmlApplicationContext(
	       paths);
	  }

	  public AbstractApplicationContext getAppContext() {
	    return appContext;
	  }
}

