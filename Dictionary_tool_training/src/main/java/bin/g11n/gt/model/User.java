package bin.g11n.gt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import bin.g11n.gt.security.SecurityUtil;
import bin.g11n.gt.util.StringUtil;



/**
 * This class represents the basic "user" object in AppFuse that allows for
 * authentication and user management. It implements Acegi Security's
 * UserDetails interface. * 
 * User.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
@Entity
@Table(name = "Z_USER")
public class User extends BaseObject implements Serializable, UserDetails {
	private static final long serialVersionUID = 3832626162173359411L;
	/* confirmPassword transient encrypted password */
	protected String confirmPassword;

	//different variable name from the generated file.
	/* lastName last name of user "Z_USER.LAST_ROMAJI_NAME" */
	protected String lastName; // required

	/* firstName first name of user "Z_USER.FIRST_ROMAJI_NAME"*/
	protected String firstName; // required

	/* username user name "Z_USER.USER_CD"*/
	protected String username; // required

	/* version version "Z_USER.VERSION"*/
	protected Integer version;

	/* accountExpired 1: valid; 0: expired "Z_USER.ACCOUNT_EXPIRED"*/
	protected boolean accountExpired;

	/* accountLocked "Z_USER.ACCOUNT_LOCKED" */
	protected boolean accountLocked;

	/* credentialsExpired "Z_USER.CREDENTIALS_EXPIRED"*/
	protected boolean credentialsExpired;

	/* enabled 1: enabled; 0: disabled. "Z_USER.ACCOUNT_ENABLED"*/
	protected boolean enabled;

	/* createDate create datetime "Z_USER.CREATE_DATETIME" */
	protected Date createDate;

	/* updateDate update datetime "Z_USER.UPDATE_USER_ID"*/
	protected Date updateDate;

	/* createOp creator "Z_USER.CREATE_USER_ID" */
	protected User createOp;

	/* updateOp mender "Z_USER.UPDATE_USER_ID" */
	protected User updateOp;


	
    //generated automatically
    /* id: "Z_USER.ID" */
    private Long id;

    /* buId: "Z_USER.BU_ID" */
    private Bu buId;

    /* firstLocalName: "Z_USER.FIRST_LOCAL_NAME" */
    private String firstLocalName;

    /* gender: "Z_USER.GENDER" */
    private String gender;

    /* email: "Z_USER.EMAIL" */
    private String email;

    /* companyTel: "Z_USER.COMPANY_TEL" */
    private String companyTel;

    /* lastLocalName: "Z_USER.LAST_LOCAL_NAME" */
    private String lastLocalName;

    /* middleRomajiName: "Z_USER.MIDDLE_ROMAJI_NAME" */
    private String middleRomajiName;

    /* middleLocalName: "Z_USER.MIDDLE_LOCAL_NAME" */
    private String middleLocalName;

    /* deleteFlg: "Z_USER.DELETE_FLG" */
    private String deleteFlg;

    /* website: "Z_USER.WEBSITE" */
    private String website;

    /* password: "Z_USER.PASSWORD" */
    private String password;

    /* passwordHint: "Z_USER.PASSWORD_HINT" */
    private String passwordHint;

    // Last Local Name + Middle Local Name + First Local Name
    private String fullLocalName;

    //transient full English name of employee 
    // First En Name + Middle En Name + Last En Name
    private String fullEnName;
    //transient full local name of employee 
    // Last Local Name + Middle Local Name + First Local Name (First En Name + Middle En Name + Last En Name)
    private String fullName;

    //the system maintenance roles defined in Z_R_USER_ROLE
    private Set<Role> sysRoles = new HashSet<Role>();
	
	//transient: all of the system maintenance roles, organization roles and project roles.
    private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;

		final User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null)
			return false;
		if (username != null ? !username.equals(user.username)
				: user.username != null)
			return false;
		return true;

	}

	public int hashCode() {
		return (username != null ? username.hashCode() : 0);
	}

	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("username", this.username)
				.append("enabled", this.enabled).append("accountExpired",
						this.accountExpired).append("credentialsExpired",
						this.credentialsExpired).append("accountLocked",
						this.accountLocked);

		GrantedAuthority[] auths = this.getAuthorities();
		if (auths != null) {
			sb.append("Granted Authorities: ");

			for (int i = 0; i < auths.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(auths[i].toString());
			}
		} else {
			sb.append("No Granted Authorities");
		}
		return sb.toString();
	}

	// getter and setter
	//generated automatically start
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
     * the Getter of buId
     * 
     * @return Returns the buId.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "BU_ID", referencedColumnName = "id", nullable = false )
    public Bu getBuId() {
        return buId;
    }

    /**
     * the Setter of buId
     *
     * @param buId The buId to set.
     */
    public void setBuId(Bu buId) {
        this.buId = buId;
    }

    /**
     * the Getter of firstLocalName
     * 
     * @return Returns the firstLocalName.
     */
    @Column(name = "FIRST_LOCAL_NAME", length = 40 )
    public String getFirstLocalName() {
        return firstLocalName;
    }

    /**
     * the Setter of firstLocalName
     *
     * @param firstLocalName The firstLocalName to set.
     */
    public void setFirstLocalName(String firstLocalName) {
        this.firstLocalName = firstLocalName;
    }

    /**
     * the Getter of gender
     * 
     * @return Returns the gender.
     */
    @Column(name = "GENDER", length = 1 )
    public String getGender() {
        return gender;
    }

    /**
     * the Setter of gender
     *
     * @param gender The gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * the Getter of email
     * 
     * @return Returns the email.
     */
    @Column(name = "EMAIL", nullable = false, length = 256 )
    public String getEmail() {
        return email;
    }

    /**
     * the Setter of email
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * the Getter of companyTel
     * 
     * @return Returns the companyTel.
     */
    @Column(name = "COMPANY_TEL", length = 14 )
    public String getCompanyTel() {
        return companyTel;
    }

    /**
     * the Setter of companyTel
     *
     * @param companyTel The companyTel to set.
     */
    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    /**
     * the Getter of lastLocalName
     * 
     * @return Returns the lastLocalName.
     */
    @Column(name = "LAST_LOCAL_NAME", length = 40 )
    public String getLastLocalName() {
        return lastLocalName;
    }

    /**
     * the Setter of lastLocalName
     *
     * @param lastLocalName The lastLocalName to set.
     */
    public void setLastLocalName(String lastLocalName) {
        this.lastLocalName = lastLocalName;
    }

    /**
     * the Getter of middleRomajiName
     * 
     * @return Returns the middleRomajiName.
     */
    @Column(name = "MIDDLE_ROMAJI_NAME", length = 40 )
    public String getMiddleRomajiName() {
        return middleRomajiName;
    }

    /**
     * the Setter of middleRomajiName
     *
     * @param middleRomajiName The middleRomajiName to set.
     */
    public void setMiddleRomajiName(String middleRomajiName) {
        this.middleRomajiName = middleRomajiName;
    }

    /**
     * the Getter of middleLocalName
     * 
     * @return Returns the middleLocalName.
     */
    @Column(name = "MIDDLE_LOCAL_NAME", length = 40 )
    public String getMiddleLocalName() {
        return middleLocalName;
    }

    /**
     * the Setter of middleLocalName
     *
     * @param middleLocalName The middleLocalName to set.
     */
    public void setMiddleLocalName(String middleLocalName) {
        this.middleLocalName = middleLocalName;
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
     * the Getter of password
     * 
     * @return Returns the password.
     */
    @Column(name = "PASSWORD", length = 100 )
    public String getPassword() {
        return password;
    }

    /**
     * the Setter of password
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * the Getter of passwordHint
     * 
     * @return Returns the passwordHint.
     */
    @Column(name = "PASSWORD_HINT", length = 30 )
    public String getPasswordHint() {
        return passwordHint;
    }

    /**
     * the Setter of passwordHint
     *
     * @param passwordHint The passwordHint to set.
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

	//generated automatically end
	
	//different field name from generated file end

	@Column(name = "ACCOUNT_LOCKED", nullable = false)
	public boolean isAccountLocked() {
		return accountLocked;
	}

	@Column(name = "CREDENTIALS_EXPIRED", nullable = false)
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Column(name = "FIRST_ROMAJI_NAME", nullable = false, length = 50)
	public String getFirstName() {
		return firstName;
	}

	@Column(name = "LAST_ROMAJI_NAME", nullable = false, length = 50)
	public String getLastName() {
		return lastName;
	}

	/**
	 * the Getter of createDate
	 * 
	 * @return Returns the createDate.
	 */
	@Column(name = "CREATE_DATETIME", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * the Setter of createDate
	 * 
	 * @param createDate
	 *            The createDate to set.
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * the Getter of createOp
	 * 
	 * @return Returns the createOp.
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER_ID")
	public User getCreateOp() {
		return createOp;
	}

	/**
	 * the Setter of createOp
	 * 
	 * @param createOp
	 *            The createOp to set.
	 */
	public void setCreateOp(User createOp) {
		this.createOp = createOp;
	}

	/**
	 * the Getter of updateDate
	 * 
	 * @return Returns the updateDate.
	 */
	@Column(name = "UPDATE_DATETIME", nullable = false)
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * the Setter of updateDate
	 * 
	 * @param updateDate
	 *            The updateDate to set.
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "USER_CD", nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	/**
	 * the Getter of updateOp
	 * 
	 * @return Returns the updateOp.
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATE_USER_ID")
	public User getUpdateOp() {
		return updateOp;
	}

	/**
	 * the Setter of updateOp
	 * 
	 * @param updateOp
	 *            The updateOp to set.
	 */
	public void setUpdateOp(User updateOp) {
		this.updateOp = updateOp;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	@Column(name = "ACCOUNT_ENABLED")
	public boolean isEnabled() {
		return enabled;
	}

	@Column(name = "ACCOUNT_EXPIRED", nullable = false)
	public boolean isAccountExpired() {
		return accountExpired;
	}
	//different field name from generated file end

	
	
	//customized methods start

	
	/**
	 * @see org.acegisecurity.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Transient
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public void setSysRoles(Set<Role> sysRoles) {
		this.sysRoles = sysRoles;
	}

	/**
	 * the Getter of roles
	 * 
	 * @return Returns the roles.
	 */
	@Transient
	public Set<Role> getRoles() {
		//Security get all roles of user.
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

	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Z_R_USER_ROLE", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getSysRoles() {
		return sysRoles;
	}

	/**
	 * Convert user roles to LabelValue objects for convenience.
	 * 
	 * @return a list of LabelValue objects with role information
	 */
	@Transient
	public List<LabelValue> getRoleList() {
		List<LabelValue> userRoles = new ArrayList<LabelValue>();

		if (this.roles != null) {
			for (Role role : roles) {
				// convert the user's roles to LabelValue Objects
				userRoles.add(new LabelValue(role.getName(), role.getName()));
			}
		}

		return userRoles;
	}

	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public void addRole(Role role) {
		getRoles().add(role);
	}

	/** This method is called when user logins.
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#getAuthorities()
	 */
	@Transient
	public GrantedAuthority[] getAuthorities() {
		//The current user's authorities are exacted from DB.
		if (roles != null){
			this.setRoles(SecurityUtil.getRolesByUser(this));
		}
		//get all roles here
		return roles.toArray(new GrantedAuthority[0]);
	}
	
	
	/**
	 * the Getter of fullEnName
	 * 
	 * @return Returns the fullEnName.
	 */
	@Transient
	public String getFullEnName() {
		//check whether the fullEnName is blank or not
		if(StringUtil.isNotBlank(fullEnName)){
			return fullEnName;
		}
		StringBuffer result = new StringBuffer();
		String prefix = firstName;
		String middle = middleRomajiName;
		String suffix = lastName;
		
		if (StringUtil.isNotBlank(prefix)){
			result.append(prefix.trim());
			result.append(" ");
		}
		if (StringUtil.isNotBlank(middle)){
			result.append(middle.trim());
			result.append(" ");
		}
		if (StringUtil.isNotBlank(suffix)){
			result.append(suffix.trim());
		}
		
		return result.toString();
	}

	/**
	 * the Setter of fullEnName
	 *
	 * @param fullEnName The fullEnName to set.
	 */
	public void setFullEnName(String fullEnName) {
		this.fullEnName = fullEnName;
	}

	/**
	 * the Getter of fullLocalName
	 * 
	 * @return Returns the fullLocalName.
	 */
	@Transient
	public String getFullLocalName() {
		//check whether the fullLocalName is blank or not
		if(StringUtil.isNotBlank(fullLocalName)){
			return fullLocalName;
		}
		
		StringBuffer result = new StringBuffer();
		String prefix = lastLocalName;
		String middle = middleLocalName;
		String suffix = firstLocalName;
		
		if (StringUtil.isNotBlank(prefix)){
			result.append(prefix.trim());
			result.append(" ");
		}
		if (StringUtil.isNotBlank(middle)){
			result.append(middle.trim());
			result.append(" ");
		}
		if (StringUtil.isNotBlank(suffix)){
			result.append(suffix.trim());
		}
		
		return result.toString();
	}

	/**
	 * the Setter of fullLocalName
	 *
	 * @param fullLocalName The fullLocalName to set.
	 */
	public void setFullLocalName(String fullLocalName) {
		this.fullLocalName = fullLocalName;
	}

	/**
	 * Returns the full name fullLocalName(fullEnName).
	 * 
	 * @return String full name
	 */
	@Transient
	public String getFullName() {
		//check whether the fullName is blank or not
		if(StringUtil.isNotBlank(fullName)){
			return fullName;
		}
		StringBuffer result = new StringBuffer();
		String prefix = this.getFullLocalName();
		String suffix = this.getFullEnName();
		
		if (StringUtil.isNotBlank(prefix)){
			result.append(prefix);
		}
		if (StringUtil.isNotBlank(suffix)){
			result.append("(");
			result.append(suffix);
			result.append(")");
		}
		
		return result.toString();
	}

	/**
	 * the Setter of fullName
	 *
	 * @param fullName The fullName to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	

	/**
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Transient
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	/**
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Transient
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	//customized methods end

}
