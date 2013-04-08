package bin.g11n.gt.model;

import java.io.Serializable;

import bin.g11n.gt.common.Constants;


/**
 * Base class for Model objects.  Child objects should implement toString(), 
 * equals() and hashCode();
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public abstract class BaseObject implements Serializable {
	//Row number.
    public String rownum;
    //This flag is used for permit control for each row data.
    public String rowOperationFlg; 
    public abstract String toString();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
	public String getRownum() {
		return rownum;
	}
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
	public String getRowOperationFlg() {
		return rowOperationFlg;
	}
	public void setRowOperationFlg(String rowOperationFlg) {
		this.rowOperationFlg = rowOperationFlg;
	}
    
    public boolean isCanModify(){
    	return Constants.ROW_DATA_CAN_MODIFY_TRUE.equals(this.rowOperationFlg) ||
    		Constants.ROW_DATA_CAN_REVIEW_MODIFY_TRUE.equals(this.rowOperationFlg);
    }
    
    public boolean isCanApprove(){
    	return Constants.ROW_DATA_CAN_APPROVE_TRUE.equals(this.rowOperationFlg) ||
		Constants.ROW_DATA_CAN_REVIEW_MODIFY_TRUE.equals(this.rowOperationFlg);
    }

}
