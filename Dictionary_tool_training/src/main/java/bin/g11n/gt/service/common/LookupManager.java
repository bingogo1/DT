package bin.g11n.gt.service.common;

import java.util.List;

import bin.g11n.gt.model.LabelValue;


/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface LookupManager extends Manager {
    /**
     * Retrieves all possible roles from persistence layer
     * @return List of LabelValue objects
     */
    public List<LabelValue> getAllRoles();
}
