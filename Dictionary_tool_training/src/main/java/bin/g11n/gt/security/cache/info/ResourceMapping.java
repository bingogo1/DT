package bin.g11n.gt.security.cache.info;

/**
 * ResourceMapping.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ResourceMapping {   
       
    // url   
    private String resourcePath;   
       
    // the role in this project   
    private String[] recipients = new String[0];

	/**
	 * the Getter of recipients
	 * 
	 * @return Returns the recipients.
	 */
	public String[] getRecipients() {
		return recipients;
	}

	/**
	 * the Setter of recipients
	 *
	 * @param recipients The recipients to set.
	 */
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	/**
	 * the Getter of resourcePath
	 * 
	 * @return Returns the resourcePath.
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * the Setter of resourcePath
	 *
	 * @param resourcePath The resourcePath to set.
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}   
          
  
}   