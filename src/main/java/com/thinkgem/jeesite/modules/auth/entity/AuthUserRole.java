package com.thinkgem.jeesite.modules.auth.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AuthUserRole extends DataEntity<AuthUserRole>{

    private static final long serialVersionUID = 1L;
	private String userid;
	private String roleid;

    public AuthUserRole(){
        super();
    }

    public AuthUserRole(String id) {
        super(id);
    }
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	
}
