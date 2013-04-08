package bin.g11n.cil;

import java.util.HashMap;

/**
 * @author guobin
 *
 */
public interface ICommonInfra {
	
	/**
	 * Get instance of class implementing requested interface
	 * @param infClass requested interface
	 * @return instance of requested interface or <code>null</code>
	 */
	public Object  getInterface(Class<?> infClass);

}
