package bin.g11n.gt.service.common;

import java.util.List;
import java.util.Set;

import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.User;

/**
 * function business service
 * FunctionManager.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface FunctionManager extends Manager {

	/**
	 * find all functions what are limited with access.
	 * getFunctionsWithAllAccessFalse 
	 *
	 * @return  List
	 */
	public List<Function> getFunctionsWithAllAccessFalse();
	public Set<Function> getGrantableFunctionsByRoleId(Long roleId);
    
    public abstract Function findFunctionByName(final String functionName);

    public abstract Function findFunctionById(final Long id);

    public abstract Function addFunction(final Function function, final User createOp);

    public abstract Function updateFunction(final Function function, final User updateOp);

    public abstract void deleteFunction(final Function function, final User deleteOp);

    public abstract void removeFunction(final Function function);


}
