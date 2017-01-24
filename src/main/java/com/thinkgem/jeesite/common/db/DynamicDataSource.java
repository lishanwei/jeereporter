package com.thinkgem.jeesite.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Iterator;
import java.util.Map;

/**
 * @author jiajianhong
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /** 
     * determineCurrentLookupKey
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return (String) com.thinkgem.jeesite.common.db.DataSourceContextHolder.getDbTarget();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        // 把这些值也得保存起来
        Iterator<Object> keyIt = targetDataSources.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = (String) keyIt.next();

            DbInfo dbInfo = new DbInfo();
            dbInfo.setKey(key);
            dbInfo.setName(key);

            DataSourceContextHolder.dbInfoList.add(dbInfo);
        }
    }

}
