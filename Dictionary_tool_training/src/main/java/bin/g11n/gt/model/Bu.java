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
 * Bu.java
 * 
 * @author G11N Team
 * @version $Revision: 1.1 $  $Date 04/14/2009 10:22:14.292
 * @history
 * 
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "M_BU")
public class Bu extends BaseObject implements Serializable {
    //generated automatically
    /* id: "M_BU.ID" */
    private Long id;

    /* buName: "M_BU.BU_NAME" */
    private String buName;

    /* buDescription: "M_BU.BU_DESCRIPTION" */
    private String buDescription;

    /* buLeaderId: "M_BU.BU_LEADER_ID" */
    private User buLeaderId;

    /* createUserId: "M_BU.CREATE_USER_ID" */
    private User createUserId;

    /* createDatetime: "M_BU.CREATE_DATETIME" */
    private Date createDatetime;

    /* updateUserId: "M_BU.UPDATE_USER_ID" */
    private User updateUserId;

    /* updateDatetime: "M_BU.UPDATE_DATETIME" */
    private Date updateDatetime;

    /* deleteFlg: "M_BU.DELETE_FLG" */
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
     * the Getter of buName
     * 
     * @return Returns the buName.
     */
    @Column(name = "BU_NAME", nullable = false, length = 256 )
    public String getBuName() {
        return buName;
    }

    /**
     * the Setter of buName
     *
     * @param buName The buName to set.
     */
    public void setBuName(String buName) {
        this.buName = buName;
    }

    /**
     * the Getter of buDescription
     * 
     * @return Returns the buDescription.
     */
    @Column(name = "BU_DESCRIPTION", length = 256 )
    public String getBuDescription() {
        return buDescription;
    }

    /**
     * the Setter of buDescription
     *
     * @param buDescription The buDescription to set.
     */
    public void setBuDescription(String buDescription) {
        this.buDescription = buDescription;
    }

    /**
     * the Getter of buLeaderId
     * 
     * @return Returns the buLeaderId.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "BU_LEADER_ID", referencedColumnName = "id" )
    public User getBuLeaderId() {
        return buLeaderId;
    }

    /**
     * the Setter of buLeaderId
     *
     * @param buLeaderId The buLeaderId to set.
     */
    public void setBuLeaderId(User buLeaderId) {
        this.buLeaderId = buLeaderId;
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
		if (!(obj instanceof Bu))
			return false;

		final Bu finalObj = (Bu) obj;
		if (id != null ? !id.equals(finalObj.id) : finalObj.id != null)
			return false;
		return true;
	}
}
