package bin.g11n.gt.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.displaytag.pagination.PaginatedList;

import bin.g11n.cil.CILHelper;
import bin.g11n.cil.CILProducer;
import bin.g11n.cil.bundle.IG11nResourceBundle;
import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.common.RoleManager;
import bin.g11n.gt.service.common.UserManager;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * Implementation of <strong>ActionSupport</strong> that contains 
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Action classes.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BaseAction extends ActionSupport  implements Preparable{
    private static final long serialVersionUID = 3525445612504421307L;
    public static final String CANCEL = "cancel";
    protected transient final ILogger logger = G11nLogFactory.getLog(getClass());
    protected IG11nResourceBundle resources;
    protected UserManager userManager = null;
    protected RoleManager roleManager = null;
    protected String from = null;
    protected String cancel = null;
    protected String delete = null;
    protected String save = null;
    protected String templateName = null;   
    protected String buttonName = null;
    
    //DisplayTag variables
    protected PaginatedList paginatedList = null;
    protected int currentPage;
    protected int pageSize = Constants.PAGE_RECORD_SIZE;
	protected Integer totalSize;
    
    public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getTotalSize() {
		return totalSize;
	}


	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}


	public String getFrom() {
		return from;
	}


	public void setButtonName(String buttonName) {
	      this.buttonName = buttonName;
	}

	
    public BaseAction() {
		super();
    	try {
    		if (logger.isEnabled(ILogger.ELevel.INFO)){
        		String msg = this.toString();
        		User user = SecurityUtil.getCurrentUserInfo();
        		String userName = "";
        		if (user != null){
        			userName = user.getUsername();
        		}
        		logger.log(ILogger.ELevel.INFO, userName + ": " + msg.substring(msg.lastIndexOf(".")+1, msg.indexOf("@")) + "  executing -->");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    	}
	}


	public String cancel() {
        return CANCEL;
    }
    
    @SuppressWarnings("unchecked")
    protected void saveMessage(String msg) {
        List messages = (List) getRequest().getSession().getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        getRequest().getSession().setAttribute("messages", messages);
    }
    
    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    protected Map getConfiguration() {
        Map config = (HashMap) getSession().getServletContext().getAttribute(Constants.CONFIG);
        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }
        return config;
    }
    
    /**
     * Convenience method to get the request
     * @return current request
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }
    
    /**
     * Convenience method to get the response
     * @return current response
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * Convenience method to get the session
     */
    protected HttpSession getSession() {
    	return getRequest().getSession();
    }
    
	protected IG11nResourceBundle getBundleInstance() {
		if (resources == null) {
			// Create and set up the window.
			CILHelper cILHelper = new CILHelper((new CILProducer())
					.createInfra());
			// Get interface
			resources = cILHelper.getResourceBundle();
			// Instantiates the bundle class with 1 parameter of baseName.
			resources = resources.getBundle(Constants.BUNDLE_KEY);
		}
		return resources;
	}

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    /**
     * Convenience method for setting a "from" parameter to indicate the previous page.
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }
    
    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setSave(String save) {
        this.save = save;
    }
    
    /**
     * This method is called to allow the action to prepare itself.
     *
     * @throws Exception thrown if a system level exception occurs.
     */
    public void prepare() throws GtException {
    	//to be overrided by your own action class.
    }

	/**
	 * the Getter of paginatedList
	 * 
	 * @return Returns the paginatedList.
	 */
	public PaginatedList getPaginatedList() {
		return paginatedList;
	}

	/**
	 * the Setter of paginatedList
	 *
	 * @param paginatedList The paginatedList to set.
	 */
	public void setPaginatedList(PaginatedList paginatedList) {
		this.paginatedList = paginatedList;
	}

}
