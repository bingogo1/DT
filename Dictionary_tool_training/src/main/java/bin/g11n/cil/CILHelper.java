package bin.g11n.cil;

import bin.g11n.cil.bundle.IG11nResourceBundle;

/**
 * The helper class of CIL utilitis. By create a instance of it, 
 * the client can get an instance of specified utility interface.
 * 
 * 
 * @author guobin
 *
 */
public class CILHelper {
	
	// helper class for interface keeping
	protected class	InterfaceContainer<I>
	{
		private I inst = null;
		// TODO: solve this terrible solution with "Class clz" 
		public I	getInstance(Class clz)
		{
			if(inst == null)
				inst = (I)infra.getInterface(clz);
			
			return inst;
		}
	}
	
	protected ICommonInfra infra = null;
	public CILHelper(ICommonInfra infra)
	{
		 this.infra = infra;
	}

		//Step 2-1. Defines interface variables. 
	 protected InterfaceContainer<IG11nResourceBundle> resourceBundle = new InterfaceContainer<IG11nResourceBundle>();

	//Step 2-2. Defines interface getters. 

	 /**
	  * Get instance of class implementing IG11nResourceBundle 
	  * @return instance of class implementing IG11nResourceBundle
	  */
	 public IG11nResourceBundle getResourceBundle(){
		 return resourceBundle.getInstance(IG11nResourceBundle.class); 
	 }



}
