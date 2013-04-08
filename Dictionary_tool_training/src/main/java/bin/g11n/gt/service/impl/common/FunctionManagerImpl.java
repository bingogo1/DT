package bin.g11n.gt.service.impl.common;

import java.util.List;
import java.util.Set;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.FunctionDao;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.User;
import bin.g11n.gt.service.common.FunctionManager;


/**
 * FunctionManagerImpl.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class FunctionManagerImpl extends BaseManager implements FunctionManager {
	private FunctionDao dao;
	
	public void setFunctionDao(FunctionDao dao) {
		this.dao = dao;
	}
	
	/**
	 * findFunctionByName 
	 *
	 * @param functionName
	 * @return  Function
	 */
	public Function findFunctionByName(final String functionName) {
		
		return dao.getFunctionByName(functionName);
	}
	
	/**
	 * findFunctionById 
	 *
	 * @param id
	 * @return  Function
	 */
	public Function findFunctionById(final Long id) {
		
		return dao.getFunctionById(id);
	}
	
	/**
	 * find all functions what are limited with access.
	 * getFunctionsWithAllAccessFalse 
	 *
	 * @return  List
	 */
	public List<Function> getFunctionsWithAllAccessFalse() {
		return dao.getFunctionsByFlag(Constants.ALL_ACCESS_FLG__BOOLEAN_DISABLED);
	}
	
	public Set<Function> getGrantableFunctionsByRoleId(Long roleId){
		return dao.getGrantableFunctionsByRoleId(roleId);
	}
	
	/**
	 * addFunction 
	 *
	 * @param function
	 * @param createOp
	 * @return  Function
	 */
	public Function addFunction(final Function function, final User createOp) {
		return dao.addFunction(function, createOp);
	}
	
	/**
	 * updateFunction 
	 *
	 * @param function
	 * @param updateOp
	 * @return  Function
	 */
	public Function updateFunction(final Function function, final User updateOp) {
		return dao.updateFunction(function, updateOp);
	}
	
	/**
	 * logically delete the object
	 * deleteFunction 
	 *
	 * @param function  void
	 */
	public void deleteFunction(final Function function, final User deleteOp) {
		dao.deleteFunction(function, deleteOp);
	}
	
	/**
	 * remove the object form physical database
	 * removeFunction 
	 *
	 * @param function  void
	 */
	public void removeFunction(final Function function) {
		dao.removeFunction(function);
	}
	

}
