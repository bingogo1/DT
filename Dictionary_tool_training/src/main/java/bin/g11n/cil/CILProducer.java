package bin.g11n.cil;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import bin.g11n.cil.bundle.IG11nResourceBundle;
import bin.g11n.cil.bundle.impl.poly.G11nPropertyResourceBundle;

/**
 * 1. Configures default implementation classes for the interfaces of CIL. 
 * 2. Provides setInterface API for the
 * caller to set his own implementation class of interfaces.
 * 3. Provides a map to store and update the map of pares {interface, implementation} in the CIL.
 * 
 * @author guobin
 * 
 */
public class CILProducer {
	/**
	 * The map contains peer (infClass, implClass)
	 */
	protected Dictionary<Class, Class> infImplMap = new Hashtable<Class, Class>();

	/**
	 * Default constructor
	 */
	public CILProducer() {
		//Initiate the Map.
		setDefaultImpl();
	}

	/**
	 * Set a implementation class of a interface into the map.
	 * 
	 * @param inf Interface class.
	 * @param impl Implementation class.
	 */
	public void setInterface(Class ifs, Class impl) {
		if(ifs == null || impl == null)
			throw new NullPointerException();

		infImplMap.put(ifs, impl);
	}

	/**
	 * Creates an instance of class implementing ICommonInfra.
	 * @return instance of class implementing ICommonInfra
	 */
	// example: if we will copy entire map into CommonInfra, we can change
	// map of CommonInfra via CILProducer - it's potential bug
	public ICommonInfra createInfra() {
		CommonInfra infr = new CommonInfra();
		for(Enumeration<Class> en = infImplMap.keys(); en.hasMoreElements(); )
		{
			Class cls = en.nextElement();
			infr.addInterface(cls, infImplMap.get(cls));
		}
		return infr;
	}
	
	/**
	 * Put all default {key,value} into map.
	 */
	protected void setDefaultImpl(){
		//TODO This maybe can be improved.
		setInterface(IG11nResourceBundle.class, G11nPropertyResourceBundle.class);
	}
	
}
