package bin.g11n.gt.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.common.exception.UserExistsException;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UpdateUserAction extends BaseAction {
    private static final long serialVersionUID = 6938712115191L;
	private Long id;

    private User user;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	@SuppressWarnings("unchecked")
	public void prepare() throws GtException{
		if (id == null) {
			user = userManager.getUserById(user.getId());
		} else {
			user = userManager.getUserById(id);
		}

	}
	
	/**
	 * init 
	 *
	 * @return  String
	 */
	public String init(){
		return SUCCESS;
	}
	
    public String delete() throws IOException {       
        userManager.removeUser(user.getId().toString());
        List<String> args = new ArrayList<String>();
        args.add(user.getFullName());
        saveMessage(getText("user.deleted", args));

        return SUCCESS;
    }

    public String update() throws IOException {
    	User existingUser = userManager.getUserByUsername(user.getUsername());
    	if (existingUser != null && !user.getId().equals(existingUser.getId())){
            addActionError(getText("errors.existing.user", new String[]{user.getUsername()}));
            return ERROR;
    	}
    	String existedPasswrod = this.userManager.getUserById(user.getId()).getPassword();
    	// check if new password is blank
    	if (StringUtil.isEmpty(user.getPassword()))
    	{
    		if (StringUtil.isNotEmpty(user.getConfirmPassword()))
	    	{
	    		addActionMessage(getText("errors.twofields"));
	    		return INPUT;
	    	}
    		else
    			user.setPassword(existedPasswrod);
    	}
    	// check if password matchs confirmpassword
    	else
    	{
    		if (StringUtil.isEmpty(user.getConfirmPassword()) 
    				|| !StringUtil.equals(user.getPassword(), user.getConfirmPassword())) {
	    		addActionMessage(getText("errors.twofields"));
	    		return INPUT;
	    	}
    		else {
	    		String algorithm = (String) getConfiguration().get(Constants.ENC_ALGORITHM);
	
	            if (algorithm == null) { // should only happen for test case
	            	logger.log(ILogger.ELevel.DEBUG, "assuming testcase, setting algorithm to 'SHA'");
	                algorithm = "SHA";
	            }
            
	            user.setPassword(StringUtil.encodePassword(user.getPassword(), algorithm));
    		}
    		
    	}
    	
    	User updateOp = SecurityUtil.getCurrentUserInfo();
    	Date date = DateUtil.getNewDate();
    	
    	user.setUpdateOp(updateOp);
    	user.setUpdateDate(date);
    	
    	userManager.updateUser(user, updateOp);

		logger.log(ILogger.ELevel.INFO, "Update user " + user.getUsername() + " successfully");
    	addActionMessage(getText("saveSuccessfully"));
        return SUCCESS;
    }

    public String execute() {
        return SUCCESS;
    }

    public String cancel() {
//        if (!"list".equals(from)) {
//            return "mainMenu";
//        }
        return "cancel";
    }

    public String save() throws Exception {
        if (delete != null) {
            return delete();
        }

        Boolean encrypt = (Boolean) getConfiguration().get(Constants.ENCRYPT_PASSWORD);

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
        	User existingUser = userManager.getUserByUsername(user.getUsername());
        	if (!existingUser.getId().equals(user.getId())){
                addActionError(getText("errors.existing.user", new String[]{user.getUsername()}));
                return ERROR;
        	}
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
        }
    }

}
