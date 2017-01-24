/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.auth.dao.AuthRoleDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthRole;
import com.thinkgem.jeesite.modules.auth.entity.AuthUserRole;

/**
 * 系统角色Service
 * @author LYN
 * @version 2016-01-26
 */
@Service
@Transactional(readOnly = true)
public class AuthRoleService extends CrudService<AuthRoleDao, AuthRole> {

	@Autowired
	AuthRoleDao authRoleDao;

    @Autowired
    private AuthUserService authUserService;

	public AuthRole get(String id) {
		return super.get(id);
	}
	
	public List<AuthRole> findList(AuthRole authRole) {
		return super.findList(authRole);
	}
	
	public Page<AuthRole> findPage(Page<AuthRole> page, AuthRole authRole) {
		return super.findPage(page, authRole);
	}
	
	@Transactional(readOnly = false)
	public void save(AuthRole authRole) {
		super.save(authRole);
	}

    @Transactional(readOnly = false)
    public String saveAuthRole(AuthRole authRole) {
        try {
            super.save(authRole);
        } catch (DuplicateKeyException e) {
            logger.error("插入code字段唯一索引出现异常，返回结果", e);
            return "角色编码已存在，请重新输入";
        } catch (Exception e) {
            logger.error("插入数据出现异常", e);
            return "保存出现异常，请检查";
        }
        return "保存成功";
    }
	
	@Transactional(readOnly = false)
	public void delete(AuthRole authRole) {
		super.delete(authRole);
	}

	@Transactional(readOnly = false)
	public int assignUserToRole(AuthRole authRole, String[] ids, String[] preIds) {
		List<AuthUserRole> addList = new ArrayList<AuthUserRole>();
        List<String> reduceList = new ArrayList<String>();
		for(int i=0; i< ids.length; i++){

            // 选出增加的用户id
            if (ArrayUtils.indexOf(preIds, ids[i]) < 0) {
                AuthUserRole authUserRole  = new AuthUserRole();
                authUserRole.preInsert();
                authUserRole.setUserid(ids[i]);
                authUserRole.setRoleid(authRole.getId());
                addList.add(authUserRole);
            }

		}
        for(int i=0; i< preIds.length; i++){

            // 选出减少的用户id
            if (ArrayUtils.indexOf(ids, preIds[i]) < 0) {
                reduceList.add(preIds[i]);
            }

        }
        if (!Collections3.isEmpty(addList)) {
            authRoleDao.assignUserToRole(addList);
        }
        if (!Collections3.isEmpty(reduceList)) {
            authRoleDao.deleteUserRole(authRole.getId(), reduceList);
        }
		return addList.size();
	}

	@Transactional(readOnly = false)
	public void outrole(String userid, String roleid) {
		authRoleDao.outRole(userid, roleid);
	}

    /**
     * 查询角色编码是否存在
     * @param roleCodeList
     * @return
     */
    public boolean existRoleCode(List<String> roleCodeList) {
        return dao.existRoleCode(roleCodeList) >= roleCodeList.size() ? true : false;
    }

    /**
     * 根据用户名和密码获取用户角色信息
     * @param username
     * @param passwordMd5Str
     * @return
     */
    public List<String> findRoleCodeList(String username, String passwordMd5Str, String sysCode) {

        // 根据用户名和密码获取用户信息
        AuthUser authUser = authUserService.getAuthUser(username, passwordMd5Str);

        // 根据用户id查询角色编码
        return authRoleDao.findRoleCodeList(authUser.getId(), sysCode);
    }
}