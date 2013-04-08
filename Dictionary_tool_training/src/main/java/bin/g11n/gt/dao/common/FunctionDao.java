package bin.g11n.gt.dao.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.User;

/**
 * FunctionDao.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface FunctionDao extends GenericDao<Function, Long> {
//  public abstract SearchResult searchFunction(SearchQuery cond);

	public abstract Collection getFunctionsByRoles(Collection granted);

	public abstract List getFunctionsByFlag(boolean allAccessFlg);

	public abstract Set<Function> getGrantableFunctionsByRoleId(Long roleId);

    public abstract Function getFunctionByName(final String functionName);

    public abstract Function getFunctionByUrl(final String functionUrl);

    public abstract Function getFunctionById(final Long id);

    public abstract Function addFunction(final Function function, final User createOp);

    public abstract Function updateFunction(final Function function, final User updateOp);

    public abstract void deleteFunction(final Function function, final User deleteOp);

    public abstract void removeFunction(final Function function);

	
}
