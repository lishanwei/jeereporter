/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户DAO接口
 * @author LYN
 * @version 2016-01-26
 */
@MyBatisDao
public interface AuthUserDao extends CrudDao<AuthUser> {
	List<AuthUser> findByAuthRole(String roleid);

    public int exist(@Param(value = "username")String username);

    public AuthUser getAuthUser(@Param(value = "username")String username, @Param(value = "password")String passwordMd5Str);

    public int updateUserPass(@Param(value = "username")String username,
                              @Param(value = "oldPass")String oldPass, @Param(value = "newPass")String newPass);

    public int resetPass(AuthUser authUser);

    public int existPhone(String phone);

    public void resetUser(AuthUser authUser);

    public List<AuthUser> getAuthUserList(List<String> usernameList);

    public void deleteList(List<AuthUser> authUserList);

    public int updateUserName(@Param(value = "oldUsername")String oldUsername, @Param(value = "newUsername")String newUsername,@Param(value = "newPass") String newPass);
}