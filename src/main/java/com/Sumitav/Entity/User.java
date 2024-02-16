package com.Sumitav.Entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Long userId;

     
    private String fullName;

    
    private String email;

     
    private String password;
    
    private boolean enabled=true;

    // One-to-Many mapping with Role
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles= new HashSet<>();

    
    
	public User() {
		
		  
	}



	public User(Long userId, String fullName, String email,boolean enabled, String password, Set<UserRole> userRoles) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.userRoles = userRoles;
		this.enabled=enabled;
	}
	
	
	// i will not make custom class to implement user details 
	//this user will handle lets see my logic

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set=new HashSet<>();
        this.userRoles.forEach(userRole ->
        {
            set.add(new Authority(userRole.getRole().getRoleName()));
        });
        return set;
    }
	
	
	
	
	
	
	


	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Set<UserRole> getUserRoles() {
		return userRoles;
	}



	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
	 @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }



		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return  this.email;
		}


 

	 
}
