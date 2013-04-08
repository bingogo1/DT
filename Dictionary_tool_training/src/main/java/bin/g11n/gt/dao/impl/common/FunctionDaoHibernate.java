package bin.g11n.gt.dao.impl.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.FunctionDao;
import bin.g11n.gt.dao.common.IBaseDao;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.security.cache.FunctionCache;
import bin.g11n.gt.security.cache.info.GrantedFunction;
import bin.g11n.gt.security.cache.info.GrantedFunctionImpl;
import bin.g11n.gt.security.cache.info.RoleByNameCache;


/**
 * FunctionDaoHibernate.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class FunctionDaoHibernate extends GenericDaoHibernate<Function, Long> implements FunctionDao {
    private IBaseDao baseDao;
    private FunctionCache cache;
    private RoleByNameCache roleCache;
    
    

    /**
     *  Constructor 
     *  return_type
     */
    public FunctionDaoHibernate() {
        super(Function.class);
    }

	/**
	 * getFunctionsByRoles 
	 *
	 * @param granted the GrantedAuthority element.
	 * @return  Collection the GrantedFunction collection 
	 */
	public Collection getFunctionsByRoles(Collection granted) {
		Set set = new HashSet();
		if(null == granted) throw new IllegalArgumentException("Granted Roles cannot be null");
        
		for(Iterator it = granted.iterator();it.hasNext();){
            
            GrantedAuthority grantedAuthority = (GrantedAuthority)it.next();
            Role  role = roleCache.getRoleByRoleNameCache(grantedAuthority.getAuthority()); //
            if(role == null){
            	//get the role object from db by its key and key value. here the authortiy is the function 
            	role = (Role)baseDao.loadByKey(Role.class, "name", grantedAuthority.getAuthority());
            	if (role != null){
            		roleCache.putRoleInCache(role);
            	}
            	
            }
            GrantedFunction[] grantedFunctions = cache.getFunctionFromCache(role.getName());
            
            if(grantedFunctions == null){
            	
            	Set functions = role.getFunctions();
    			for(Iterator it2 = functions.iterator();it2.hasNext();){	
                    Function function = (Function)it2.next();
                    GrantedFunction grantedFunction = new GrantedFunctionImpl(function.getName());
    				set.add(  grantedFunction  ); 
    			}
                 
    			grantedFunctions = (GrantedFunction[]) set.toArray(new GrantedFunction[0]);
            	if (role != null && grantedFunctions != null){
            		cache.putFuncitonInCache(role.getName(),grantedFunctions);
            	}
            }
            
            for(int i = 0 ; i < grantedFunctions.length; i++){
            	 GrantedFunction grantedFunction = grantedFunctions[i];
            	 set.add(grantedFunction);
            }
		}
       
		return set;
	}

	/**
	 * getFunctionsByFlag 
	 *
	 * @param allAccessFlg the Access flag.
	 * @return  List the search result collection 
	 */
	public List getFunctionsByFlag(final boolean allAccessFlg) {
		return getHibernateTemplate().find("from Function function where function.allAccessFlg = ?  ",	allAccessFlg);
	}

	/**
	 * getFunctionByName 
	 *
	 * @param functionName
	 * @return  Function
	 */
	public Function getFunctionByName(final String functionName) {
		
		List list = getHibernateTemplate().find("from Function function where function.name = ? ", functionName);
		if (list.isEmpty()) return null;
		else
			return (Function)list.get(0);
	}
	
	/**
	 * getFunctionByUrl 
	 *
	 * @param functionUrl
	 * @return  Function
	 */
	public Function getFunctionByUrl(final String functionUrl) {
		
		List list = getHibernateTemplate().find("from Function function where function.url = ? ", functionUrl);
		if (list.isEmpty()) return null;
		else
			return (Function)list.get(0);
	}
	
	/**
	 * getFunctionById 
	 *
	 * @param id
	 * @return  Function
	 */
	public Function getFunctionById(final Long id) {
		
		return (Function) this.getHibernateTemplate().load(Function.class, id);
	}
	
	/**
	 * find all functions what are limited with access.
	 * getFunctionsWithAllAccessFalse 
	 *
	 * @return  List
	 */
	public Set<Function> getGrantableFunctionsByRoleId(Long roleId) {
		Set<Function> functions = new HashSet<Function>();
		String hql = "FROM Function function LEFT JOIN FETCH function.roles role "
			+ " WHERE role.id =:id";
		List queryList = this.getSession().createQuery(hql).setLong("id", roleId).list();
	       if (queryList != null && queryList.size() > 0){
	            Iterator roleIte = queryList.iterator();
	            while (roleIte.hasNext()){
	            	functions.add((Function)roleIte.next());
	            }
	        }
		return functions;
	}
	
	/**
	 * addFunction 
	 *
	 * @param function
	 * @param createOp
	 * @return  Function
	 */
	public Function addFunction(final Function function, final User createOp) {
		//set common fields
		getHibernateTemplate().save(function);
		return function;
	}
	
	/**
	 * updateFunction 
	 *
	 * @param function
	 * @param updateOp
	 * @return  Function
	 */
	public Function updateFunction(final Function function, final User updateOp) {
		//set common fields
		getHibernateTemplate().update(function);
		return function;
	}
	
	/**
	 * logically delete the object
	 * deleteFunction 
	 *
	 * @param function  void
	 */
	public void deleteFunction(final Function function, final User deleteOp) {

		getHibernateTemplate().update(function);
	}
	
	/**
	 * remove the object form physical database
	 * removeFunction 
	 *
	 * @param function  void
	 */
	public void removeFunction(final Function function) {
		getHibernateTemplate().delete(function);
	}
	
	public RoleByNameCache getRoleCache() {
		return roleCache;
	}

	public void setRoleCache(RoleByNameCache roleCache) {
		this.roleCache = roleCache;
	}

	public FunctionCache getCache() {
		return cache;
	}

	public void setCache(FunctionCache cache) {
		this.cache = cache;
	}

	public IBaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }

  
}
