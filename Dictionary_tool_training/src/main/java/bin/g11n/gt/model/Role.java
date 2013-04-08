package bin.g11n.gt.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;


/**
 * Role.java
 * 
 * @author G11N Team
 * @version $Revision: 1.1 $  $Date 03/06/2009 17:11:44.424
 * @history
 * 
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "Z_ROLE")
public class Role extends BaseObject implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 3690197650654049848L;
    //generated automatically
    /* id: "Z_ROLE.ID" */
    private Long id;

    /* name: "Z_ROLE.NAME" */
    private String name;

    /* description: "Z_ROLE.DESCRIPTION" */
    private String description;

    /* description: "Z_ROLE.SHORT_NAME" */
    private String shortName;

    /* deleteFlg: "Z_ROLE.DELETE_FLG" */
    private String deleteFlg;

	/* sysMaintenanceUsers the users defined in the z_r_user_role */
	private Set<User> sysUsers = new HashSet<User>();

	private Set<Function> functions = new HashSet<Function>();
	
	/* users the users defined in the z_r_user_role, m_controller and m_pm_basicinfo*/
	private Set<User> users = new HashSet<User>();



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

    
    @Column(name = "SHORT_NAME", length = 30 )
    public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	/**
	 * the Getter of sysUsers
	 * 
	 * @return Returns the sysUsers.
	 */
	@ManyToMany(
	        cascade={CascadeType.PERSIST, CascadeType.MERGE},
	        mappedBy="sysRoles",
	        targetEntity=User.class	)
	public Set<User> getSysUsers() {
		return sysUsers;
	}

	/**
	 * the Setter of sysUsers
	 *
	 * @param sysUsers The sysUsers to set.
	 */
	public void setSysUsers(Set<User> sysUsers) {
		this.sysUsers = sysUsers;
	}

	/**
	 * the Getter of users
	 * 
	 * @return Returns the users.
	 */
	@Transient
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * the Setter of users
	 *
	 * @param users The users to set.
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * the Getter of functions
	 * 
	 * @return Returns the functions.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="Z_R_ROLE_FUNCTION", 
            joinColumns = { @JoinColumn( name="role_id") },
            inverseJoinColumns = @JoinColumn( name="function_id")
    )    
	public Set<Function> getFunctions() {
		return functions;
	}

	/**
	 * the Setter of functions
	 *
	 * @param functions The functions to set.
	 */
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
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
		if (!(obj instanceof Role))
			return false;

		final Role finalObj = (Role) obj;
		if (id != null ? !id.equals(finalObj.id) : finalObj.id != null)
			return false;
		return true;
	}
}
