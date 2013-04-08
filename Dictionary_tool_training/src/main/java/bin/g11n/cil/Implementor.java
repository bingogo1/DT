package bin.g11n.cil;

/**
 * The base class of all abstract class of utilities.
 * 
 * @author guobin
 * 
 */
public abstract class Implementor {
	public Implementor() {
	}

	private ICommonInfra infra = null;
	protected  void setCommonInfra(ICommonInfra infra)
	{
		this.infra = infra;
	}
	
	protected ICommonInfra getCommonInfra()
	{
		return infra;
	}
}
