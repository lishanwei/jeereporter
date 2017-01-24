/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthRole;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import com.thinkgem.jeesite.modules.auth.entity.AuthUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联DAO接口
 * @author jiajianhong
 * @version 2016-01-29
 */
@MyBatisDao
public interface AuthUserRoleDao extends CrudDao<AuthUserRole> {

    public int deleteUserRole(String id);

    public void insertList(List<AuthUserRole> authUserRoleList);

    public void deleteUserRoleRalations(@Param("authUserList")List<AuthUser> authUserList, @Param("authRoleList")List<AuthRole> authRoleList);

    public List<AuthUserRole> findListByAuthUser(List<AuthUser> authUserList);

}