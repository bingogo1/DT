package bin.g11n.gt.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.struts2.ServletActionContext;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.exception.UserExistsException;
import bin.g11n.gt.model.Bu;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.service.app.BuManager;
import bin.g11n.gt.service.common.UserManager;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class AddUserAction extends BaseAction {
    private static final long serialVersionUID = 6776558938712115191L;
    private List users;
    private User user;
    private String username;
    
    private BuManager buManager;
    private UserManager userManager;
    
    public BuManager getBuManager() {
		return buManager;
	}

	public void setBuManager(BuManager buManager) {
		this.buManager = buManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public List getUsers() {
        return users;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String delete() throws IOException {       
//        userManager.removeUser(user.getId().toString());
//        List<String> args = new ArrayList<String>();
//        args.add(user.getFullName());
//        saveMessage(getText("user.deleted", args));
//
//        return SUCCESS;
//    }

//    public String edit() throws IOException {
//        HttpServletRequest request = getRequest();
//        boolean editProfile =
//            (request.getRequestURI().indexOf("editProfile") > -1);
//
//        // if URL is "editProfile" - make sure it's the current user
//        if (editProfile) {
//            // reject if username passed in or "list" parameter passed in
//            // someone that is trying this probably knows the AppFuse code
//            // but it's a legitimate bug, so I'll fix it. ;-)
//            if ((request.getParameter("username") != null) ||
//                    (request.getParameter("from") != null)) {
//                ServletActionContext.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
//                logger.log(ILogger.ELevel.WARNING, "User '" + request.getRemoteUser() + "' is trying to edit user '" +
//                         request.getParameter("username") + "'");
//
//                return null;
//            }
//        }
//
//        // if a user's username is passed in
//        if (username != null) {
//            // lookup the user using that id
//            user = userManager.getUserByUsername(username);
//        } else if (editProfile) {
//            user = userManager.getUserByUsername(request.getRemoteUser());
//        } else {
//            user = new User();
//            user.addRole(new Role());
//        }
//
//        if (user.getUsername() != null) {
//            user.setConfirmPassword(user.getPassword());
//
//            // if user logged in with remember me, display a warning that they can't change passwords
//            logger.log(ILogger.ELevel.DEBUG, "checking for remember me login...");
//
//            AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
//            SecurityContext ctx = SecurityContextHolder.getContext();
//
//            if (ctx != null) {
//                Authentication auth = ctx.getAuthentication();
//
//                if (resolver.isRememberMe(auth)) {
//                    getSession().setAttribute("cookieLogin", "true");
//                    saveMessage(getText("userProfile.cookieLogin"));
//                }
//            }
//        }
//       
//        return SUCCESS;
//    }

    public String execute() {        
        return "input";
    }

    public String cancel() {
        if (!"list".equals(from)) {
            return SUCCESS;
        }
        return "cancel";
    }

    public String save() throws Exception {
//        if (delete != null) {
//            return delete();
//        }
    	if (user == null || StringUtil.isBlank(user.getUsername()) || 
    			StringUtil.isBlank(user.getFirstName()) || StringUtil.isBlank(user.getLastName()) || 
    			StringUtil.isBlank(user.getPassword()) || StringUtil.isBlank(user.getConfirmPassword()))
			return Constants.RESULT_ERROR;
    	
    	if (!user.getPassword().equals(user.getConfirmPassword()))
    	{
    		addActionMessage(getText("errors.twofields"));
    		return Constants.RESULT_ERROR;
    	}
    	
    	if (this.userManager.getUserByUsername(user.getUsername()) != null)
    	{
    		addActionMessage(getText("errors.existing.user", new String[]{user.getUsername()}));
    		return "existedCD";
    	}
		
		User op = SecurityUtil.getCurrentUserInfo();
		// hard coding, need to be alternated
		Bu bu = this.buManager.findBuById(1L);
		Date date = DateUtil.getNewDate();
		
		String algorithm = (String) getConfiguration().get(Constants.ENC_ALGORITHM);

        if (algorithm == null) { // should only happen for test case
        	logger.log(ILogger.ELevel.DEBUG, "assuming testcase, setting algorithm to 'SHA'");
            algorithm = "SHA";
        }

        user.setPassword(StringUtil.encodePassword(user.getPassword(), algorithm));
		
		user.setAccountExpired(Constants.ACCOUNT_EXPIRED_BOOLEAN_VALID);
		user.setBuId(bu);
		if (StringUtil.isEmpty(user.getFirstLocalName()))
			user.setFirstLocalName(user.getFirstName());
		if (StringUtil.isEmpty(user.getLastLocalName()))
			user.setLastLocalName(user.getLastName());
		user.setCreateDate(date);
		user.setUpdateDate(date);
		user.setCreateOp(op);
		user.setUpdateOp(op);
		user.setVersion(null);
		user.setEnabled(Constants.ACCOUNT_ENABLED_BOOLEAN_ENABLED);
		user.setCredentialsExpired(Constants.CREDENTIALS_EXPIRED_BOOLEAN_VALID);
    	
		user.setDeleteFlg("0");
		
		user = this.userManager.saveUser(user);
		
		if (user != null)
		{
			logger.log(ILogger.ELevel.INFO, "Save user " + user.getUsername() + " successfully");
			addActionMessage(getText("saveSuccessfully"));
			return "success";
		}
		else
			return Constants.RESULT_ERROR;
        /*Boolean encrypt = (Boolean) getConfiguration().get(Constants.ENCRYPT_PASSWORD);

        if ("true".equals(getRequest().getParameter("encryptPass")) && (encrypt != null && encrypt)) {
            String algorithm = (String) getConfiguration().get(Constants.ENC_ALGORITHM);

            if (algorithm == null) { // should only happen for test case
            	logger.log(ILogger.ELevel.DEBUG, "assuming testcase, setting algorithm to 'SHA'");
                algorithm = "SHA";
            }

            user.setPassword(StringUtil.encodePassword(user.getPassword(), algorithm));
        }

        Integer originalVersion = user.getVersion();
        boolean isNew = ("".equals(getRequest().getParameter("user.version")));

        String[] userRoles = getRequest().getParameterValues("userRoles");

        for (int i = 0; userRoles != null && i < userRoles.length; i++) {
            String roleName = userRoles[i];
            user.addRole(roleManager.getRole(roleName));
        }

        try {
        	user = userManager.saveUser(user);
        } catch (UserExistsException e) {
        	logger.log(ILogger.ELevel.WARNING, "UserExistsException occurs");
            List<String> args = new ArrayList<String>();
            args.add(user.getUsername());
            args.add(user.getEmail());
            addActionError(getText("errors.existing.user", args));

            // reset the version # to what was passed in
            user.setVersion(originalVersion);
            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            return INPUT;
        }

        if (!"list".equals(from)) {
            // add success messages
            saveMessage(getText("user.saved"));

            return "mainMenu";
        } else {
            // add success messages
            List<String> args = new ArrayList<String>();
            args.add(user.getFullName());
            if (isNew) {
                saveMessage(getText("user.added", args));
                return "addAnother";
            } else {
                saveMessage(getText("user.updated.byAdmin", args));
                return INPUT;
            }
        }*/
    }

//    public String list() {
//        users = userManager.getUsers(new User());
//        return SUCCESS;
//    }
}
