package com.thinkgem.jeesite.common.db;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jiajianhong
 *
 */
public class DataSourceContextHolder {
    private static Logger log = Logger.getLogger(DataSourceContextHolder.class);
    // 存储DBKEY
    private static final ThreadLocal<Object> dbkeyHolder = new ThreadLocal<Object>();
    // 存储表index
    private static final ThreadLocal<Object> tableindexHolder = new ThreadLocal<Object>();
    // 所有库和所有表的分布
    public static final Map<Integer, List<Integer>> dbEntities = new HashMap<Integer, List<Integer>>();
    // 从配置文件中读取数据库的数量
    public static final int DB_SHARDING_COUNT = 2;
    // 从数据库中读取数据库表的数量
    public static final int TABLE_SHARDING_COUNT = 2;

    public static List<DbInfo> dbInfoList = new ArrayList<DbInfo>();

    static {
        for (int i = 0; i < DB_SHARDING_COUNT; i++) {
            List<Integer> tableList = new ArrayList<Integer>();
            for (int j = 0; j < TABLE_SHARDING_COUNT; j++) {
                tableList.add(j);
            }
            dbEntities.put(i, tableList);
        }

    }

    /**
     * 根据订单号计算数据库索引值
     * @param orderID
     * @return
     */
    public static int calculateDBKey(long orderID) {
        return (int) (orderID % DB_SHARDING_COUNT);
    }

    /**
     * 根据订单号计算数据库表的索引值
     * @param orderID
     * @return
     */
    public static int calculateTableKey(long orderID) {

        return (int) ((orderID / DB_SHARDING_COUNT) % TABLE_SHARDING_COUNT);
    }

    /**
     * 设置数据库对象
     * @param dbType
     */
    public static void setDbTarget(String dbType) {

        dbkeyHolder.set(dbType);

    }

    /**
     * 获取数据库对象
     * @return
     */
    public static String getDbTarget() {

        String dbTarget = (String) dbkeyHolder.get();

        if (StringUtils.isEmpty(dbTarget)) {
            return "office";
        } else {
            return dbTarget;
        }

//        return (String) dbkeyHolder.get();

    }

    /**
     * 清除设置的数据库对象
     */
    public static void clearDbTarget() {

        dbkeyHolder.remove();

    }

    /**
     * 设置数据库表对象
     * @param tableIndex
     */
    public static void setTableIndex(int tableIndex) {
        tableindexHolder.set(tableIndex);
    }

    /**
     * 获取数据库表对象
     * @return
     */
    public static int getTableIndex() {
        return (Integer) tableindexHolder.get();
    }

}
