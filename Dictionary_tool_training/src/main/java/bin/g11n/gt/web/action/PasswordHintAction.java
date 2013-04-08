package bin.g11n.gt.web.action;

import java.util.ArrayList;
import java.util.List;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.model.User;
import bin.g11n.gt.util.RequestUtil;


/**
 * Action class to send password hints to registered users.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class PasswordHintAction extends BaseAction {
    private static final long serialVersionUID = -4037514607101222025L;
    private String username;
    
    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String execute() {
    	List<String> args = new ArrayList<String>();
        
        // ensure that the username has been sent
        if (username == null) {
        	logger.log(ILogger.ELevel.WARNING, "Username not specified, notifying user that it's a required field.");

            args.add(getText("user.username"));
            addActionError(getText("errors.required", args));
            return INPUT;
        }
        logger.log(ILogger.ELevel.DEBUG, "Processing Password Hint...");
        
        // look up the user's information
        try {
            User user = userManager.getUserByUsername(username);

            StringBuffer msg = new StringBuffer();
            msg.append("Your password hint is: " + user.getPasswordHint());
            msg.append("\n\nLogin at: " + RequestUtil.getAppURL(getRequest()));

            args.add(username);
            
            saveMessage(getText("login.passwordHint.sent", args));
            
        } catch (Exception e) {
            e.printStackTrace();
            // If exception is expected do not rethrow
            addActionError(getText("login.passwordHint.error", args));
            return INPUT;
        }

        return SUCCESS;
    }
}
