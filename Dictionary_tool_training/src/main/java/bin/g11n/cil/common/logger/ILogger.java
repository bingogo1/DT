package bin.g11n.cil.common.logger;

/**
 * <p>A simple logging interface abstracting logging APIs.  In order to be
 * instantiated successfully by {@link G11nLogFactory}, classes that implement
 * this interface must have a constructor that takes a single String
 * parameter representing the "name" of this ILogger.</p>
 *
 * <p> The six logging levels used by <code>ILogger</code> are (in order):
 * <ol>
 * <li>trace (the least serious)</li>
 * <li>debug</li>
 * <li>info</li>
 * <li>warn</li>
 * <li>error</li>
 * <li>fatal (the most serious)</li>
 * </ol>
 * The mapping of these log levels to the concepts used by the underlying
 * logging system is implementation dependent.
 * The implemention should ensure, though, that this ordering behaves
 * as expected.</p>
 *
 * <p>Performance is often a logging concern.
 * By examining the appropriate property,
 * a component can avoid expensive operations (producing information
 * to be logged).</p>
 *
 * <p> For example,
 * <code><pre>
 *    if (log.isDebugEnabled()) {
 *        ... do something expensive ...
 *        log.debug(theResult);
 *    }
 * </pre></code>
 * </p>
 *
 * <p>Configuration of the underlying logging system will generally be done
 * external to the Logging APIs, through whatever mechanism is supported by
 * that system.</p>
 *
 */
public interface ILogger {
	/**
	 * ILogger level.
	 *
	 */
	public enum ELevel {
		OFF, FATAL, ERROR, WARNING, INFO, DEBUG, TRACE, ALL
	}
	
	/**
	 * Checks whether the log with specified level can be output.
	 * 
	 * @param eLevel Log level.
	 * @return false: disabled, true: enabled.
	 */
	public boolean isEnabled(ELevel eLevel);

    /** 
     * Outputs log message with specified level.
	 *
	 * @param ELevel Enum object of log level
	 * @param String Message string
	 */
	public void log(ELevel eLevel, String msg);

	/** 
	 * Outputs log message with specified level and caught exception.
	 *
	 * @param ELevel Enum object of log level
	 * @param String Message string
	 * @param Throwable caught exception.
	 */
	public void log(ELevel eLevel, String msg, Throwable t);
}
