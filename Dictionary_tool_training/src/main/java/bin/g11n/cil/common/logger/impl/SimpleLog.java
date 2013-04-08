package bin.g11n.cil.common.logger.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.LogConfigurationException;

/**
 * <p>Simple implementation of ILogger that sends all enabled log messages,
 * for all defined loggers, to System.err.  The following system properties
 * are supported to configure the behavior of this logger:</p>
 * <ul>
 * <li><code>bin.g11n.cil.common.logger.impl.SimpleLog.defaultlog</code> -
 *     Default logging detail level for all instances of SimpleLog.
 *     Must be one of ("trace", "debug", "info", "warn", "error", or "fatal").
 *     If not specified, defaults to "info". </li>
 * <li><code>bin.g11n.cil.common.logger.impl.SimpleLog.log.xxxxx</code> -
 *     Logging detail level for a SimpleLog instance named "xxxxx".
 *     Must be one of ("trace", "debug", "info", "warn", "error", or "fatal").
 *     If not specified, the default logging detail level is used.</li>
 * <li><code>bin.g11n.cil.common.logger.impl.SimpleLog.showlogname</code> -
 *     Set to <code>true</code> if you want the ILogger instance name to be
 *     included in output messages. Defaults to <code>false</code>.</li>
 * <li><code>bin.g11n.cil.common.logger.impl.SimpleLog.showShortLogname</code> -
 *     Set to <code>true</code> if you want the last component of the name to be
 *     included in output messages. Defaults to <code>true</code>.</li>
 * <li><code>bin.g11n.cil.common.logger.impl.SimpleLog.showdatetime</code> -
 *     Set to <code>true</code> if you want the current date and time
 *     to be included in output messages. Default is <code>false</code>.</li>
 * <li><code>bin.g11n.cil.common.logger.impl.SimpleLog.dateTimeFormat</code> -
 *     The date and time format to be used in the output messages.
 *     The pattern describing the date and time format is the same that is
 *     used in <code>java.text.SimpleDateFormat</code>. If the format is not
 *     specified or is invalid, the default format is used.
 *     The default format is <code>yyyy/MM/dd HH:mm:ss:SSS zzz</code>.</li>
 * </ul>
 *
 * <p>In addition to looking for system properties with the names specified
 * above, this implementation also checks for a class loader resource named
 * <code>"simplelog.properties"</code>, and includes any matching definitions
 * from this resource (if it exists).</p>
 *
 */
public class SimpleLog implements ILogger, Serializable {


    // ------------------------------------------------------- Class Attributes

    /** All system properties used by <code>SimpleLog</code> start with this */
    static protected final String systemPrefix =
        "bin.g11n.cil.common.logger.impl.SimpleLog.";

    /** Properties file resource name (simplelog.properties) used by <code>SimpleLog</code> */
    static protected final String simpleLogResource =
        "com/hp/g11n/cil/simplelog.properties";

    /** Properties loaded from simplelog.properties */
    static protected final Properties simpleLogProps = new Properties();

    /** The default format to use when formating dates */
    static protected final String DEFAULT_DATE_TIME_FORMAT =
        "yyyy/MM/dd HH:mm:ss:SSS zzz";

    /** Include the instance name in the log message? */
    static protected boolean showLogName = false;
    /** Include the short name ( last component ) of the logger in the log
     *  message. Defaults to true - otherwise we'll be lost in a flood of
     *  messages without knowing who sends them.
     */
    static protected boolean showShortName = true;
    /** Include the current time in the log message */
    static protected boolean showDateTime = false;
    /** The date and time format to use in the log message */
    static protected String dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;

	private final static String NULL_LEVEL_MSG = "No such level: null";
    /**
     * Used to format times.
     * <p>
     * Any code that accesses this object should first obtain a lock on it,
     * ie use synchronized(dateFormatter); this requirement was introduced
     * in 1.1.1 to fix an existing thread safety bug (SimpleDateFormat.format
     * is not thread-safe).
     */
    static protected DateFormat dateFormatter = null;

    // ---------------------------------------------------- ILogger ELevel Constants


    /** Enable all logging levels */
    public static final int LOG_LEVEL_ALL    = 0;
    /** "Trace" level logging. */
    public static final int LOG_LEVEL_TRACE  = 1;
    /** "Debug" level logging. */
    public static final int LOG_LEVEL_DEBUG  = 2;
    /** "Info" level logging. */
    public static final int LOG_LEVEL_INFO   = 3;
    /** "Warn" level logging. */
    public static final int LOG_LEVEL_WARN   = 4;
    /** "Error" level logging. */
    public static final int LOG_LEVEL_ERROR  = 5;
    /** "Fatal" level logging. */
    public static final int LOG_LEVEL_FATAL  = 6;
    /** Enable no logging levels */
    public static final int LOG_LEVEL_OFF    = 7;

    // ------------------------------------------------------------ Initializer

    private static String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
            ; // Ignore
        }
        return (prop == null) ? simpleLogProps.getProperty(name) : prop;
    }

    private static String getStringProperty(String name, String dephault) {
        String prop = getStringProperty(name);
        return (prop == null) ? dephault : prop;
    }

    private static boolean getBooleanProperty(String name, boolean dephault) {
        String prop = getStringProperty(name);
        return (prop == null) ? dephault : "true".equalsIgnoreCase(prop);
    }

    // Initialize class attributes.
    // Load properties file, if found.
    // Override with system properties.
    static {
        // Add props from the resource simplelog.properties
        InputStream in = getResourceAsStream(simpleLogResource);
        if(null != in) {
            try {
                simpleLogProps.load(in);
                in.close();
            } catch(java.io.IOException e) {
                // ignored
            }
        }

        showLogName = getBooleanProperty( systemPrefix + "showlogname", showLogName);
        showShortName = getBooleanProperty( systemPrefix + "showShortLogname", showShortName);
        showDateTime = getBooleanProperty( systemPrefix + "showdatetime", showDateTime);

        if(showDateTime) {
            dateTimeFormat = getStringProperty(systemPrefix + "dateTimeFormat",
                                               dateTimeFormat);
            try {
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            } catch(IllegalArgumentException e) {
                // If the format pattern is invalid - use the default format
                dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            }
        }
    }

    // ------------------------------------------------------------- Attributes

    /** The name of this simple log instance */
    protected String logName = null;
    /** The current log level */
    protected int currentLogLevel;
    /** The short name of this simple log instance */
    private String shortLogName = null;


    // ------------------------------------------------------------ Constructor

    /**
     * Construct a simple log with given name.
     *
     * @param name log name
     */
    public SimpleLog(String name) {

        logName = name;

        // Set initial log level
        // Used to be: set default log level to ERROR
        // IMHO it should be lower, but at least info ( costin ).
        setLevel(SimpleLog.LOG_LEVEL_INFO);

        // Set log level from properties
        String lvl = getStringProperty(systemPrefix + "log." + logName);
        int i = String.valueOf(name).lastIndexOf(".");
        while(null == lvl && i > -1) {
            name = name.substring(0,i);
            lvl = getStringProperty(systemPrefix + "log." + name);
            i = String.valueOf(name).lastIndexOf(".");
        }

        if(null == lvl) {
            lvl =  getStringProperty(systemPrefix + "defaultlog");
        }

        if("all".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_ALL);
        } else if("trace".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_TRACE);
        } else if("debug".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_DEBUG);
        } else if("info".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_INFO);
        } else if("warn".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_WARN);
        } else if("error".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_ERROR);
        } else if("fatal".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_FATAL);
        } else if("off".equalsIgnoreCase(lvl)) {
            setLevel(SimpleLog.LOG_LEVEL_OFF);
        }

    }


    // -------------------------------------------------------- Properties

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
			 case FATAL: return isLevelEnabled(SimpleLog.LOG_LEVEL_FATAL);
			 case ERROR: return isLevelEnabled(SimpleLog.LOG_LEVEL_ERROR);
			 case WARNING: return isLevelEnabled(SimpleLog.LOG_LEVEL_WARN);
			 case INFO: return isLevelEnabled(SimpleLog.LOG_LEVEL_INFO);
			 case DEBUG: return isLevelEnabled(SimpleLog.LOG_LEVEL_DEBUG);
			 case TRACE: return isLevelEnabled(SimpleLog.LOG_LEVEL_TRACE);
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
		 if (eLevel == null){
				log(ILogger.ELevel.WARNING, NULL_LEVEL_MSG);
				return;
			 }
		 int currentLevel = LOG_LEVEL_OFF;
		 switch (eLevel){
			 case FATAL: currentLevel = LOG_LEVEL_FATAL;
			 break;
			 case ERROR: currentLevel = LOG_LEVEL_ERROR;
			 break;
			 case WARNING: currentLevel = LOG_LEVEL_WARN;
			 break;
			 case INFO: currentLevel = LOG_LEVEL_INFO;
			 break;
			 case DEBUG: currentLevel = LOG_LEVEL_DEBUG;
			 break;
			 case TRACE: currentLevel = LOG_LEVEL_TRACE;
		 }
		 if (currentLevel >= this.currentLogLevel){
			 //If current level is set validly, output log, or els don't output log.
			 log(currentLevel, msg, t);
		 }
	}

    /**
     * <p> Set logging level. </p>
     *
     * @param currentLogLevel new logging level
     */
    public void setLevel(int currentLogLevel) {

        this.currentLogLevel = currentLogLevel;

    }


    /**
     * <p> Get logging level. </p>
     */
    public int getLevel() {

        return currentLogLevel;
    }


    // -------------------------------------------------------- Logging Methods


    /**
     * <p> Do the actual logging.
     * This method assembles the message
     * and then calls <code>write()</code> to cause it to be written.</p>
     *
     * @param type One of the LOG_LEVEL_XXX constants defining the log level
     * @param message The message itself (typically a String)
     * @param t The exception whose stack trace should be logged
     */
    protected void log(int type, Object message, Throwable t) {
        // Use a string buffer for better performance
        StringBuffer buf = new StringBuffer();

        // Append date-time if so configured
        if(showDateTime) {
            Date now = new Date();
            String dateText;
            synchronized(dateFormatter) {
                dateText = dateFormatter.format(now);
            }
            buf.append(dateText);
            buf.append(" ");
        }

        // Append a readable representation of the log level
        switch(type) {
            case SimpleLog.LOG_LEVEL_TRACE: buf.append("[TRACE] "); break;
            case SimpleLog.LOG_LEVEL_DEBUG: buf.append("[DEBUG] "); break;
            case SimpleLog.LOG_LEVEL_INFO:  buf.append("[INFO] ");  break;
            case SimpleLog.LOG_LEVEL_WARN:  buf.append("[WARN] ");  break;
            case SimpleLog.LOG_LEVEL_ERROR: buf.append("[ERROR] "); break;
            case SimpleLog.LOG_LEVEL_FATAL: buf.append("[FATAL] "); break;
        }

        // Append the name of the log instance if so configured
        if( showShortName) {
            if( shortLogName==null ) {
                // Cut all but the last component of the name for both styles
                shortLogName = logName.substring(logName.lastIndexOf(".") + 1);
                shortLogName =
                    shortLogName.substring(shortLogName.lastIndexOf("/") + 1);
            }
            buf.append(String.valueOf(shortLogName)).append(" - ");
        } else if(showLogName) {
            buf.append(String.valueOf(logName)).append(" - ");
        }

        // Append the message
        buf.append(String.valueOf(message));

        // Append stack trace if not null
        if(t != null) {
            buf.append(" <");
            buf.append(t.toString());
            buf.append(">");

            java.io.StringWriter sw= new java.io.StringWriter(1024);
            java.io.PrintWriter pw= new java.io.PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            buf.append(sw.toString());
        }

        // Print to the appropriate destination
        write(buf);

    }


    /**
     * <p>Write the content of the message accumulated in the specified
     * <code>StringBuffer</code> to the appropriate output destination.  The
     * default implementation writes to <code>System.err</code>.</p>
     *
     * @param buffer A <code>StringBuffer</code> containing the accumulated
     *  text to be logged
     */
    protected void write(StringBuffer buffer) {

        System.err.println(buffer.toString());

    }


    /**
     * Is the given log level currently enabled?
     *
     * @param logLevel is this level enabled?
     */
    protected boolean isLevelEnabled(int logLevel) {
        // log level are numerically ordered so can use simple numeric
        // comparison
        return (logLevel >= currentLogLevel);
    }


    // -------------------------------------------------------- ILogger Implementation


    /**
     * Return the thread context class loader if available.
     * Otherwise return null.
     *
     * The thread context class loader is available for JDK 1.2
     * or later, if certain security conditions are met.
     *
     * @exception LogConfigurationException if a suitable class loader
     * cannot be identified.
     */
    private static ClassLoader getContextClassLoader()
    {
        ClassLoader classLoader = null;

        if (classLoader == null) {
            try {
                // Are we running on a JDK 1.2 or later system?
                Method method = Thread.class.getMethod("getContextClassLoader",
                        (Class[]) null);

                // Get the thread context class loader (if there is one)
                try {
                    classLoader = (ClassLoader)method.invoke(Thread.currentThread(), 
                            (Class[]) null);
                } catch (IllegalAccessException e) {
                    ;  // ignore
                } catch (InvocationTargetException e) {
                    /**
                     * InvocationTargetException is thrown by 'invoke' when
                     * the method being invoked (getContextClassLoader) throws
                     * an exception.
                     *
                     * getContextClassLoader() throws SecurityException when
                     * the context class loader isn't an ancestor of the
                     * calling class's class loader, or if security
                     * permissions are restricted.
                     *
                     * In the first case (not related), we want to ignore and
                     * keep going.  We cannot help but also ignore the second
                     * with the logic below, but other calls elsewhere (to
                     * obtain a class loader) will trigger this exception where
                     * we can make a distinction.
                     */
                    if (e.getTargetException() instanceof SecurityException) {
                        ;  // ignore
                    } else {
                        // Capture 'e.getTargetException()' exception for details
                        // alternate: log 'e.getTargetException()', and pass back 'e'.
                        throw new LogConfigurationException
                            ("Unexpected InvocationTargetException", e.getTargetException());
                    }
                }
            } catch (NoSuchMethodException e) {
                // Assume we are running on JDK 1.1
                ;  // ignore
            }
        }

        if (classLoader == null) {
            classLoader = SimpleLog.class.getClassLoader();
        }

        // Return the selected class loader
        return classLoader;
    }

    private static InputStream getResourceAsStream(final String name)
    {
        return (InputStream)AccessController.doPrivileged(
            new PrivilegedAction() {
                public Object run() {
                    ClassLoader threadCL = getContextClassLoader();

                    if (threadCL != null) {
                        return threadCL.getResourceAsStream(name);
                    } else {
                        return ClassLoader.getSystemResourceAsStream(name);
                    }
                }
            });
    }
}

