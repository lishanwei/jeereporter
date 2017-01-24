/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 系统用户Entity
 * @author LYN
 * @version 2016-01-26
 */
public class AuthUser extends DataEntity<AuthUser> {
	
	private static final long serialVersionUID = 1L;
    public static final String originPass = "123456";

	private String username;		// 用户名
	private String password;		// 密码
	private String source;		// 来源
	private String phone;		// phone

    private String roleCode;  // 角色编码

    private List<AuthRole> authRoleList = Lists.newArrayList();

	public AuthUser() {
		super();
	}

	public AuthUser(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户名长度必须介于 0 和 100 之间")
    @ExcelField(title="username", align=2, sort=1)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=200, message="密码长度必须介于 0 和 200 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=50, message="来源长度必须介于 0 和 50 之间")
    @ExcelField(title="来源", align=2, sort=3)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=1, max=20, message="phone长度必须介于 1 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    @ExcelField(title="角色编码", align=2, sort=5)
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<AuthRole> getAuthRoleList() {
        return authRoleList;
    }

    public void setAuthRoleList(List<AuthRole> authRoleList) {
        this.authRoleList = authRoleList;
    }

    public List<String> getRoleIdList() {
        List<String> roleIdList = Lists.newArrayList();
        for (AuthRole authRole : authRoleList) {
            roleIdList.add(authRole.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        authRoleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            AuthRole authRole = new AuthRole();
            authRole.setId(roleId);
            authRoleList.add(authRole);
        }
    }
}