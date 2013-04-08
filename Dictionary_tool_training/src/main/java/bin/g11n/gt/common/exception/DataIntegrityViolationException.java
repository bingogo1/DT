package bin.g11n.gt.common.exception;

public class DataIntegrityViolationException extends DatabaseException {

	/**
	 * Construct RollerException, wrapping existing throwable.
	 * @param s Error message.
	 * @param t Throwable to be wrapped
	 */
	public DataIntegrityViolationException(String s,Throwable t)
	{
		super(s,t);
	}
	/**
	 * Construct RollerException, wrapping existing throwable.
	 * @param t Throwable to be wrapped
	 */
	public DataIntegrityViolationException(Throwable t)
	{
        super(t);
	}
	/**
	 * Construct RollerException, with error message.
	 * @param s Error message
	 */
	public DataIntegrityViolationException(String s)
	{
		super(s);
	}

}
