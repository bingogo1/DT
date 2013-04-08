 package bin.g11n.gt.common;



/**
 * Role constant values. 
 * The content of every ROLE_xxxx is mapped to the value of Z_ROLE.NAME
 *
 * RoleConstants.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class RoleConstants {

    /* ROLE_DEFAULT default role, has all permits with accessflg=1 */
	public static final String ROLE_DEFAULT = "ROLE_DEFAULT";
    /* ROLE_ADMIN super admin of role, has all permits*/
	public static final String ROLE_ADMIN = "ROLE_SUPER_ADMIN";
	
    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "user";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";
    
    public static final String ROLE_LPM = "ROLE_LPM";
    public static final String ROLE_GPM = "ROLE_GPM";
    public static final String ROLE_OTHER_CONTROLLER = "ROLE_OTHER_CONTROLLER";
    public static final String ROLE_TEAM_LEADER = "ROLE_TEAM_LEADER";
}