package bin.g11n.gt.util;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * PageList.java
 * 
 * For display tag external pagination.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */

public class PageList implements PaginatedList {

	private int fullListSize;
	private List list;
	private int pageNumber;
	private int objectsPerPage;
	private String searchId;
	private String sortCriterion;
	private SortOrderEnum sortDirection;
	
	/**
	 * the Setter of objectsPerPage
	 *
	 * @param objectsPerPage The objectsPerPage to set.
	 */
	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	/**
	 * the Setter of pageNumber
	 *
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * the Setter of searchId
	 *
	 * @param searchId The searchId to set.
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	/**
	 * the Setter of sortCriterion
	 *
	 * @param sortCriterion The sortCriterion to set.
	 */
	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	/**
	 * the Setter of sortDirection
	 *
	 * @param sortDirection The sortDirection to set.
	 */
	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	public int getFullListSize() {
		return fullListSize;
	}
	
	public void setFullListSize(int fs) {
		fullListSize = fs;
	}

	public List getList() {
		return list;
	}
	
	public void setList(List lst) {
		list = lst;
	}

	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public String getSearchId() {
		return searchId;
	}

	public String getSortCriterion() {
		return sortCriterion;
	}

	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}

}
