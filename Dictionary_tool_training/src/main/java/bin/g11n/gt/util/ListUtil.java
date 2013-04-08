package bin.g11n.gt.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.pagination.PaginatedList;

import bin.g11n.gt.model.BaseObject;

/**
 * List Utility Class used to split pages. 
 *  
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ListUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	
	/**
	 * chopList 
	 *
	 * @param lst
	 * @param start
	 * @param end
	 * @return  List
	 */
	public static List chopList(List lst, int start, int end) {
		List result = new ArrayList();
		int size = lst.size();
		
		// on error, return an empty List.
		if ((start>size)||(end>size)||(start>=end)) {
			return result;
		}
		
		// get the list on range.
		for (int i=start;i<end;i++) {
			result.add(lst.get(i));
		}
		
		return result;
	}
	
	/**
	 * makeFirstPagingList 
	 *
	 * @param lst
	 * @param size
	 * @param fullSize
	 * @return  PaginatedList
	 */
	public static PaginatedList makeFirstPagingList(List lst,int size,int fullSize) {
		PageList result = new PageList();
		// set for first page
		result.setObjectsPerPage(size);
		result.setFullListSize(fullSize);
		result.setPageNumber(1);
		result.setList(chopList(lst,0,size));
		return (PaginatedList)result;
	}
	
	public static PaginatedList makePagingList(List lst,int size,int fullSize, int currPage) {
		PageList result = new PageList();
		result.setObjectsPerPage(size);
		result.setFullListSize(fullSize);
		result.setPageNumber(currPage);
		
		BaseObject bo;
		for (int i = 0; i < lst.size(); i++) {
			bo = (BaseObject) lst.get(i);
			bo.setRownum("" + i);
		}
		
		result.setList(lst);
		
		return (PaginatedList)result;
	}
}
