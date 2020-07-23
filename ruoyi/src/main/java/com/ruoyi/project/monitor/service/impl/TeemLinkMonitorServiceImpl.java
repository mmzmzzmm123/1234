package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.project.monitor.domain.TeemLinkStat;
import com.ruoyi.project.monitor.mapper.TeemLinkMonitorMapper;
import com.ruoyi.project.monitor.service.ITeemLinkMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TeemLinkMonitorServiceImpl implements ITeemLinkMonitorService {
    @Autowired
    private TeemLinkMonitorMapper teemLinkMonitorMapper;

    @Override
    public TeemLinkStat stat(String tableName, String columnName) {
        return teemLinkMonitorMapper.getColumnStat(tableName, columnName);
    }
}
