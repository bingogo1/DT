package bin.g11n.gt.service.impl.app;

import java.util.List;

import bin.g11n.gt.dao.app.BuDao;
import bin.g11n.gt.model.Bu;
import bin.g11n.gt.service.app.BuManager;
import bin.g11n.gt.service.impl.common.BaseManager;


/**
 * BuManagerImpl.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BuManagerImpl extends BaseManager implements BuManager {
	private BuDao dao;

	public List findAllBu() {

		return dao.findAllBu();
	}

	public void setBuDao(BuDao budao) {
		this.dao = budao;
	}

	public Bu findBuById(Long id) {
		return this.dao.findBuById(id);
	}

	public Bu findBuByBuName(String buName) {
		return this.dao.findBuByBuName(buName);
	}

}
