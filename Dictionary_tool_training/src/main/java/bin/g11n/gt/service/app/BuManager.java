package  bin.g11n.gt.service.app;

import java.util.List;

import bin.g11n.gt.model.Bu;
import bin.g11n.gt.service.common.Manager;


/**
 * 
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface BuManager extends Manager {
	List findAllBu();
	public Bu findBuById(Long id);
	Bu findBuByBuName(String buName);
}
