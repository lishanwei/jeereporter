/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.interceptor;

import com.thinkgem.jeesite.common.db.DataSourceContextHolder;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 日志拦截器
 * @author ThinkGem
 * @version 2014-8-19
 */
public class DataSourceInterceptor extends BaseService implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);

    private String dataSourceName;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		// 设置数据库为想使用的数据库
        logger.info("数据库切换为"  + dataSourceName);
        DataSourceContextHolder.setDbTarget(dataSourceName);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
//		if (modelAndView != null){
//			logger.info("ViewName: " + modelAndView.getViewName());
//		}

        // 设置数据库为默认的数据库
        DataSourceContextHolder.setDbTarget(null);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {

        // 设置数据库为默认的数据库
        DataSourceContextHolder.setDbTarget(null);
	}

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
