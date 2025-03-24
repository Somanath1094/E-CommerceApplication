package com.ecommerce.app.domain;

import com.ecommerce.app.constant.AppRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name= "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, name = "role_name")
	private AppRole roleName;

	public Role() {
	}

	public Role(AppRole roleCustomer) {
		// TODO Auto-generated constructor stub
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public AppRole getRoleName() {
		return roleName;
	}

	public void setRoleName(AppRole roleName) {
		this.roleName = roleName;
	}


}
