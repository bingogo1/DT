package bin.g11n.gt.web.action;


import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.struts2.ServletActionContext;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.RoleConstants;
import bin.g11n.gt.common.exception.UserExistsException;
import bin.g11n.gt.model.User;
import bin.g11n.gt.util.StringUtil;


/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class SignupAction extends BaseAction {
    private static final long serialVersionUID = 6558317334878272308L;
    private User user;
    private String cancel;
    
    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }

    public String execute() {
        if (cancel != null) {
            return CANCEL;
        }
        if (ServletActionContext.getRequest().getMethod().equals("GET")) {
            return INPUT;
        }
        return SUCCESS;
    }
    
    public String doDefault() {
        return INPUT;
    }
    
    public String save() throws Exception {
        Boolean encrypt = (Boolean) getConfiguration().get(Constants.ENCRYPT_PASSWORD);
        
        if (encrypt != null && encrypt) {
            String algorithm = (String) getConfiguration().get(Constants.ENC_ALGORITHM);
    
            if (algorithm == null) { // should only happen for test case
            	logger.log(ILogger.ELevel.DEBUG, "assuming testcase, setting algorithm to 'SHA'");
                algorithm = "SHA";
            }
        
            user.setPassword(StringUtil.encodePassword(user.getPassword(), algorithm));
        }
        
        user.setEnabled(true);
        
        // Set the default user role on this new user
        user.addRole(roleManager.getRole(RoleConstants.USER_ROLE));

        try {
            user = userManager.saveUser(user);
        } catch (UserExistsException e) {
        	logger.log(ILogger.ELevel.WARNING, "UserExistsException occurs");
            List<String> args = new ArrayList<String>();
            args.add(user.getUsername());
            args.add(user.getEmail());
            addActionError(getText("errors.existing.user", args));

            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            return INPUT;
        }

        saveMessage(getText("user.registered"));
        getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        // log user in automatically
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getConfirmPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return SUCCESS;
    }
}
