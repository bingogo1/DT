package bin.g11n.gt.dao.app;

import java.util.List;

import bin.g11n.gt.dao.common.GenericDao;
import bin.g11n.gt.model.Bu;
import bin.g11n.gt.model.User;

/**
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface BuDao extends GenericDao<Bu, Long> {
	List findAllBu();

	Bu findBuById(Long id);
	User findBuLeaderById(Long id);
	Bu findBuByBuName(String buName);
}
