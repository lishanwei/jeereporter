/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统角色Entity
 * @author LYN
 * @version 2016-01-28
 */
public class AuthRole extends DataEntity<AuthRole> {
	
	private static final long serialVersionUID = 1L;
	private String authsysId;		// 归属系统
	private String code;		// 角色编码
	private String name;		// 角色名称
	private String enname;		// 英文名称
	private String roleType;		// 角色类型
	
	public AuthRole() {
		super();
	}

	public AuthRole(String id){
		super(id);
	}

	@Length(min=1, max=64, message="归属系统长度必须介于 1 和 64 之间")
	public String getAuthsysId() {
		return authsysId;
	}

	public void setAuthsysId(String authsysId) {
		this.authsysId = authsysId;
	}
	
	@Length(min=1, max=20, message="角色编码长度必须介于 1 和 40 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=100, message="角色名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="英文名称长度必须介于 0 和 255 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	@Length(min=0, max=255, message="角色类型长度必须介于 0 和 255 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}