package com.thinkgem.jeesite.common.aspect;

import com.thinkgem.jeesite.common.annotation.DataSourceName;
import com.thinkgem.jeesite.common.db.DataSourceContextHolder;
import com.thinkgem.jeesite.common.db.DynamicDataSourceTransactionManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

/**
 * Created by jjh on 16/3/25.
 */
@Aspect
@Order(1)
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    private String dbTarget = null;

    @Pointcut(value = "execution(* com.thinkgem..service.*.*(..))")
    public void serviceMethod(){

    }

//    @Before("serviceMethod()")
//    public void beforeMethod(JoinPoint jp) {
//
//        DataSourceName dataSourceName = (DataSourceName) jp.getSignature().getDeclaringType().getAnnotation(DataSourceName.class);
//
//        if (null != dataSourceName) {
//            dbTarget = DataSourceContextHolder.getDbTarget();
//            logger.info("数据库切换为" + dataSourceName.value());
//            DataSourceContextHolder.setDbTarget(dataSourceName.value());
//        } else {
//            logger.info("没有注解,直接默认数据库");
//            DataSourceContextHolder.setDbTarget(dbTarget);
//        }
//
//    }

    @AfterThrowing("serviceMethod()")
    public void afterException() {

        logger.info("抛出异常,直接默认数据库");
        // 抛出异常时,切换为默认数据库
        DataSourceContextHolder.setDbTarget(dbTarget);
    }

    @After("serviceMethod()")
    public void afterMethod() {

        logger.info("执行结束,直接默认数据库");

        // 执行结束,切换为默认数据库
        DataSourceContextHolder.setDbTarget(dbTarget);

    }

}
