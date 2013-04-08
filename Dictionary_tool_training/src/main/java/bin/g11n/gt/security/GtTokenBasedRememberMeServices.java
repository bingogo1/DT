package bin.g11n.gt.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices;
import org.springframework.beans.factory.InitializingBean;


/** override GtTokenBasedRememberMeServices.cancelCookie() method in case null point exception.
 * 
 * GtTokenBasedRememberMeServices.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GtTokenBasedRememberMeServices extends TokenBasedRememberMeServices implements RememberMeServices, InitializingBean, LogoutHandler {

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null){
    		this.cancelCookie(request, response, "Logout of user " + authentication.getName());
        }else {
    		this.cancelCookie(request, response, "Logout of user session timeout user.");
        }
    }

	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response, String reasonForLog) {
		if ((reasonForLog != null) && logger.isDebugEnabled()) {
			logger.debug("Cancelling cookie for reason: " + reasonForLog);
		}

		response.addCookie(makeCancelCookie(request));
	}


}
