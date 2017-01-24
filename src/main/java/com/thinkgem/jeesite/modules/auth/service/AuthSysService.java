/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.service;

import java.util.List;

import com.thinkgem.jeesite.common.annotation.DataSourceName;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.auth.entity.AuthSys;
import com.thinkgem.jeesite.modules.auth.dao.AuthSysDao;

/**
 * 系统信息Service
 * @author jiajianhong
 * @version 2016-01-28
 */
@Service
@DataSourceName("office")
@Transactional(readOnly = true)
public class AuthSysService extends CrudService<AuthSysDao, AuthSys> {

	public AuthSys get(String id) {
		return super.get(id);
	}
	
	public List<AuthSys> findList(AuthSys authSys) {
		return super.findList(authSys);
	}
	
	public Page<AuthSys> findPage(Page<AuthSys> page, AuthSys authSys) {
		return super.findPage(page, authSys);
	}
	
	@Transactional(readOnly = false)
	public String saveAuthSys(AuthSys authSys) {
        try {
            super.save(authSys);
        } catch (DuplicateKeyException e) {
            logger.error("插入code字段唯一索引出现异常，返回结果", e);
            return "系统编码已存在，请重新输入";
        } catch (Exception e) {
            logger.error("插入数据出现异常", e);
            return "保存出现异常，请检查";
        }
        return "保存成功";
	}

    @Transactional(readOnly = false)
    public void save(AuthSys authSys) {
        super.save(authSys);
    }

	@Transactional(readOnly = false)
	public void delete(AuthSys authSys) {
		super.delete(authSys);
	}

    /**
     * 检查系统编码是否存在
     * @param sysCode
     * @return
     */
    public boolean existSysCode(String sysCode) {

        return dao.existSysCode(sysCode) > 0 ? true : false;
    }
}