package bin.g11n.gt.common.exception;


/**
 * An exception that is thrown by classes wanting to trap unique 
 * constraint violations.  This is used to wrap Spring's 
 * DataIntegrityViolationException so it's checked in the web layer.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UserExistsException extends Exception {
	private static final long serialVersionUID = 4050482305178810162L;

	/**
     * Constructor for UserExistsException.
     *
     * @param message exception message
     */
    public UserExistsException(String message) {
        super(message);
    }
}
