package bin.g11n.gt.dao.impl.app;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import bin.g11n.gt.common.Constants;
import bin.g11n.gt.dao.app.BuDao;
import bin.g11n.gt.dao.impl.common.GenericDaoHibernate;
import bin.g11n.gt.model.Bu;
import bin.g11n.gt.model.User;


/**
 * BuDaoHibernate.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BuDaoHibernate extends GenericDaoHibernate<Bu,Long> implements BuDao {

	public BuDaoHibernate() {
		super(Bu.class);

	}

	/**
	 * findAllBu 
	 *
	 * @return  List
	 */
	public List findAllBu() {
	
		return getHibernateTemplate().find("from Bu bu " +
				"where bu.deleteFlg=?", Constants.DELETE_FLG_NORMAL);
	}
	
	/**
	 * 
	 * findBuById 
	 *
	 * @param id
	 * @return  Bu
	 */
	public Bu findBuById(Long id) {
		List buList=this.getHibernateTemplate().find("from Bu bu where bu.id=? ",id);
		if(buList.isEmpty()){
			return new Bu();
		}
		else return (Bu) buList.get(0);
		
	}
	
	public User findBuLeaderById(Long id) {
		List buList=this.getHibernateTemplate().find("select user from Bu bu LEFT JOIN bu.buLeaderId user " +
				"where bu.id=? " +
				" AND bu.deleteFlg=? ",new Object[]{id, Constants.DELETE_FLG_NORMAL});
		if(buList.isEmpty()){
			return new User();
		}
		else return (User) buList.get(0);
		
	}
	
	
	/**
	 *
	 * @param buCd
	 * @return  Bu
	 */
	public Bu findBuByBuName(String buName) {
		String hql ="SELECT bu FROM Bu bu " +
					"WHERE bu.buName=? and bu.deleteFlg=? ";
		Query query = this.getSession().createQuery(hql).
				setString(0, buName).
				setString(0, Constants.DELETE_FLG_NORMAL);
		List buList = query.list();
		if(buList.isEmpty()){
			return null;
		}
		else return (Bu) buList.get(0);
	}
	
	/**
	 * skillHome used this method
	 * asmReqUpdate used this methord
	 * findBuIdListByBuCd
	 *  
	 * @author dlv
	 * @param buCdList
	 * @return  List
	 */
	public List findBuIdListByBuCd(List buCdList){
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append("SELECT DISTINCT bu.id FROM Bu bu WHERE bu.deleteFlg = ? ");
		params.add(Constants.DELETE_FLG_NORMAL);
		if(buCdList!=null && buCdList.size()> 0){
			hql.append(" AND bu.buName IN (? ");
			params.add(buCdList.get(0));
			for (int i=1 ;i < buCdList.size();i++){
				hql.append(", ? ");
				params.add(buCdList.get(i));
			}
			hql.append(" )");
		}
		List list = getHibernateTemplate().find(hql.toString(),params.toArray());
		if (list.isEmpty()) {
			return new ArrayList();
		} else {
			return list;
		}
	}
	
}