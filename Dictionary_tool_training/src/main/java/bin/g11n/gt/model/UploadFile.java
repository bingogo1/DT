package bin.g11n.gt.model;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * UploadFile.java
 * 
 * @author G11N Team
 * @version $Revision: 1.1 $  $Date 03/06/2009 17:11:44.455
 * @history
 * 
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "Z_UPLOAD_FILE")
public class UploadFile extends BaseObject implements Serializable {
    //generated automatically
    /* id: "Z_UPLOAD_FILE.ID" */
    private Long id;

    /* fileName: "Z_UPLOAD_FILE.FILE_NAME" */
    private String fileName;

    /* description: "Z_UPLOAD_FILE.DESCRIPTION" */
    private String description;

    /* fileContent: "Z_UPLOAD_FILE.FILE_CONTENT" */
    private Byte[] fileContent;

    
    /* fileObj File object*/
    private File fileObj;
    
    /* fileObjFileName this field can be set original file name by the s:file tag according to fileObj property. */
    private String fileObjFileName;
    
    private String fileObjContentType;

    private String toDeleteFlg;

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
     * the Getter of fileName
     * 
     * @return Returns the fileName.
     */
    @Column(name = "FILE_NAME", nullable = false, length = 100 )
    public String getFileName() {
        return fileName;
    }

    /**
     * the Setter of fileName
     *
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * the Getter of description
     * 
     * @return Returns the description.
     */
    @Column(name = "DESCRIPTION", length = 200 )
    public String getDescription() {
        return description;
    }

    /**
     * the Setter of description
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * the Getter of fileContent
     * 
     * @return Returns the fileContent.
     */
    @Column(name = "FILE_CONTENT" )
    @Lob
    public Byte[] getFileContent() {
        return fileContent;
    }

    /**
     * the Setter of fileContent
     *
     * @param fileContent The fileContent to set.
     */
    public void setFileContent(Byte[] fileContent) {
        this.fileContent = fileContent;
    }


	//override methods here:
	/**
	 * toString 
	 * @author common team
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
	 * @author common team
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
	 * @author common team
	 *
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UploadFile))
			return false;

		final UploadFile finalObj = (UploadFile) obj;
		if (id != null ? !id.equals(finalObj.id) : finalObj.id != null)
			return false;
		return true;
	}

	/**
	 * the Getter of fileObj
	 * 
	 * @return Returns the fileObj.
	 */
	@Transient
	public File getFileObj() {
		return fileObj;
	}

	/**
	 * the Setter of fileObj
	 *
	 * @param fileObj The fileObj to set.
	 */
	public void setFileObj(File fileObj) {
		this.fileObj = fileObj;
	}

	/**
	 * the Getter of fileObjContentType
	 * 
	 * @return Returns the fileObjContentType.
	 */
    @Column(name = "FILE_CONTENT_TYPE", length = 100 )
	public String getFileObjContentType() {
		return fileObjContentType;
	}

	/**
	 * the Setter of fileObjContentType
	 *
	 * @param fileObjContentType The fileObjContentType to set.
	 */
	public void setFileObjContentType(String fileObjContentType) {
		this.fileObjContentType = fileObjContentType;
	}
	
	  /**
	 * the Getter of fileObjFileName
	 * 
	 * @return Returns the fileObjFileName.
	 */
    @Column(name = "FILE_REAL_NAME", length = 100 )
	public String getFileObjFileName() {
		return fileObjFileName;
	}

	/**
	 * the Setter of fileObjFileName
	 *
	 * @param fileObjFileName The fileObjFileName to set.
	 */
	public void setFileObjFileName(String fileObjFileName) {
		this.fileObjFileName = fileObjFileName;
	}

	/**
	 * the Getter of toDeleteFlg
	 * 
	 * @return Returns the toDeleteFlg.
	 */
	@Transient
	public String getToDeleteFlg() {
		return toDeleteFlg;
	}

	/**
	 * the Setter of toDeleteFlg
	 *
	 * @param toDeleteFlg The toDeleteFlg to set.
	 */
	public void setToDeleteFlg(String toDeleteFlg) {
		this.toDeleteFlg = toDeleteFlg;
	}

}
