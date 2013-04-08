package bin.g11n.cil.common.logger.impl;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import bin.g11n.cil.common.logger.ILogger;

/**
 * <p>
 * Implementation of the <code>bin.g11n.cil.common.logger.ILogger</code>
 * interface that wraps the standard JDK logging mechanisms that were introduced
 * in the Merlin release (JDK 1.4).
 * </p>
 * 
 */

public class Jdk14Logger implements ILogger, Serializable {

	private final static String NULL_LEVEL_MSG = "No such level: null";

	/**
	 * This member variable simply ensures that any attempt to initialise this
	 * class in a pre-1.4 JVM will result in an ExceptionInInitializerError. It
	 * must not be private, as an optimising compiler could detect that it is
	 * not used and optimise it away.
	 */
	protected static final Level dummyLevel = Level.FINE;

	// ----------------------------------------------------------- Constructors

	/**
	 * Construct a named instance of this Logger.
	 * 
	 * @param name
	 *            Name of the logger to be constructed
	 */
	public Jdk14Logger(String name) {

		this.name = name;
//		logger = getLogger();

	}

	// ----------------------------------------------------- Instance Variables

	/**
	 * The underlying Logger implementation we are using.
	 */
	protected transient Logger logger = null;

	/**
	 * The name of the logger we are wrapping.
	 */
	protected String name = null;

	// --------------------------------------------------------- Public Methods
	/**
	 * Checks whether the log with specified level can be output.
	 * 
	 * @param eLevel
	 *            Log level.
	 * @return false: disabled, true: enabled.
	 */
	public boolean isEnabled(ELevel eLevel) {
		if (eLevel == null) {
			log(ILogger.ELevel.WARNING, NULL_LEVEL_MSG);
			return false;
		}
		switch (eLevel) {
		case FATAL:
			return getLogger().isLoggable(Level.SEVERE);
		case ERROR:
			return getLogger().isLoggable(Level.SEVERE);
		case WARNING:
			return getLogger().isLoggable(Level.WARNING);
		case INFO:
			return getLogger().isLoggable(Level.INFO);
		case DEBUG:
			return getLogger().isLoggable(Level.FINE);
		case TRACE:
			return getLogger().isLoggable(Level.FINEST);
		}
		return false;
	}

	/**
	 * Outputs log message with specified level.
	 * 
	 * @param ELevel
	 *            Enum object of log level
	 * @param String
	 *            Message string
	 */
	public void log(ELevel eLevel, String msg) {
		log(eLevel, msg, null);
	}

	/**
	 * Outputs log message with specified level and caught exception.
	 * 
	 * @param ELevel
	 *            Enum object of log level
	 * @param String
	 *            Message string
	 * @param Throwable
	 *            caught exception.
	 */
	public void log(ELevel eLevel, String msg, Throwable t) {
		if (eLevel == null) {
			log(ILogger.ELevel.WARNING, NULL_LEVEL_MSG);
			return;
		}
		switch (eLevel) {
		case FATAL:
			doLog(Level.SEVERE, msg, t);
			break;
		case ERROR:
			doLog(Level.SEVERE, msg, t);
			break;
		case WARNING:
			doLog(Level.WARNING, msg, t);
			break;
		case INFO:
			doLog(Level.INFO, msg, t);
			break;
		case DEBUG:
			doLog(Level.FINE, msg, t);
			break;
		case TRACE:
			doLog(Level.FINEST, msg, t);
		}
	}

	private void doLog(Level level, String msg, Throwable ex) {

		Logger logger = getLogger();
		if (logger.isLoggable(level)) {
			// Hack (?) to get the stack trace.
			Throwable dummyException = new Throwable();
			StackTraceElement locations[] = dummyException.getStackTrace();
			// Caller will be the third element
			String cname = "unknown";
			String method = "unknown";
			if (locations != null && locations.length > 2) {
				StackTraceElement caller = locations[2];
				cname = caller.getClassName();
				method = caller.getMethodName();
			}
			if (ex == null) {
				logger.logp(level, cname, method, msg);
			} else {
				logger.logp(level, cname, method, msg, ex);
			}
		}

	}

	/**
	 * Return the native Logger instance we are using.
	 */
	public Logger getLogger() {
		if (logger == null) {
			// Set "java.util.logging.config.file" property to use customized
			// config file.
			String configureFile = getJdk14ConfigFile();
			if (configureFile != null){
				System.setProperty("java.util.logging.config.file",
						configureFile);
			}
			createDirectoryForHandler(configureFile);
			logger = Logger.getLogger(name);

		}
		return (logger);
	}

	/**
	 * Finds user's configuration file in the class path. If does not exist, 
	 * default configuration file of CIL: "bin.g11n.cil.jdklogging_default.properties", 
	 * 
	 * @return
	 */
	private String getJdk14ConfigFile() {
		String[] allClassPathes = System.getProperty("java.class.path").split(
				";");
		//Get user's configuration of jdk14logger.
		File propFile = new File("logging.properties");
		//If the jdk14 configuration file can be found from the root of classpath, return it.
		if (propFile.exists()) {
			// Found user's configuration file in the class path.
			return propFile.getPath();
		}
		//In case it is in other classpath. find it.
		for (String tmpPath : allClassPathes) {
			tmpPath = tmpPath + java.io.File.separator
					+ "logging.properties";
			propFile = new File(tmpPath);
			if (propFile.exists()) {
				// Found user's configuration file in the class path.
				return tmpPath;
			}
		}
		// If user doesn't define configuration file for the jdk14logger, uses
		// default configuration file of CIL. "bin.g11n.cil.jdklogging_default.properties"
		for (String tmpPath : allClassPathes) {
			tmpPath = tmpPath + java.io.File.separator + "com"
					+ java.io.File.separator + "hp" + java.io.File.separator
					+ "g11n" + java.io.File.separator + "cil"
					+ java.io.File.separator + "jdklogging_default.properties";
			propFile = new File(tmpPath);
			if (propFile.exists()) {
				// Found user's configuration file in the class path.
				return tmpPath;
			}
		}
    	//Find in the jar file.
    	URL urlOfJar = ClassLoader.getSystemResource("com/hp/g11n/cil/jdklogging_default.properties");
    	if (urlOfJar != null) {
			// Found user's configuration file in the class path.
			return urlOfJar.getPath();
		}

		return null;
	}
	

	private void createDirectoryForHandler(String fname){
        File logsDir = new File("logs");

        if (!logsDir.exists())
        {
            if (!logsDir.mkdirs())
            {
                System.out.println("Fail to create logs directory: " + logsDir.getAbsolutePath());
            }
        }

	}
}
