package bin.g11n.gt.dao.impl.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.common.UserDao;
import bin.g11n.gt.model.Role;
import bin.g11n.gt.model.User;
import bin.g11n.gt.util.DateUtil;
import bin.g11n.gt.util.StringUtil;


/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * UserDaoHibernate.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {

    public UserDaoHibernate() {
        super(User.class);
    }

    /**
     * @see UserDao#getUsers()
     */
    @SuppressWarnings("unchecked")
	public List<User> getUsers() {
        return getHibernateTemplate().find("" +
        		"from User u where u.deleteFlg=? order by upper(u.username)",
        		 Constants.DELETE_FLG_NORMAL);
    }

		/**
		 * get the data list from db by search condition.
		 * 
		 * @param searchCondition
		 * @param from
		 * @param size
		 * @return Object[]{count, list}
		 */
		public Object[] searchUserList(User searchCondition, int from, int size){
			List<Object> paramsList = new ArrayList<Object>();
			List<Type> parmTypeList = new ArrayList<Type>();
			StringBuffer strSql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			strSql.append("From User user" 
			        + " LEFT JOIN FETCH user.buId AS bu"
					+ " where user.deleteFlg<> ? ");
			countSql.append("SELECT COUNT(user.id) ");
			countSql.append("From User user" 
			        + " LEFT JOIN user.buId AS bu"
					+ " where user.deleteFlg<> ? ");
			paramsList.add(Constants.DELETE_FLG_DELETED);
			parmTypeList.add((Type) Hibernate.STRING);
			if (searchCondition.getBuId() != null &&
					StringUtil.isNotBlank(searchCondition.getBuId().getBuName())) {
				strSql.append(" and bu.buName like ? ");
				countSql.append(" and bu.buName like ? ");
				paramsList.add(searchCondition.getBuId().getBuName() + "%");
				parmTypeList.add((Type) Hibernate.STRING);
			}
			if (StringUtil.isNotBlank(searchCondition.getEmail())) {
				strSql.append(" and user.email like ? ");
				countSql.append(" and user.email like ? ");
				paramsList.add(searchCondition.getEmail() + "%");
				parmTypeList.add((Type) Hibernate.STRING);
			}
			if (StringUtil.isNotBlank(searchCondition.getCompanyTel())) {
				strSql.append(" and user.companyTel like ? ");
				countSql.append(" and user.companyTel like ? ");
				paramsList.add(searchCondition.getCompanyTel() + "%");
				parmTypeList.add((Type) Hibernate.STRING);
			}
			if (StringUtil.isNotBlank(searchCondition.getFullName())) {
				strSql.append(" AND (user.lastName like ? " +
						" OR user.firstName like ? OR user.lastLocalName like ? " +
						" OR user.firstLocalName like ? " +
						" OR user.username like ? ) ");
				countSql.append(" AND (user.lastName like ? " +
						" OR user.firstName like ? OR user.lastLocalName like ? " +
						" OR user.firstLocalName like ? " +
						" OR user.username like ? ) ");
				String temp = searchCondition.getFullName() + "%";
				paramsList.add(temp);
				paramsList.add(temp);
				paramsList.add(temp);
				paramsList.add(temp);
				paramsList.add(temp);
				parmTypeList.add((Type) Hibernate.STRING);
				parmTypeList.add((Type) Hibernate.STRING);
				parmTypeList.add((Type) Hibernate.STRING);
				parmTypeList.add((Type) Hibernate.STRING);
				parmTypeList.add((Type) Hibernate.STRING);
			}

			if (searchCondition.isAccountLocked()) {
				strSql.append(" and user.accountLocked = ? ");
				countSql.append(" and user.accountLocked = ? ");
				paramsList.add(Constants.ACCOUNT_LOCKED_BOOLEAN_LOCKED);
				parmTypeList.add((Type) Hibernate.BOOLEAN);
			}else {
				strSql.append(" and user.accountLocked = ? ");
				countSql.append(" and user.accountLocked = ? ");
				paramsList.add(Constants.ACCOUNT_LOCKED_BOOLEAN_UNLOCKED);
				parmTypeList.add((Type) Hibernate.BOOLEAN);
			}
			strSql.append("ORDER BY user.lastName, user.firstName ");
			return new Object[]{getSession().createQuery(countSql.toString()).setParameters(
					paramsList.toArray(), parmTypeList.toArray(new Type[] {})).uniqueResult(),
					getSession().createQuery(strSql.toString()).setParameters(
					paramsList.toArray(), parmTypeList.toArray(new Type[] {}))
					.setFirstResult(from).setMaxResults(size).list()};

		}
		
	
	/**
     * @see UserDao#saveUser(User)
     */
    public User saveUser(User user) {
        logger.log(ILogger.ELevel.DEBUG, "user's id: " + user.getId());
        getHibernateTemplate().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return user;
    }
    
    /**
     * Overridden simply to call the saveUser method. This is happening 
     * because saveUser flushes the session and saveObject of BaseDaoHibernate 
     * does not.
     */
    @Override
    public User save(User user) {
        return this.saveUser(user);
    }

    /** 
    * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
    */
    public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List users = getHibernateTemplate().find(
				"from User user " +
				"where user.username=? and user.deleteFlg = ?",
				new String[] { username, Constants.DELETE_FLG_NORMAL });
		if (users == null || users.isEmpty()) {
			throw new UsernameNotFoundException("user '" + username
					+ "' not found...");
		} else {
			return (UserDetails) users.get(0);
		}
	}
    
	/**
	 * getUserByUserName 
	 *
	 * @param username
	 * @return  User
	 */
	public User getUserByUserName(final String username) {
		
		Criteria criteria = getSession().createCriteria(User.class)
    	.add( Restrictions.and(
    			Restrictions.eq("username", username),
    			Restrictions.eq("deleteFlg", Constants.DELETE_FLG_NORMAL)));
		return (User) criteria.uniqueResult();
		
	}
	
	/**
	 * Get system maintenance roles of the user
	 * 
	 * getSysRolesByUser 
	 *
	 * @param id user id
	 * @return  Set<Role> user's system maintenance role set.
	 */
	public Set<Role> getSysRolesByUser(final Long id){
		Set<Role> roles = new HashSet<Role>();
		String hql = "FROM Role role LEFT JOIN FETCH role.sysUsers user LEFT JOIN FETCH role.functions "
			+ " WHERE user.id =:id";
		List queryList = this.getSession().createQuery(hql).setLong("id", id).list();
	       if (queryList != null && queryList.size() > 0){
	            Iterator roleIte = queryList.iterator();
	            while (roleIte.hasNext()){
	            	roles.add((Role)roleIte.next());
	            }
	        }
		return roles;
	}
	
	/**
	 * getUserById 
	 *
	 * @param id
	 * @return  User
	 */
	public User getUserById(final Long id) {
		
		return (User) this.getHibernateTemplate().load(User.class, id);
	}
	
	/**
	 * addUser 
	 *
	 * @param user
	 * @param createOp
	 * @return  User
	 */
	public User addUser(final User user, final User createOp) {
		//set common fields
		user.setCreateDate(DateUtil.getNewDate());
		user.setUpdateDate(DateUtil.getNewDate());
		user.setEnabled(Constants.ACTIVE_FLG_BOOLEAN_ENABLED);
		user.setDeleteFlg(Constants.DELETE_FLG_NORMAL);
		
		getHibernateTemplate().save(user);
		return user;
	}
	
	/**
	 * updateUser 
	 *
	 * @param user
	 * @param updateOp
	 * @return  User
	 */
	public User updateUser(final User user, final User updateOp) {
		getHibernateTemplate().update(user);
		return user;
	}
	
	/**
	 * logically delete the object
	 * deleteUser 
	 *
	 * @param user  void
	 */
	public void deleteUser(final User user, final User deleteOp) {
		//set common fields
		user.setDeleteFlg(Constants.DELETE_FLG_DELETED);

		getHibernateTemplate().update(user);
	}
	
	/**
	 * remove the object form physical database
	 * removeUser 
	 *
	 * @param user  
	 */
	public void removeUser(final User user) {
		getHibernateTemplate().delete(user); 
	}
	
//    public int assignRoleToUser(Long roleId, Long targetUserId){
//    	
//    }
//    public int unassignRoleFromUser(Long roleId, Long targetUserId){
//    	
//    }
//
	/* Find user list what includes only active, unlocked, undeleted users. 
	 * 
	 * @return List of user. 
	 */
	public List findValidDropdownUserList() {
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(Constants.DELETE_FLG_NORMAL);
		paramList.add(Constants.ACCOUNT_EXPIRED_BOOLEAN_VALID);
		paramList.add(Constants.ACCOUNT_LOCKED_BOOLEAN_UNLOCKED);
		paramList.add(Constants.ACCOUNT_ENABLED_BOOLEAN_ENABLED);

		StringBuffer strHQL = new StringBuffer(
				"FROM User user " +
				" WHERE user.deleteFlg =? " +
				" AND user.accountExpired =? " +
				" AND user.accountLocked =? " +
				" AND user.enabled =? " +
				" ORDER BY user.lastName, user.firstName ");
		
		return getHibernateTemplate().find(strHQL.toString(), paramList
				.toArray());
	}
	
	public String createSQL(String name,String[] skillName){
		String str = "";
		for(int i=0;i<skillName.length;i++){
			if(i == skillName.length-1){
				str += name +" LIKE '%" + skillName[i] + "%' ";
			}else{
				str += name +" LIKE '%" + skillName[i] + "%' or ";
			}
		}
		return str;
	
	}
	
	/**
	 * findUserByUserId 
	 * @param userId
	 * @return  User
	 */
	public User findUserByUserId(Long userId) {

		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" FROM User user " +
				"                            WHERE user.id = ? " +
				"                              AND user.deleteFlg = ? ");
		
		params.add(userId);
		params.add(Constants.DELETE_FLG_NORMAL);
		
		List resultList = this.getHibernateTemplate().find(hql.toString(),params.toArray());
		if(resultList.size() > 0){			
			return (User) resultList.get(0);			
		}else{			
			return new User();			
		}
	}

}