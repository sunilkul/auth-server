package com.example.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name= "tblUser")
public class UserEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;    
    private String loginId; 
    private String pwd; 

   
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private RoleEntity role;  

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}	

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", loginId=" + loginId + ", pwd=" + pwd + ", role=" + role + "]";
	}

	 
    
    

}
