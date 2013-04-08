package bin.g11n.cil;

import java.util.Dictionary;
import java.util.Hashtable;

public class CommonInfra implements ICommonInfra {

	protected class ImplPare
	{
		private Class<?>	cls;
		private Object		inst = null;
		public ImplPare(Class<?>	clazz) throws NullPointerException 
		{
			cls = clazz;
			if(cls == null)
				throw new NullPointerException();
		}
		
		public	Object getInstance(ICommonInfra infra) throws CilInstantiationException
		{
			if(inst == null)
			{
				// example: only first construction will be synchronized
				synchronized(this)
				{
					try {
						inst = cls.newInstance();
					} catch (Throwable t) {
						// example: actually we can catch here 2 or more exceptions, but client of the
						// method should see only one - our CilInstantiationException 
						throw new CilInstantiationException("Cannot instantiate " + cls.getName(), t);
					}
					
					if(inst instanceof Implementor)
					{
						((Implementor)inst).setCommonInfra(infra);	
					}
					else
						throw new CilInstantiationException("Class " + cls.getName() + " should be inherited from bin.g11n.cil.Implementor");
				}
			}
			return inst;
		}
	}
	
	protected Dictionary<Class, ImplPare>	map = new Hashtable<Class, ImplPare>(); 

	public Object getInterface(Class<?> infClass) {
		Object result = null;
		ImplPare pare = map.get(infClass);
		if(pare != null)
		{
			try {
				result = pare.getInstance(this);
			} catch (CilInstantiationException e) {
			}
		}
		return result;
	}

	/**
	 * Add mapping of interface class to implementor class
	 * @param ifs class of interface
	 * @param impl class of implementor
	 */
	public	void	addInterface(Class ifs, Class impl)
	{
		if(ifs == null || impl == null)
			throw new NullPointerException();

		map.put(ifs, new ImplPare(impl));
	}
}
