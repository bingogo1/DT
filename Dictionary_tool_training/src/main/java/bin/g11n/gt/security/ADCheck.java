package bin.g11n.gt.security;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.ILogger.ELevel;

/**
 * User authorization class.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ADCheck {
	public LdapContext ctx = null;
	public Hashtable env = null;
	
	private static transient final ILogger logger = G11nLogFactory.getLog(ADCheck.class);
	
	public static void main (String[] args) {
		logger.log(ELevel.DEBUG, "Start...");
		ADCheck target = new ADCheck();
		boolean isAuth = target.auth("guobin@asiapacific.com","gg");
		if (!isAuth) {
			logger.log(ELevel.WARNING, "Auth failed...");
		}
			
	}
	
	public ADCheck() {
		env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		//What's HP NIL server?
//		env.put(Context.PROVIDER_URL,"ldap://xxxxx:389");
//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		try {
            ctx = new InitialLdapContext(env, null);

        } catch (NamingException e) {
        	logger.log(ELevel.WARNING, "user cannot be authtenticated.");
        }
	}
	
	public boolean auth(String username,String password) {
		try {
			ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, username);
			ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
			ctx.reconnect(null);
			logger.log(ELevel.INFO, username + " pass authenticated");
			return true;
		} catch (AuthenticationException ae) {
			logger.log(ELevel.WARNING, username + " cannot be authtenticated.");
			//ae.printStackTrace();
			return false;
		} catch (NamingException ne) {
			logger.log(ELevel.WARNING, username + " cannot be authtenticated.");
			//ne.printStackTrace();
			return false;
		} catch (NullPointerException ne) {
			return false;
		}
	}
}
