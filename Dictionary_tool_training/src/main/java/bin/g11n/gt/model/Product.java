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
 * Product.java
 * 
 * @author G11N Team
 * @version $Revision: 1.1 $  $Date 04/14/2009 10:22:14.354
 * @history
 * 
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "M_PRODUCT")
public class Product extends BaseObject implements Serializable {
    //generated automatically
    /* id: "M_PRODUCT.ID" */
    private Long id;

    /* productName: "M_PRODUCT.PRODUCT_NAME" */
    private String productName;

    /* productVersion: "M_PRODUCT.PRODUCT_VERSION" */
    private String productVersion;

    /* productDescription: "M_PRODUCT.PRODUCT_DESCRIPTION" */
    private String productDescription;

    /* productCenterId: "M_PRODUCT.PRODUCT_CENTER_ID" */
    private ProductCenter productCenterId;

    /* createUserId: "M_PRODUCT.CREATE_USER_ID" */
    private User createUserId;

    /* createDatetime: "M_PRODUCT.CREATE_DATETIME" */
    private Date createDatetime;

    /* updateUserId: "M_PRODUCT.UPDATE_USER_ID" */
    private User updateUserId;

    /* updateDatetime: "M_PRODUCT.UPDATE_DATETIME" */
    private Date updateDatetime;

    /* deleteFlg: "M_PRODUCT.DELETE_FLG" */
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
     * the Getter of productName
     * 
     * @return Returns the productName.
     */
    @Column(name = "PRODUCT_NAME", nullable = false, length = 512 )
    public String getProductName() {
        return productName;
    }

    /**
     * the Setter of productName
     *
     * @param productName The productName to set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * the Getter of productVersion
     * 
     * @return Returns the productVersion.
     */
    @Column(name = "PRODUCT_VERSION", length = 40 )
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * the Setter of productVersion
     *
     * @param productVersion The productVersion to set.
     */
    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    /**
     * the Getter of productDescription
     * 
     * @return Returns the productDescription.
     */
    @Column(name = "PRODUCT_DESCRIPTION", length = 512 )
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * the Setter of productDescription
     *
     * @param productDescription The productDescription to set.
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * the Getter of productCenterId
     * 
     * @return Returns the productCenterId.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CENTER_ID", referencedColumnName = "id" )
    public ProductCenter getProductCenterId() {
        return productCenterId;
    }

    /**
     * the Setter of productCenterId
     *
     * @param productCenterId The productCenterId to set.
     */
    public void setProductCenterId(ProductCenter productCenterId) {
        this.productCenterId = productCenterId;
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
		if (!(obj instanceof Product))
			return false;

		final Product finalObj = (Product) obj;
		if (id != null ? !id.equals(finalObj.id) : finalObj.id != null)
			return false;
		return true;
	}
}
