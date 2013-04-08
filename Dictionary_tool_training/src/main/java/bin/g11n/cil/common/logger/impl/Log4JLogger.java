package bin.g11n.cil.common.logger.impl;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.Loader;

import bin.g11n.cil.common.logger.ILogger;

/**
 * Implementation of {@link ILogger} that maps directly to a
 * <strong>Logger</strong> for log4J version 1.2.
 * <p>
 * Initial configuration of the corresponding Logger instances should be done
 * in the usual manner, as outlined in the Log4J documentation.
 */

public class Log4JLogger implements ILogger, Serializable {

    // ------------------------------------------------------------- Attributes

    /** The fully qualified name of the Log4JLogger class. */
    private static String FQCN = null;
    
    /** ILogger to this logger */
    private transient Logger logger = null;

    /** Logger name */
    private String name = null;

    private static Priority traceLevel;
    
	private final static String NULL_LEVEL_MSG = "No such level: null";


    
    // ------------------------------------------------------------
    // Static Initializer.
    //
    // Note that this must come after the static variable declarations
    // otherwise initialiser expressions associated with those variables
    // will override any settings done here.
    //
    // Verify that log4j is available, and that it is version 1.2.
    // If an ExceptionInInitializerError is generated, then G11nLogFactoryImpl
    // will treat that as meaning that the appropriate underlying logging
    // library is just not present - if discovery is in progress then
    // discovery will continue.
    // ------------------------------------------------------------

    static {
        if (!Priority.class.isAssignableFrom(Level.class)) {
            // nope, this is log4j 1.3, so force an ExceptionInInitializerError
            throw new InstantiationError("Log4J 1.2 not available");
        }
        
        // Releases of log4j1.2 >= 1.2.12 have Priority.TRACE available, earlier
        // versions do not. If TRACE is not available, then we have to map
        // calls to ILogger.trace(...) onto the DEBUG level.
        
        try {
            traceLevel = (Priority) Level.class.getDeclaredField("TRACE").get(null);
        } catch(Exception ex) {
            // ok, trace not available
            traceLevel = Priority.DEBUG;
        }
    }

    
    // ------------------------------------------------------------ Constructor

//    public Log4JLogger() {
//    }
//

    /**
     * Base constructor.
     */
    public Log4JLogger(String name) {
        this.name = name;
//        this.logger = getLogger();
    }

//    /** 
//     * For use with a log4j factory.
//     */
//    public Log4JLogger(Logger logger ) {
//        if (logger == null) {
//            throw new IllegalArgumentException(
//                "Warning - null logger in constructor; possible log4j misconfiguration.");
//        }
//        this.name = logger.getName();
////        this.logger=logger;
//    }


	/**
	 * Checks whether the log with specified level can be output.
	 * 
	 * @param eLevel Log level.
	 * @return false: disabled, true: enabled.
	 */
	 public boolean isEnabled(ELevel eLevel){
		 if (eLevel == null){
				log(ILogger.ELevel.WARNING, NULL_LEVEL_MSG);
				return false;
		 }
		 switch (eLevel){
			 case FATAL: return getLogger().isEnabledFor(Level.FATAL);
			 case ERROR: return getLogger().isEnabledFor(Level.ERROR);
			 case WARNING: return getLogger().isEnabledFor(Level.WARN);
			 case INFO: return getLogger().isInfoEnabled();
			 case DEBUG: return getLogger().isDebugEnabled();
			 case TRACE: return getLogger().isEnabledFor(traceLevel);
		 }
		 return false;
	 }

    /** 
     * Outputs log message with specified level.
	 *
	 * @param ELevel Enum object of log level
	 * @param String Message string
	 */
	public void log(ELevel eLevel, String msg){
		 log(eLevel, msg, null); 
	 }

    /** 
     * Outputs log message with specified level and caught exception.
	 *
	 * @param ELevel Enum object of log level
	 * @param String Message string
	 * @param Throwable caught exception.
	 */
	 public void log(ELevel eLevel, String msg, Throwable t){
		 FQCN = Log4JLogger.class.getName();
		 if (eLevel == null){
			log(ILogger.ELevel.WARNING, NULL_LEVEL_MSG);
			return;
		 }
		 switch (eLevel){
			 case FATAL: getLogger().log(FQCN, org.apache.log4j.Level.FATAL, msg, t);
			 break;
			 case ERROR: getLogger().log(FQCN, org.apache.log4j.Level.ERROR, msg, t);
			 break;
			 case WARNING: getLogger().log(FQCN, org.apache.log4j.Level.WARN, msg, t);
			 break;
			 case INFO: getLogger().log(FQCN, org.apache.log4j.Level.INFO, msg, t);
			 break;
			 case DEBUG: getLogger().log(FQCN, org.apache.log4j.Level.DEBUG, msg, t);
			 break;
			 case TRACE: getLogger().log(FQCN, org.apache.log4j.Level.TRACE, msg, t);
		 }
	}

    /**
     * Return the native Logger instance we are using.
     */
    public Logger getLogger() {
        if (logger == null) {
			// Set "log4j.configuration" property to use customized
			// config file.
			if (Loader.getResource("log4j.xml") == null && 
					Loader.getResource("log4j.properties") == null){
				System.setProperty("log4j.configuration",
						"com/hp/g11n/cil/log4j_default.properties");
			}
			
            logger = Logger.getLogger(name);
        }
        return (this.logger);
    }

}
