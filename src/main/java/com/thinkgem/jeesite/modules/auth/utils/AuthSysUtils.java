/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.utils;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.auth.dao.AuthSysDao;
import com.thinkgem.jeesite.modules.auth.entity.AuthSys;
import com.thinkgem.jeesite.modules.sys.dao.*;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * auth工具类
 * @author jiajianhong
 * @version 2013-12-05
 */
public class AuthSysUtils {

    private static AuthSysDao authSysDao = SpringContextHolder.getBean(AuthSysDao.class);

    public static AuthSys getAuthSysById(String id) {
        return authSysDao.get(id);
    }
	
}
