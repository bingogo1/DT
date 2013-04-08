package bin.g11n.gt.dao.impl.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.RoleDao;
import bin.g11n.gt.model.Function;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.util.StringUtil;



/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 *
 * RoleDaoHibernate.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {

    public RoleDaoHibernate() {
        super(Role.class);
    }

	/**
	 * getRoleByName 
	 *
	 * @param roleName
	 * @return  Role
	 */
	public Role getRoleByName(final String roleName) {
		
		List list = getHibernateTemplate().find(
				"  FROM Role role " +
				" WHERE role.name = ? " +
				"   AND role.deleteFlg = ? ", 
				new Object[]{roleName, Constants.DELETE_FLG_NORMAL});
		if (list.isEmpty()) return null;
		else
			return (Role)list.get(0);
	}
	
    /**
     * Gets role list based on rolename list
     * @param rolenames the rolename list
     * @return roleList populated role object list
     */
    public List<Role> getRolesByNames(List<String> rolenames){
		// get query statement
		String hql = "  FROM Role role "
				+ " WHERE role.deleteFlg =:deleteFlg AND role.name IN (:roleNames)";
		 //excute query
		 return getSession().createQuery(hql).setString("deleteFlg", Constants.DELETE_FLG_NORMAL)
				.setParameterList("roleNames", rolenames.toArray()).list();
    }
    
    
    /**
	 * getRoleById 
	 *
	 * @param id
	 * @return  Role
	 */
	public Role getRoleById(final Long id) {
		
		return (Role) this.getHibernateTemplate().load(Role.class, id);
	}
	
	public Role findRoleById(Long id)
	{
		String hql = "from Role role where role.deleteFlg=? and role.id=?";
		
		return (Role) getSession().createQuery(hql).setParameter(0, Constants.DELETE_FLG_NORMAL)
													.setParameter(1, id).uniqueResult();
	}
	
	/**
	 * find all roles what have not been deleted logically
	 * getRoles 
	 *
	 * @return  List
	 */
	public List getRoles() {
		return getHibernateTemplate().find(
				"  FROM Role role " +
				" WHERE role.deleteFlg = ? ", 
				Constants.DELETE_FLG_NORMAL);
	}
	
	/**
	 * find roles by function.
	 * it is used to get role Set when the function is from different session.
	 * Note: these roles only include the system maintenance roles defined in the z_role_function
	 *  
	 * getRolesByFunction 
	 *
	 * @return  List
	 */
	public Set<Role> getRolesByFunction(Function function) {
		Set<Role> returnSet = new HashSet<Role>();
        List queryList = getHibernateTemplate().find(
        		"  FROM Role role " +
        		" WHERE role.deleteFlg = ? " +
        		"   AND role.functions.name = ? ", 
				new Object[]{Constants.DELETE_FLG_NORMAL, function.getName()});
        if (queryList != null && queryList.size() > 0){
            Iterator roleIte = queryList.iterator();
            while (roleIte.hasNext()){
            	returnSet.add((Role)roleIte.next());
            }
        }

        return returnSet;
	}
	
	/**
	 * addRole 
	 *
	 * @param role
	 * @param createOp
	 * @return  Role
	 */
	public Role addRole(final Role role, final User createOp) {
		//set common fields
		role.setDeleteFlg(Constants.DELETE_FLG_NORMAL);
		
		getHibernateTemplate().save(role);
		return role;
	}
	
	/**
	 * updateRole 
	 *
	 * @param role
	 * @param updateOp
	 * @return  Role
	 */
	public Role updateRole(final Role role, final User updateOp) {
		//set common fields
		getHibernateTemplate().update(role);
		return role;
	}
	
	/**
	 * logically delete the object
	 * deleteRole 
	 *
	 * @param role  void
	 */
	public void deleteRole(final Role role, final User deleteOp) {
		//set common fields
		role.setDeleteFlg(Constants.DELETE_FLG_DELETED);

		getHibernateTemplate().update(role);
	}
	
    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
	 * removeRole 
	 *
	 * @param roleName  roleName
	 */
	public void removeRole(final String roleName) {
        Object role = getRoleByName(roleName);
        getHibernateTemplate().delete(role);
	}
	

	/**
	 * remove the object form physical database
	 * removeRole 
	 *
	 * @param role  void
	 */
	public void removeRole(final Role role) {
		getHibernateTemplate().delete(role); 
	}

	public Object[] searchRoleList(Role searchCondition, int from, int size) {
		List<Object> paramsList = new ArrayList<Object>();
		List<Type> parmTypeList = new ArrayList<Type>();
		StringBuffer strSql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		
		strSql.append("From Role role where role.deleteFlg <> ? ");
		countSql.append("SELECT COUNT(role.id) ");
		countSql.append("From Role role where role.deleteFlg <> ? ");
		
		paramsList.add(Constants.DELETE_FLG_DELETED);
		parmTypeList.add((Type) Hibernate.STRING);
		
		if (StringUtil.isNotBlank(searchCondition.getName())) {
			strSql.append(" and role.name like ? ");
			countSql.append(" and role.name like ? ");
			paramsList.add("%" + searchCondition.getName() + "%");
			parmTypeList.add((Type) Hibernate.STRING);
		}
		
//		strSql.append("ORDER BY role.id ");
		return new Object[]{getSession().createQuery(countSql.toString()).setParameters(
				paramsList.toArray(), parmTypeList.toArray(new Type[] {})).uniqueResult(),
				getSession().createQuery(strSql.toString()).setParameters(
				paramsList.toArray(), parmTypeList.toArray(new Type[] {}))
				.setFirstResult(from).setMaxResults(size).list()};
	}
	
}
