package com.ruoyi.common.utils;

import com.ruoyi.common.enums.DBType;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据库操作工具类
 * 此工具类目的是为了兼容多个数据库
 *
 * @author laihm
 * @date 2021/10/13
 */
@Component
public class DBUtils {

    @Resource
    private VendorDatabaseIdProvider databaseIdProvider;
    @Resource
    private DataSource dataSource;

    public static DBType DB_TYPE;

    public DBUtils() {
    }

    @PostConstruct
    public void init() {
        // 初始化
        String databaseId = databaseIdProvider.getDatabaseId(dataSource);
        DB_TYPE = DBType.valueOf(StringUtils.upperCase(databaseId));
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getCurrentTime(){
        String result = "";
        if (DB_TYPE.equals(DBType.MYSQL)) {
            result = "now()";
        } else if (DB_TYPE.equals(DBType.ORACLE)) {
            result = "sysdate";
        }
        return result;
    }


}