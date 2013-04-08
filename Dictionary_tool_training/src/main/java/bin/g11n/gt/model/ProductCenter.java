package bin.g11n.gt.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import bin.g11n.gt.model.BaseObject;
import bin.g11n.gt.model.User;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;


/**
 * ProductCenter.java
 * 
 * @author G11N Team
 * @version $Revision: 1.1 $  $Date 04/14/2009 10:22:14.370
 * @history
 * 
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "M_PRODUCT_CENTER")
public class ProductCenter extends BaseObject implements Serializable {
    //generated automatically
    /* id: "M_PRODUCT_CENTER.ID" */
    private Long id;

    /* centerName: "M_PRODUCT_CENTER.CENTER_NAME" */
    private String centerName;

    /* centerDescription: "M_PRODUCT_CENTER.CENTER_DESCRIPTION" */
    private String centerDescription;

    /* productLineId: "M_PRODUCT_CENTER.PRODUCT_LINE_ID" */
    private ProductLine productLineId;

    /* createUserId: "M_PRODUCT_CENTER.CREATE_USER_ID" */
    private User createUserId;

    /* createDatetime: "M_PRODUCT_CENTER.CREATE_DATETIME" */
    private Date createDatetime;

    /* updateUserId: "M_PRODUCT_CENTER.UPDATE_USER_ID" */
    private User updateUserId;

    /* updateDatetime: "M_PRODUCT_CENTER.UPDATE_DATETIME" */
    private Date updateDatetime;

    /* deleteFlg: "M_PRODUCT_CENTER.DELETE_FLG" */
    private String deleteFlg;



  //getter and setter
    /**
     * the Getter of id
     * 
     * @return Returns the id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    public Long getId() {
        return id;
    }

    /**
     * the Setter of id
     *
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * the Getter of centerName
     * 
     * @return Returns the centerName.
     */
    @Column(name = "CENTER_NAME", nullable = false, length = 256 )
    public String getCenterName() {
        return centerName;
    }

    /**
     * the Setter of centerName
     *
     * @param centerName The centerName to set.
     */
    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    /**
     * the Getter of centerDescription
     * 
     * @return Returns the centerDescription.
     */
    @Column(name = "CENTER_DESCRIPTION", length = 256 )
    public String getCenterDescription() {
        return centerDescription;
    }

    /**
     * the Setter of centerDescription
     *
     * @param centerDescription The centerDescription to set.
     */
    public void setCenterDescription(String centerDescription) {
        this.centerDescription = centerDescription;
    }

    /**
     * the Getter of productLineId
     * 
     * @return Returns the productLineId.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_LINE_ID", referencedColumnName = "id" )
    public ProductLine getProductLineId() {
        return productLineId;
    }

    /**
     * the Setter of productLineId
     *
     * @param productLineId The productLineId to set.
     */
    public void setProductLineId(ProductLine productLineId) {
        this.productLineId = productLineId;
    }

    /**
     * the Getter of createUserId
     * 
     * @return Returns the createUserId.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_USER_ID", referencedColumnName = "id", nullable = false )
    public User getCreateUserId() {
        return createUserId;
    }

    /**
     * the Setter of createUserId
     *
     * @param createUserId The createUserId to set.
     */
    public void setCreateUserId(User createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * the Getter of createDatetime
     * 
     * @return Returns the createDatetime.
     */
    @Column(name = "CREATE_DATETIME" )
    public Date getCreateDatetime() {
        return createDatetime;
    }

    /**
     * the Setter of createDatetime
     *
     * @param createDatetime The createDatetime to set.
     */
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * the Getter of updateUserId
     * 
     * @return Returns the updateUserId.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATE_USER_ID", referencedColumnName = "id", nullable = false )
    public User getUpdateUserId() {
        return updateUserId;
    }

    /**
     * the Setter of updateUserId
     *
     * @param updateUserId The updateUserId to set.
     */
    public void setUpdateUserId(User updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * the Getter of updateDatetime
     * 
     * @return Returns the updateDatetime.
     */
    @Column(name = "UPDATE_DATETIME" )
    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    /**
     * the Setter of updateDatetime
     *
     * @param updateDatetime The updateDatetime to set.
     */
    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    /**
     * the Getter of deleteFlg
     * 
     * @return Returns the deleteFlg.
     */
    @Column(name = "DELETE_FLG", nullable = false, length = 1 )
    public String getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * the Setter of deleteFlg
     *
     * @param deleteFlg The deleteFlg to set.
     */
    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }


	//override methods here:
	/**
	 * toString 
	 * @author G11N Team
	 *
	 * @return
	 */
	public String toString() {
		//TODO add your own field name here		
		//return buffer.append(this.yourFieldName).toString();
		return "";
	}

	/**
	 * hashCode 
	 * @author G11N Team
	 *
	 * @return
	 */
	public int hashCode() {
		int result = 0;
		result = (id != null ? id.hashCode() : 0);
		return result;
	}

	/**
	 * equals 
	 * @author G11N Team
	 *
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProductCenter))
			return false;

		final ProductCenter finalObj = (ProductCenter) obj;
		if (id != null ? !id.equals(finalObj.id) : finalObj.id != null)
			return false;
		return true;
	}
}
