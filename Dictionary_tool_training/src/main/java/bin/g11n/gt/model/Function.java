package bin.g11n.gt.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;


/**
 * Function.java
 * 
 * @author G11N Team
 * @version $Revision: 1.1 $  $Date 03/06/2009 17:11:44.424
 * @history
 * 
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "Z_FUNCTION")
public class Function extends BaseObject implements Serializable,
		GrantedAuthority {
	private static final long serialVersionUID = 3690197650654049849L;
    //generated automatically
    /* id: "Z_FUNCTION.ID" */
    private Long id;

    /* name: "Z_FUNCTION.NAME" */
    private String name;

    /* description: "Z_FUNCTION.DESCRIPTION" */
    private String description;

	/* allAccessFlg true: anyone can access it. false: just authoritied user can access it 
	 * Import: if allAccessFlg of a function is set true, don't add it into the z_role_function in
	 * case the authorize error! */
	private boolean allAccessFlg;

	private Set<Role> roles = new HashSet<Role>();



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
	 * @see org.acegisecurity.GrantedAuthority#getAuthority()
	 */
	@Transient
	public String getAuthority() {
		return getName();
	}

    /**
     * the Getter of name
     * 
     * @return Returns the name.
     */
    @Column(name = "NAME", nullable = false, length = 100 )
    public String getName() {
        return name;
    }

    /**
     * the Setter of name
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * the Getter of description
     * 
     * @return Returns the description.
     */
    @Column(name = "DESCRIPTION", length = 100 )
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
	 * the Getter of allAccessFlg
	 * 
	 * @return Returns the allAccessFlg.
	 */
	@Column(name = "all_access_flg", nullable = false)
	public boolean isAllAccessFlg() {
		return allAccessFlg;
	}

	/**
	 * the Setter of allAccessFlg
	 *
	 * @param allAccessFlg The allAccessFlg to set.
	 */
	public void setAllAccessFlg(boolean allAccessFlg) {
		this.allAccessFlg = allAccessFlg;
	}

	/**
	 * the Getter of roles
	 * 
	 * @return Returns the roles.
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "functions", targetEntity = Role.class)
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * the Setter of roles
	 *
	 * @param roles The roles to set.
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
		if (!(obj instanceof Function))
			return false;

		final Function finalObj = (Function) obj;
		if (id != null ? !id.equals(finalObj.id) : finalObj.id != null)
			return false;
		return true;
	}
}
