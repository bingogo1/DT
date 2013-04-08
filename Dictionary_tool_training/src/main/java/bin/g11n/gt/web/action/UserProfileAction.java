package bin.g11n.gt.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.common.exception.GtException;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/04/20
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UserProfileAction extends BaseAction {
    private static final long serialVersionUID = 6934144191L;
    private User user;
    
    private String oldPassword;
    public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	private String newPassword;
    private String newConfirmPassword;
    
	public String getNewPassword() {
		return newPassword;
	}

	public String getNewConfirmPassword() {
		return newConfirmPassword;
	}

	public void setNewConfirmPassword(String newConfirmPassword) {
		this.newConfirmPassword = newConfirmPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	@SuppressWarnings("unchecked")
	public void prepare() throws GtException{
			user = SecurityUtil.getCurrentUserInfo();
	}
	
    public String editProfile() throws IOException {
    	//If the user try to change password, the old password must be correct.
    	if (!validatePassword()){
    		return ERROR;
    	}
    	User updateOp = user;
    	Date date = DateUtil.getNewDate();
    	
    	user.setUpdateOp(updateOp);
    	user.setUpdateDate(date);
    	User existingUser = userManager.getUserByUsername(user.getUsername());
    	if (existingUser != null && !user.getId().equals(existingUser.getId())){
            addActionError(getText("errors.existing.user", new String[]{user.getUsername()}));
            return ERROR;
    	}
    	userManager.updateUser(user, updateOp);

    	addActionMessage(getText("saveSuccessfully"));
        return SUCCESS;
    }

    private boolean validatePassword(){
    	boolean isValidFlg = false;
		String algorithm = (String) getConfiguration().get(Constants.ENC_ALGORITHM);
        if (algorithm == null) { // should only happen for test case
            algorithm = "SHA";
        }
    	if ( StringUtil.isBlank(newPassword)){
    		isValidFlg = true;
    	}else {
    		if (StringUtil.isBlank(oldPassword) ||
    				!StringUtil.encodePassword(oldPassword, algorithm).equals(user.getPassword())){
    			addActionError(getText("errors.oldpassword.error"));
    		}else if (StringUtil.isNotBlank(newConfirmPassword) && 
    				newConfirmPassword.equals(newPassword)){
    			user.setPassword(StringUtil.encodePassword(newPassword, algorithm));
    			isValidFlg = true;
    		}else {
	    		List<String> args = new ArrayList<String>();
	    		if (!newConfirmPassword.equals(newPassword)){
	                args.add(getText("user.confirmPassword.new"));
	                args.add(getText("user.password.new"));
	                addActionError(getText("errors.newConfirmPassord.error", args));
	    		}
    		}
    	}
     	return isValidFlg;
    	
    }
}
