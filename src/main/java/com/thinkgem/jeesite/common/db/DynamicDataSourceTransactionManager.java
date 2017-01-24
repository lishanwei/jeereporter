package com.thinkgem.jeesite.common.db;

import com.thinkgem.jeesite.common.annotation.DataSourceName;
import com.thinkgem.jeesite.common.utils.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DelegatingTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjh on 16/3/28.
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceTransactionManager.class);

    private static final ThreadLocal<List<String>> dbTarget = new ThreadLocal<List<String>>();

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {

        try {
            String classMethodName = null;
            if (definition instanceof DelegatingTransactionAttribute) {

                DelegatingTransactionAttribute delegatingTransactionAttribute = (DelegatingTransactionAttribute) definition;

                classMethodName = delegatingTransactionAttribute.getName();
            } else {
                TransactionTemplate transactionTemplate = (TransactionTemplate) definition;
                classMethodName = transactionTemplate.getName();
            }

            if (null != classMethodName) {
                String className = classMethodName.substring(0, classMethodName.lastIndexOf("."));

                DataSourceName dataSourceName = (DataSourceName) Class.forName(className).getAnnotation(DataSourceName.class);

                if (null == dbTarget.get()) {
                    dbTarget.set(new ArrayList<String>());
                }

                dbTarget.get().add(DataSourceContextHolder.getDbTarget());
                if (null != dataSourceName) {
                    logger.info("数据库切换为" + dataSourceName.value());
                    DataSourceContextHolder.setDbTarget(dataSourceName.value());
                } else {
                    logger.info("没有注解,直接默认数据库: office");
                    DataSourceContextHolder.setDbTarget("office");
                }
            } else {
                logger.info("获取类名为空,数据库为office");
                DataSourceContextHolder.setDbTarget("office");
            }

        } catch (Exception e) {
            logger.error("事务前获取类名出现异常", e);
            DataSourceContextHolder.setDbTarget("office");
        }

        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);

        List<String> dbList = dbTarget.get();

        // 完成之后,把数据库切换为原状态
        logger.info("事务执行完成,把数据库切换为" + (Collections3.isEmpty(dbList) ? null : dbList.get(dbList.size() - 1)));
        DataSourceContextHolder.setDbTarget((Collections3.isEmpty(dbList) ? null : dbList.get(dbList.size() - 1)));
        if (!Collections3.isEmpty(dbList)) {
            dbTarget.get().remove(dbList.size() - 1);
        }
    }

}
