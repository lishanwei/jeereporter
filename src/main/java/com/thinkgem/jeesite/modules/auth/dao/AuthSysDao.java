/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthSys;

/**
 * 系统信息DAO接口
 * @author jiajianhong
 * @version 2016-01-28
 */
@MyBatisDao
public interface AuthSysDao extends CrudDao<AuthSys> {

    public int existSysCode(String sysCode);
}