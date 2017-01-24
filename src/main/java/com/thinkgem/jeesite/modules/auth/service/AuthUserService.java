/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.Exception.AuthRoleException;
import com.thinkgem.jeesite.common.Exception.AuthUserException;
import com.thinkgem.jeesite.common.annotation.DataSourceName;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.MD5Utils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.auth.dao.AuthRoleDao;
import com.thinkgem.jeesite.modules.auth.dao.AuthUserDao;
import com.thinkgem.jeesite.modules.auth.dao.AuthUserRoleDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthRole;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import com.thinkgem.jeesite.modules.auth.entity.AuthUserRole;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统用户Service
 * @author LYN
 * @version 2016-01-26
 */
@Service
@DataSourceName("office")
@Transactional(readOnly = true)
public class AuthUserService extends CrudService<AuthUserDao, AuthUser> {

	@Autowired
	private AuthUserDao authUserDao;

    @Autowired
    private AuthRoleDao authRoleDao;

    @Autowired
    private AuthUserRoleDao authUserRoleDao;

    @Autowired
    private SystemService systemService;

	public AuthUser get(String id) {
		return super.get(id);
	}
	
	public List<AuthUser> findList(AuthUser authUser) {
		return super.findList(authUser);
	}
	
	public Page<AuthUser> findPage(Page<AuthUser> page, AuthUser authUser) {

		return super.findPage(page, authUser);
	}
	
	@Transactional(readOnly = false)
	public void save(AuthUser authUser) {
		super.save(authUser);

        // 先要把先前的角色全部删除
        authUserRoleDao.deleteUserRole(authUser.getId());

        // 同时绑定角色信息
        for (AuthRole authRole : authUser.getAuthRoleList()) {
            AuthUserRole authUserRole = new AuthUserRole();
            authUserRole.preInsert();
            authUserRole.setUserid(authUser.getId());
            authUserRole.setRoleid(authRole.getId());
            authUserRole.setIsNewRecord(true);
            authUserRoleDao.insert(authUserRole);
        }
	}
	
	@Transactional(readOnly = false)
	public void delete(AuthUser authUser) {
		super.delete(authUser);
	}
	
	public List<AuthUser> findListByAuthRole(String roleid){
		return authUserDao.findByAuthRole(roleid);
	}

    /**
     * 检查用户名和密码是否存在
     * @param username
     * @return
     */
    public boolean exist(String username) {
        return authUserDao.exist(username) > 0 ? true : false;
    }

    /**
     * 根据用户名和密码获取用户信息
     * @param username
     * @param passwordMd5Str
     * @return
     */
    public AuthUser getAuthUser(String username, String passwordMd5Str) {
        return authUserDao.getAuthUser(username, passwordMd5Str);
    }

    /**
     * 保存用户信息，并创建用户与角色的关系
     * @param authUser
     * @param roleCodeList
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean saveSysUserAndRole(AuthUser authUser, List<String> roleCodeList, String sysRoleName) {

        if (StringUtils.isNumAndLetter(authUser.getUsername())) {

            // 检查用户名是否已存在
            if (!exist(authUser.getUsername())) {

                // 查询角色编码是否存在
                if (CollectionUtils.isNotEmpty(roleCodeList) && authRoleDao.existRoleCode(roleCodeList) <= roleCodeList.size()) {

                    // 都存在，把用户名和密码插入到数据库
                    // 先把密码进行加密
                    String passwordMd5Str = MD5Utils.md5Tool(authUser.getPassword());

                    authUser.preInsert();
                    authUser.setPassword(passwordMd5Str);
                    authUser.setIsNewRecord(true);
                    try {
                        saveUserAndRole(authUser, roleCodeList);

                        systemService.syncUser(authUser, sysRoleName);
                        logger.info("username:" + authUser.getUsername() + ",password:" + authUser.getPassword() + "保存成功");
                        return true;
                    } catch (Exception e) {
                        logger.error("username:" + authUser.getUsername() + ",password:" + authUser.getPassword() + "插入数据库失败", e);
                    }

                } else {
                    logger.error("roleCode" + roleCodeList.toString() + "不存在，请检查");
                }
            } else {
                logger.error("username:" + authUser.getUsername() + "已存在，不能再次插入");

            }
        } else {
            logger.error("username:" + authUser.getUsername() + "包含了特殊字符,请重新输入");
        }
        return false;
    }

    /**
     * 保存用户信息，并创建用户与角色的关系
     * @param authUser
     * @param roleCodeList
     */
    @Transactional(readOnly = false)
    public void saveUserAndRole(AuthUser authUser, List<String> roleCodeList) {
        //保存用户的信息
        authUserDao.insert(authUser);

        for (String roleCode : roleCodeList) {
            // 查询角色的id
            AuthRole authRole = new AuthRole();
            authRole.setCode(roleCode);
            List<AuthRole> authRoleList = authRoleDao.findList(authRole);

            // 保存用户与角色的关系
            AuthUserRole authUserRole = new AuthUserRole();
            authUserRole.preInsert();
            authUserRole.setUserid(authUser.getId());
            authUserRole.setRoleid(authRoleList.get(0).getId());
            authUserRole.setIsNewRecord(true);
            authUserRoleDao.insert(authUserRole);
        }
    }

    /**
     * 更新用户密码
     * @param username
     * @param oldPass
     * @param newPass
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updateUserPass(String username, String oldPass, String newPass) {

        // 对密码进行md5加密
        oldPass = MD5Utils.md5Tool(oldPass);
        newPass = MD5Utils.md5Tool(newPass);

        return authUserDao.updateUserPass(username, oldPass, newPass) > 0 ? true : false;

    }

    /**
     * 更新用户密码
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updateUserPass(AuthUser authUser) {

        authUser.setPassword(MD5Utils.md5Tool(authUser.getPassword()));
        return authUserDao.resetPass(authUser) > 0 ? true : false;

    }

    @Transactional(readOnly = false)
    public boolean resetPass(AuthUser authUser) {

        authUser.setPassword(MD5Utils.md5Tool(AuthUser.originPass));

        return authUserDao.resetPass(authUser) > 0 ? true : false;
    }

    /**
     * 删除用户，一并删除用户分配的角色关联
     * @param authUser
     */
    @Transactional(readOnly = false)
    public void deleteUserAndRole(AuthUser authUser) {

        // 删除用户（逻辑删除）
        super.delete(authUser);

        // 删除用户与角色的关联关系
        authUserRoleDao.deleteUserRole(authUser.getId());
    }

    /**
     * 用户变更角色
     * @param username
     * @param roleCodes
     * @param sysCode
     */
    @Transactional(readOnly = false)
    public void changeUserRole(String username, String[] roleCodes, String sysCode) throws Exception {

        // 先查询出用户的信息
        AuthUser authUser = authUserDao.getAuthUser(username ,null);

        if (null == authUser) {
            logger.error("用户" + username + "查询出来为null");
            throw new AuthUserException("用户" + username + "查询出来为null");
        }

        // 把用户原来的角色关联全部删除
        authUserRoleDao.deleteUserRole(authUser.getId());

        if (roleCodes.length == 0) {
            logger.info("用户" + username + "现在没有任何角色，已经删除所有关于此用户的角色关联");
            return;
        }

        List<String> roleCodeList = new ArrayList<String>();
        CollectionUtils.addAll(roleCodeList, roleCodes);

        // 查询所有角色的id
        List<AuthRole> authRoleList = authRoleDao.findRoleIdByCode(roleCodeList, sysCode);
        if (CollectionUtils.isEmpty(authRoleList)) {
            logger.error("用户" + username + "现有的角色查询为null");
            throw new AuthRoleException("用户" + username + "现有的角色查询为null");
        }

        List<AuthUserRole> authUserRoleList = new ArrayList<AuthUserRole>();

        for (AuthRole authRole : authRoleList) {
            AuthUserRole authUserRole = new AuthUserRole();
            authUserRole.setUserid(authUser.getId());
            authUserRole.setRoleid(authRole.getId());
            authUserRole.preInsert();
            authUserRoleList.add(authUserRole);
        }

        // 插入用户与角色的关联
        authUserRoleDao.insertList(authUserRoleList);

    }

    public boolean existPhone(String phone) {
        return authUserDao.existPhone(phone) > 0 ? true : false;
    }

    /**
     * 重新启动用户
     * @param authUser
     */
    @Transactional(readOnly = false)
    public void resetUser(AuthUser authUser) {
        authUserDao.resetUser(authUser);
    }

    /**
     * 删除用户以及所对应的角色关联
     * @param usernameList
     * @param roleCodeList
     */
    @Transactional(readOnly = false)
    public void deleteUserListAndRole(List<String> usernameList, List<String> roleCodeList, String sysCode) throws Exception {

        // 根据roleCode查询角色信息
        List<AuthRole> authRoleList = authRoleDao.findRoleIdByCode(roleCodeList, sysCode);

        if (CollectionUtils.isEmpty(authRoleList)) {
            logger.error("根据角色编码[" + roleCodeList.toString() + "]查询出来的角色信息为空");
            throw new AuthRoleException("根据角色编码[" + roleCodeList.toString() + "]查询出来的角色信息为空");
        }

        // 查询用户的信息
        List<AuthUser> authUserList = authUserDao.getAuthUserList(usernameList);

        if (CollectionUtils.isEmpty(authUserList)) {
            logger.error("根据用户名[" + usernameList.toString() + "]查询出来的用户信息为空");
            throw new AuthUserException("根据用户名[" + usernameList.toString() + "]查询出来的用户信息为空");
        }

        // 查询用户的角色关联
        List<AuthUserRole> authUserRoleList = authUserRoleDao.findListByAuthUser(authUserList);

        if (CollectionUtils.isEmpty(authUserRoleList)) {
            logger.error("用户[" + usernameList.toString() + "]查询的所有角色关联为空");
            throw new AuthRoleException("用户[" + usernameList.toString() + "]查询的所有角色关联为空");
        }

        // 用户与角色的关联先删除
        authUserRoleDao.deleteUserRoleRalations(authUserList, authRoleList);

        // 计算出剩余的角色关联
        Iterator<AuthUserRole> authUserRoleIterator = authUserRoleList.iterator();
        while (authUserRoleIterator.hasNext()) {
            for (AuthRole authRole : authRoleList) {
                AuthUserRole authUserRole = authUserRoleIterator.next();
                if (authRole.getId().equalsIgnoreCase(authUserRole.getRoleid())) {
                    authUserRoleIterator.remove();
                    break;
                }
            }
        }

        if (CollectionUtils.isEmpty(authUserRoleList)) {
            logger.info("用户[" + usernameList.toString() + "]没有任何角色关联了");
        } else {
            // 计算出该逻辑删除的用户信息
            Iterator<AuthUser> authUserIterator = authUserList.iterator();
            while (authUserIterator.hasNext()) {
                for (AuthUserRole authUserRole : authUserRoleList) {
                    AuthUser authUser = authUserIterator.next();
                    if (authUser.getId().equalsIgnoreCase(authUserRole.getUserid())) {
                        // 用户id相同,说明用户还有角色,不能删除
                        authUserIterator.remove();
                        break;
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(authUserList)) {
            logger.info("没有需要逻辑删除的用户");
        } else {
            // 把该逻辑删除的用户进行逻辑删除
            authUserDao.deleteList(authUserList);
        }

        logger.info("删除用户[" + usernameList.toString() + "]的,以及删除系统[" + sysCode + "]" +
                "角色[" + roleCodeList.toString() + "]的关联已经完成");

    }

    /**
     * 查询用户的所有角色关联
     * @param authUser
     * @return
     */
    public List<AuthRole> findUserRoleList(AuthUser authUser) {

        // 查询用户所关联的角色id
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserid(authUser.getId());
        List<AuthUserRole> authUserRoleList = authUserRoleDao.findList(authUserRole);

        if (CollectionUtils.isEmpty(authUserRoleList)) {
            logger.info("用户[" + authUser.getId() + "]没有任何关联的角色");
            return Lists.newArrayList();
        }

        // 查询角色的信息
        return authRoleDao.findUserRoleList(authUserRoleList);
    }

    /**
     * 更新用户登陆名
     * @param oldUsername   旧用户名
     * @param newUsername   新用户名
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean updateUserName(String oldUsername, String newUsername) {
    	
    	String newPass = MD5Utils.md5Tool(AuthUser.originPass);
        logger.info("开始更新用户登录名,旧用户名:" + oldUsername + ",新用户名:" + newUsername);

        // 检查参数是否有问题
        if (StringUtils.isEmpty(oldUsername) || StringUtils.isEmpty(newUsername)) {
            logger.error("旧用户名或新用户名为空,无法更新");
            return false;
        }

        // 检查新用户名是否存在,存在的不能更新
        if (!exist(oldUsername)) {
            logger.error("原用户名:" + oldUsername + "不存在,不能更新");
            return false;
        }
        
        // 检查新用户名是否存在,存在的不能更新
        if (exist(newUsername)) {
            logger.error("新用户名:" + newUsername + "已存在,不能更新,保持唯一");
            return false;
        }

        // 更新用户名
        return authUserDao.updateUserName(oldUsername, newUsername,newPass) > 0 ? true : false;

    }
}