package bin.g11n.gt.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import org.directwebremoting.WebContextFactory;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * handle messages of the project 
 * to call the method in this file like this: 
 * MessageUtil.getInstance().getText(keyStr, valuesArray);
 * 
 * MessageUtil.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class MessageUtil {
	/* MessageUtil instance */
	private static MessageUtil instance = null;
	
	/* callerAction the action class instance who calls this util file. */
	private BaseAction callerAction = null;

	/* set file reading pattern */
	private Properties config = new Properties();

	/* property file list */
	private static final String[] resources = {Constants.BUNDLE_KEY , 
			Constants.BUNDLE_KEY_COMMON};

	/* resource file key so that the specified file can be read. */
	private static final String[] resourceKeys = { "", "com", "app"};

	/**
	 * constructor
	 */
	private MessageUtil() {
		init();
	}

	/**
	 * init void
	 */
	private void init() {
		for (int i = 0; i < resources.length; i++) {
			//display message only in english
			ResourceBundle resource = PropertyResourceBundle
					.getBundle(resources[i], Locale.ENGLISH);
			Enumeration enu = resource.getKeys();
			String key = null;
			String value = null;
			while (enu.hasMoreElements()) {
				key = (String) enu.nextElement();
				value = resource.getString(key);
				// set property object
				config.setProperty(key, value);
			}
		}
	}

	/** if this method is called from action class, return this action instance.
	 * 
	 * getCallerActionIns 
	 *
	 * @return  BaseAction
	 */
	private BaseAction getCallerActionIns() {
		BaseAction callerAction = null;
		if (ServletActionContext.getContext().getName() != null) {
			callerAction = (BaseAction)ActionContext.getContext().getActionInvocation().getAction();
		}
		return callerAction;
	}
	
    /**
     * add error message into an action so that it can display on the screen. 
     * 
     * addActionError 
     *
     * @param anErrorMessage  
     * @return void
     */
    public void addActionError(String anErrorMessage) {
    	getCallerActionIns().addActionError(anErrorMessage);
    }

    /**
     * add error message into an action so that it can display on the screen. 
     * 
     * addActionError 
     *
	 * @param key
	 *            message key
	 * @param messageArgs
	 *            parameter value
     * @return void
     */
    public void addActionError(String messageKey, Object[] messageArgs) {
    	getCallerActionIns().addActionError(getText(messageKey, messageArgs));
    }

    /**
     * add info message into an action so that it can display on the screen.
     * 
     * addActionMessage 
     *
     * @param aMessage  void
     */
    public void addActionMessage(String aMessage) {
    	getCallerActionIns().addActionMessage(aMessage);
    }

    /**
     * add info message into an action so that it can display on the screen.
     * 
     * addActionMessage 
     *
	 * @param key
	 *            message key
	 * @param messageArgs
	 *            parameter value
     * @return void
     */
    public void addActionMessage(String messageKey, Object[] messageArgs) {
    	getCallerActionIns().addActionMessage(getText(messageKey, messageArgs));
    }

    /**
     * add error message on an field so that it can display on the screen.
     * 
     * addFieldError 
     *
     * @param fieldName
     * @param errorMessage message string 
     * @return void
     */
    public void addFieldError(String fieldName, String errorMessage) {
    	getCallerActionIns().addFieldError(fieldName, errorMessage);
    }

    /**
     * add error message on an field so that it can display on the screen.
     * 
     * addFieldError 
     *
     * @param fieldName
	 * @param key
	 *            message key
	 * @param messageArgs
	 *            parameter value
     * @return void
     */
    public void addFieldError(String fieldName, String messageKey, Object[] messageArgs) {
    	getCallerActionIns().addFieldError(fieldName, getText(messageKey, messageArgs));
    }

    /**
     * save message into session. 
     * 
     * saveMessage 
     *
     * @param msg 
     * @return void
     */
    public void saveMessage(String msg) {
    	try {
    		HttpSession session = getSession();
            List messages = (List) session.getAttribute("messages");
            if (messages == null) {
                messages = new ArrayList();
            }
            messages.add(msg);
            session.setAttribute("messages", messages);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    /**
     * save error message into session 
     * 
     * saveErrorMessage 
     *
     * @param msg 
     * @return void
     */
    public void saveErrorMessage(String msg) {
    	try {
    		HttpSession session = getSession();
            List messages = (List) session.getAttribute("errorMessages");
            if (messages == null) {
                messages = new ArrayList();
            }
            messages.add(msg);
            session.setAttribute("errorMessages", messages);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
	/**
	 * generate message with one parameter
	 * 
	 * @param key
	 *            message key
	 * @param arg
	 *            parameter value
	 * @return String message
	 */
	public String getText(String key) {
		return getText(key, new Object[]{});
	}

	/**
	 * generate message with one parameter
	 * 
	 * @param key
	 *            message key
	 * @param arg
	 *            parameter value
	 * @return String message
	 */
	public String getText(String key, String arg) {
		return getText(key, new Object[]{arg});
	}

	/**
	 * generate message with multiple parameter
	 * 
	 * @param key
	 *            message key
	 * @param messageArgs
	 *            parameter value
	 * @return String message
	 */
	public String getText(String key, Object[] messageArgs) {
		// rid the dot
		// replace the prefix of the key so that it can be found in the
		// properties file.
		if (key.indexOf(".") == 0) { 
			key = key.substring(1, key.length());
		}else if (key.indexOf("com.") == 0){
			key = key.replaceFirst("com.", "");
		}else if (key.indexOf("app.") == 0){
			key = key.replaceFirst("app.", "");
		}
		String msg = MessageFormat.format(config.getProperty(key), messageArgs);
		if (msg == null)
			return "";
		return msg;
	}

	/**
	 * get MessageUtil Object Instance
	 * 
	 * @return MessageUtil Object
	 */
	public static MessageUtil getInstance() {
		if (instance == null) {
			instance = new MessageUtil();
		}
		return instance;
	}
	
    /** get session from context
     * 
     * getSession 
     *
     * @return  HttpSession
     */
    private HttpSession getSession(){
		HttpSession session = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request == null){
			//if the caller is not from an action, get session from DWR context:
			session = WebContextFactory.get().getSession();
		}else {
			session = request.getSession();
		}
		return session;
    }
    

}
