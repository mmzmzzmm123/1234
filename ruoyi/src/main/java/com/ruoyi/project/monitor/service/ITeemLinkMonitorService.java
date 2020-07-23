package com.ruoyi.project.monitor.service;

import com.ruoyi.project.monitor.domain.SysOperLog;
import com.ruoyi.project.monitor.domain.TeemLinkStat;

import java.util.List;

/**
 * teemlink 服务层
 *
 * @author ruoyi
 */
public interface ITeemLinkMonitorService {
    /**
     * 数据统计
     *
     * @param tableName
     * @param columnName
     * @return
     */
    TeemLinkStat stat(String tableName, String columnName);
}
