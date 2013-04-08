 package bin.g11n.gt.common;

/**
 * Constant values used throughout the application.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class Constants {
    //~ Static fields/initializers =============================================
   public static final int PAGE_RECORD_SIZE = 10;
   public static final Integer MAX_EXPORT_SIZE = new Integer(10000);
	
	public static final String RESOURCE_PATH_PREFIX = "/";   

	public static final String COMMA = ",";
	
	public static final String PATH = "d:\\";

	public static final String SIMPLE_DATE_FORCAT = "yyyy-MM-dd HH:mm:ss.SSSS";

    /** The name of the common ResourceBundle used in this application */
    public static final String BUNDLE_KEY_COMMON = "ApplicationCommon";
    /** The name of the ResourceBundle used in this application */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /** The encryption algorithm key to be used for passwords */
    public static final String ENC_ALGORITHM = "algorithm";

    /** A flag to indicate if passwords should be encrypted */
    public static final String ENCRYPT_PASSWORD = "encryptPassword";

    /** File separator from System properties */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /** User home from System properties */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /** The name of the configuration hashmap stored in application scope. */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";
    
    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";


    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";
    
    //for review status setting
    /* approved */
    public static final String REVIEW_STATUS_APPROVED = "approved";
    /* waiting for approval*/
    public static final String REVIEW_STATUS_ORIGINAL_TRANSLATION = "original translation";
    /* under revision */
    public static final String REVIEW_STATUS_UNDER_REVISION = "Under Revision";
    /* change request */
    public static final String REVIEW_STATUS_REQUEST_CHANGE = "Request change";
    
    
    /* spreadsheet data parsing validation constants */
    public static final int DATA_PARSE_OVERLENGTH = -4;
    public static final int DATA_PARSE_ALREADY_EXIST = -3;
    public static final int DATA_PARSE_NO_REVIEWER = -2;
    public static final int DATA_PARSE_DATA_MISSING = -1;
    public static final int DATA_PARSE_NORMAL = 0;
    
    /* DELETE_FLG_DELETED: the delete flag. deleted*/
    public static final String DELETE_FLG_DELETED = "1";
    /* DELETE_FLG_DELETED: the delete flag. normal.*/
    public static final String DELETE_FLG_NORMAL = "0";
    
    public static final boolean ACCOUNT_EXPIRED_BOOLEAN_EXPIRED = true;
    public static final boolean ACCOUNT_EXPIRED_BOOLEAN_VALID = false;

    public static final boolean ACCOUNT_LOCKED_BOOLEAN_LOCKED = true;
    public static final boolean ACCOUNT_LOCKED_BOOLEAN_UNLOCKED = false;

    public static final boolean CREDENTIALS_EXPIRED_BOOLEAN_EXPIRED = true;
    public static final boolean CREDENTIALS_EXPIRED_BOOLEAN_VALID = false;

    public static final boolean ACCOUNT_ENABLED_BOOLEAN_ENABLED = true;
    public static final boolean ACCOUNT_ENABLED_BOOLEAN_DISABLED = false;

    /* ACTIVE_FLG_DISABLED: the active flag acegi set 1 as enabled.*/
    public static final String ACTIVE_FLG_DISABLED = "0";
    public static final boolean ACTIVE_FLG_BOOLEAN_DISABLED = false;
    /* ACTIVE_FLG_ENABLED: the active flag enabled. default value*/
    public static final String ACTIVE_FLG_ENABLED = "1";
    public static final boolean ACTIVE_FLG_BOOLEAN_ENABLED = true;
    
    /* ALL_ACCESS_FLG_DISABLED: the access flag 
     * false: the authorized user who has defined in z_role_function can access.*/
    public static final String ALL_ACCESS_FLG_DISABLED = "0";
    public static final boolean ALL_ACCESS_FLG__BOOLEAN_DISABLED = false;
    /* ALL_ACCESS_FLG_DISABLED: the access flag true: any authorized user can access.*/
    public static final String ALL_ACCESS_FLG__ENABLED = "1";
    public static final boolean ALL_ACCESS_FLG__BOOLEAN_ENABLED = true;
    
    /** Button Constants Start */
    public static final String BUTTON_CONFIRM = "Confirm";
    public static final String BUTTON_SAVE = "Save";
    public static final String BUTTON_OK = "OK";
    public static final String BUTTON_BACK = "Back";
    public static final String BUTTON_CANCEL = "Cancel";
    public static final String BUTTON_VIEW = "View";
    public static final String BUTTON_CHANGE = "Change";
    public static final String BUTTON_DELETE = "Delete";
    public static final String BUTTON_ADD = "Add";
    public static final String BUTTON_SEARCH = "Search";
    public static final String BUTTON_UPLOAD = "Upload";
    public static final String BUTTON_DOWNLOAD = "Download";
    public static final String BUTTON_FORECAST = "Forecast";
    public static final String BUTTON_AGREE = "agree";
    public static final String BUTTON_REJECT = "reject";
    public static final String BUTTON_RESET = "Reset";  
    public static final String BUTTON_LIST = "List"; 
    public static final String BUTTON_CHOOSE = "Choose"; 
    /** Button Constants End */
    
    /** Result Constants Start */
    public static final String RESULT_CONFIRM = "confirm";
    public static final String RESULT_SAVE = "save";
    public static final String RESULT_OK = "ok";
    public static final String RESULT_BACK = "back";
    public static final String RESULT_CANCEL = "cancel";
    public static final String RESULT_VIEW = "view";
    public static final String RESULT_CHANGE = "change";
    public static final String RESULT_DELETE = "delete";
    public static final String RESULT_ADD = "add";
    public static final String RESULT_SEARCH = "search";
    public static final String RESULT_UPLOAD = "upload";
    public static final String RESULT_DOWNLOAD = "download";
    public static final String RESULT_FORECAST = "forecast";
    public static final String RESULT_AGREE = "agree";
    public static final String RESULT_REJECT = "reject";
    public static final String RESULT_STATUS = "status";
    public static final String RESULT_RESET = "reset";
    public static final String RESULT_APPLY = "apply";
    public static final String RESULT_ERROR = "error";
    public static final String RESULT_UPDATE = "update";
    public static final String RESULT_SUBMIT = "submit";
    /** Result Constants End */  
    
    /** Row data controling operation flag for BaseObject.rowOperationFlg*/
    public static final String ROW_DATA_CAN_MODIFY_TRUE = "1";
    public static final String ROW_DATA_CAN_MODIFY_FALSE = "-1";
    public static final String ROW_DATA_CAN_REVIEW_MODIFY_TRUE = "2";
    public static final String ROW_DATA_CAN_REVIEW_MODIFY_FALSE = "-2";
    public static final String ROW_DATA_CAN_APPROVE_TRUE = "3";
    public static final String ROW_DATA_CAN_APPROVE_FALSE = "-3";
}