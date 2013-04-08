package bin.g11n.cil.bundle;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;

public interface IG11nResourceBundle {

	/**
	 * Implementation of G11nResourceBundle.getKeys.
	 */
	public Enumeration<String> getKeys();

	/**
	 * Gets a resource bundle using the specified base name, the default locale,
	 * and the caller's class loader. Calling this method is equivalent to
	 * calling getBundle(baseName, Locale.getDefault(),
	 * this.getClass().getClassLoader()), except that getClassLoader() is run
	 * with the security privileges of IG11nResourceBundle.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified properties file
	 *            name with package name. For example: "bin.g11n.cil.cilResource" is specified 
	 *            the file of "/src/resources/com/hp/g11n/cil/cilResource.properties".
	 * @exception java.lang.NullPointerException
	 *                if <code>baseName</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no resource bundle for the specified base name can be
	 *                found
	 * @return a resource bundle for the given base name and the default locale
	 */
	public IG11nResourceBundle getBundle(String baseName);

	/**
	 * Gets a resource bundle using the specified base name and locale, and the
	 * caller's class loader. Calling this method is equivalent to calling
	 * getBundle(baseName, locale, this.getClass().getClassLoader()), except
	 * that getClassLoader() is run with the security privileges of
	 * IG11nResourceBundle.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified properties file
	 *            name with package name. For example: "bin.g11n.cil.cilResource" is specified 
	 *            the file of "/src/resources/com/hp/g11n/cil/cilResource.properties".
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @exception java.lang.NullPointerException
	 *                if <code>baseName</code> or <code>locale</code> is
	 *                <code>null</code>
	 * @exception MissingResourceException
	 *                if no resource bundle for the specified base name can be
	 *                found
	 * @return a resource bundle for the given base name and locale
	 */
	public IG11nResourceBundle getBundle(String baseName, Locale locale);

	/**
	 * Gets a resource bundle using the specified base name, locale, and class
	 * loader.
	 * 
	 * <p>
	 * Conceptually, <code>getBundle</code> uses the following strategy for
	 * locating and instantiating resource bundles:
	 * <p>
	 * <code>getBundle</code> uses the base name, the specified locale, and
	 * Locale.getDefault to generate a
	 * sequence of <em>candidate bundle names</em>. If the specified locale's
	 * language, country, and variant are all empty strings, then the base name
	 * is the only candidate bundle name. 
	 * Otherwise, the following sequence is
	 * generated from the attribute values of the specified locale (language1,
	 * country1, and variant1) and of the default locale (language2, country2,
	 * and variant2):
	 * <ul>
	 * <li> baseName + "_" + language1 + "_" + country1 + "_" + variant1
	 * <li> baseName + "_" + language1 + "_" + country1
	 * <li> baseName + "_" + language1
	 * <li> baseName + "_" + language2 + "_" + country2 + "_" + variant2
	 * <li> baseName + "_" + language2 + "_" + country2
	 * <li> baseName + "_" + language2
	 * <li> baseName
	 * </ul>
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified properties file
	 *            name with package name. For example: "bin.g11n.cil.cilResource" is specified 
	 *            the file of "/src/resources/com/hp/g11n/cil/cilResource.properties".
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @param loader
	 *            the class loader from which to load the resource bundle
	 * @exception java.lang.NullPointerException
	 *                if <code>baseName</code>, <code>locale</code>, or
	 *                <code>loader</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no resource bundle for the specified base name can be
	 *                found
	 * @return a resource bundle for the given base name and locale
	 */
	public IG11nResourceBundle getBundle(String baseName, Locale locale,
			ClassLoader loader);

	/**
	 * Gets a string for the given key from this resource bundle or one of its
	 * parents. Calling this method is equivalent to calling <blockquote>
	 * <code>(String) {@link #getObject(java.lang.String) getObject}(key)</code>.
	 * </blockquote>
	 * 
	 * @param key
	 *            the key for the desired string
	 * @exception NullPointerException
	 *                if <code>key</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no object for the given key can be found
	 * @exception ClassCastException
	 *                if the object found for the given key is not a string
	 * @return the string for the given key
	 */
	public String getString(String key);
	
	/**
	 * generate text with one parameter
	 * 
	 * @param key
	 *            message key
	 * @param arg
	 *            parameter value
	 * @return String message
	 */
	public String getString(String key, String arg);

	/**
	 * generate text with multiple parameter
	 * 
	 * @param key
	 *            message key
	 * @param messageArgs
	 *            parameter value
	 * @return String message
	 */
	public String getString(String key, Object[] messageArgs);

	/**
	 * Gets a string array for the given key from this resource bundle or one of
	 * its parents. Calling this method is equivalent to calling <blockquote>
	 * <code>(String[]) {@link #getObject(java.lang.String) getObject}(key)</code>.
	 * </blockquote>
	 * 
	 * @param key
	 *            the key for the desired string array
	 * @exception NullPointerException
	 *                if <code>key</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no object for the given key can be found
	 * @exception ClassCastException
	 *                if the object found for the given key is not a string
	 *                array
	 * @return the string array for the given key
	 */
	public String[] getStringArray(String key);

	/**
	 * Gets an object for the given key from this resource bundle or one of its
	 * parents. This method first tries to obtain the object from this resource
	 * bundle using {@link #handleGetObject(java.lang.String) handleGetObject}.
	 * If not successful, and the parent resource bundle is not null, it calls
	 * the parent's <code>getObject</code> method. If still not successful, it
	 * throws a MissingResourceException.
	 * 
	 * @param key
	 *            the key for the desired object
	 * @exception NullPointerException
	 *                if <code>key</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no object for the given key can be found
	 * @return the object for the given key
	 */
	public Object getObject(String key);

	/**
	 * Returns the locale of this resource bundle. This method can be used after
	 * a call to getBundle() to determine whether the resource bundle returned
	 * really corresponds to the requested locale or is a fallback.
	 * 
	 * @return the locale of this resource bundle
	 */
	public Locale getLocale();
}
