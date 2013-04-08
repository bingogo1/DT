package bin.g11n.gt.security;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.acegisecurity.providers.encoding.PlaintextPasswordEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;


/**
 * This class is used to override the DaoAuthenticationProvider that provided by acegi.
 * The method additionalAuthenticationChecks has been overriden here.
 * 
 * GtDaoAuthenticationProvider.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GtDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    //~ Instance fields ================================================================================================

    private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
    private SaltSource saltSource;
    private UserDetailsService userDetailsService;
    private boolean includeDetailsObject = true;

    //~ Methods ========================================================================================================

    protected void additionalAuthenticationChecks(UserDetails userDetails,
        UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {
    	ResourceBundle bundle = PropertyResourceBundle.getBundle("jdbc", Locale.ENGLISH);
    	String bypass = bundle.getString("BypassADCheck");
    	if (bypass.equalsIgnoreCase("true")) {
    		return ;
    	} else {
    		boolean isAuth = isValidPasswordAuth(userDetails, authentication);
    		//If LDAP authorization, use this api to replace the password handling api.
//    		boolean isAuth = isValidLdapAuth(userDetails, authentication);
    		if (!isAuth) {
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
            }
    	}
    }
    
    /**
     * If LDAP authorization is valid, return true, else throw exception.
     * 
     * @param userDetails
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    private boolean isValidLdapAuth(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
    			throws AuthenticationException{
    	//ldap authentication
    	ADCheck target = new ADCheck();
		boolean isAuth = target.auth(userDetails.getUsername()+ "@asiapacific.com",authentication.getCredentials().toString());
		//force it false for dev. environment.
		if (!isAuth) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
        }
		return isAuth;
    }

    /**
     * If password is valid, return true, else throw exception.
     * 
     * @param userDetails
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    private boolean isValidPasswordAuth(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
    			throws AuthenticationException{
		Object salt = null;
		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}
		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
					includeDetailsObject ? userDetails : null);
		}
		String presentedPassword = authentication.getCredentials() == null ? "" : authentication.getCredentials()
				.toString();

		if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
					includeDetailsObject ? userDetails : null);
		}
		return true;
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public SaltSource getSaltSource() {
        return saltSource;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                "UserDetailsService returned null, which is an interface contract violation");
        }

        return loadedUser;
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords. If not set, {@link
     * PlaintextPasswordEncoder} will be used by default.
     *
     * @param passwordEncoder The passwordEncoder to use
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * The source of salts to use when decoding passwords. <code>null</code> is a valid value, meaning the
     * <code>DaoAuthenticationProvider</code> will present <code>null</code> to the relevant
     * <code>PasswordEncoder</code>.
     *
     * @param saltSource to use when attempting to decode passwords via the <code>PasswordEncoder</code>
     */
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

	public boolean isIncludeDetailsObject() {
		return includeDetailsObject;
	}

	public void setIncludeDetailsObject(boolean includeDetailsObject) {
		this.includeDetailsObject = includeDetailsObject;
	}
}
