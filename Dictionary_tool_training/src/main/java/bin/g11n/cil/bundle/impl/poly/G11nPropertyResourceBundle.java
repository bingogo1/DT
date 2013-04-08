package bin.g11n.cil.bundle.impl.poly;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import bin.g11n.cil.bundle.impl.G11nResourceBundle;

/**
 * 
 * @author guobin
 *
 */
public class G11nPropertyResourceBundle extends G11nResourceBundle {
	private Map lookup;

	public G11nPropertyResourceBundle() {
	}

	/**
	 * Creates a property resource bundle.
	 * 
	 * @param stream
	 *            property file to read from.
	 */
	public G11nPropertyResourceBundle(InputStream stream) throws IOException {
		Properties properties = new Properties();
		properties.load(stream);
		lookup = new HashMap(properties);
	}

	/**
	 * Implementation of G11nResourceBundle.getKeys.
	 */
	public Enumeration<String> getKeys() {
		// G11nResourceBundle parent = resourceBundle;
		return new G11nResourceBundleEnumeration(lookup.keySet(),
				(parent != null) ? parent.getKeys() : null);
	}

	// Implements java.util.G11nResourceBundle.handleGetObject; inherits javadoc
	// specification.
	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}
		return lookup.get(key);
	}

}
