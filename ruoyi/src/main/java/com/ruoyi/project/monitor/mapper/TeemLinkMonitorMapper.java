package com.ruoyi.project.monitor.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.monitor.domain.TeemLinkStat;
import com.ruoyi.project.monitor.domain.TeemLinkTable;
import org.apache.ibatis.annotations.Param;

/**
 * teemlink数据监控
 *
 * @author lihe
 */
@DS("teemlink")
public interface TeemLinkMonitorMapper {

    /**
     * 查看某表的字段数据统计
     *
     * @param tableName
     * @param columnName
     * @return
     */
    TeemLinkStat getColumnStat(@Param("tableName") String tableName, @Param("columnName") String columnName);
}
