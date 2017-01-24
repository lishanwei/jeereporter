/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.dao;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthRole;
import com.thinkgem.jeesite.modules.auth.entity.AuthUserRole;

/**
 * 系统角色DAO接口
 * @author LYN
 * @version 2016-01-26
 */
@MyBatisDao
public interface AuthRoleDao extends CrudDao<AuthRole> {

	public void assignUserToRole(List<AuthUserRole> authUserRoles);

	public void outRole(@Param( value="userid" ) String userid, @Param(value="roleid") String roleid);

    public void deleteUserRole(@Param(value = "roleId")String roleId, @Param(value = "reduceList")List<String> reduceList);

    public int existRoleCode(List<String> roleCodeList);

    public List<String> findRoleCodeList(@Param(value = "userId")String id, @Param(value = "sysCode")String sysCode);

    public List<AuthRole> findRoleIdByCode(@Param(value = "codeList")List<String> roleCodes,
                                           @Param(value = "sysCode")String sysCode);

    public List<AuthRole> findUserRoleList(List<AuthUserRole> authUserRoleList);
}