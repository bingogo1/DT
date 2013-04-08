package bin.g11n.gt.common.exception;


public class DatabaseException extends GtException {

	/**
	 * Construct RollerException, wrapping existing throwable.
	 * @param s Error message.
	 * @param t Throwable to be wrapped
	 */
	public DatabaseException(String s,Throwable t)
	{
		super(s,t);
	}
	/**
	 * Construct RollerException, wrapping existing throwable.
	 * @param t Throwable to be wrapped
	 */
	public DatabaseException(Throwable t)
	{
        super(t);
	}
	/**
	 * Construct RollerException, with error message.
	 * @param s Error message
	 */
	public DatabaseException(String s)
	{
		super(s);
	}

}
