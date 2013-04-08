package bin.g11n.cil.common.logger.impl;


import java.io.Serializable;

import bin.g11n.cil.common.logger.ILogger;


/**
 * <p>Trivial implementation of ILogger that throws away all messages.  No
 * configurable system properties are supported.</p>
 *
 */
public class NoOpLog implements ILogger, Serializable {

    /** Convenience constructor */
    public NoOpLog() { }
    /** Base constructor */
    public NoOpLog(String name) { }
    
	/**
	 * Do nothing.
	 * 
	 * @param eLevel Log level.
	 * @return false: disabled, true: enabled.
	 */
	 public boolean isEnabled(ELevel eLevel){
		 return false;
	 }

    /** 
	 * Do nothing.
	 *
	 * @param ELevel Enum object of log level
	 * @param String Message string
	 */
	public void log(ELevel eLevel, String msg){}

    /** 
	 * Do nothing.
	 *
	 * @param ELevel Enum object of log level
	 * @param String Message string
	 * @param Throwable caught exception.
	 */
	 public void log(ELevel eLevel, String msg, Throwable t){}

}
