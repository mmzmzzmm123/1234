package com.ruoyi.flyway.service.impl;

import com.ruoyi.flyway.domain.FlywaySchemaHistory;
import com.ruoyi.flyway.mapper.FlywaySchemaHistoryMapper;
import com.ruoyi.flyway.service.IFlywaySchemaHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * flyway管理Service业务层处理
 * 
 * @author yangwb
 * @date 2022-07-07
 */
@Service
public class FlywaySchemaHistoryServiceImpl implements IFlywaySchemaHistoryService
{
    @Autowired
    private FlywaySchemaHistoryMapper flywaySchemaHistoryMapper;

    /**
     * 查询flyway管理
     * 
     * @param installedRank flyway管理主键
     * @return flyway管理
     */
    @Override
    public FlywaySchemaHistory selectFlywaySchemaHistoryByInstalledRank(Long installedRank)
    {
        return flywaySchemaHistoryMapper.selectFlywaySchemaHistoryByInstalledRank(installedRank);
    }

    /**
     * 查询flyway管理列表
     * 
     * @param flywaySchemaHistory flyway管理
     * @return flyway管理
     */
    @Override
    public List<FlywaySchemaHistory> selectFlywaySchemaHistoryList(FlywaySchemaHistory flywaySchemaHistory)
    {
        return flywaySchemaHistoryMapper.selectFlywaySchemaHistoryList(flywaySchemaHistory);
    }

    /**
     * 新增flyway管理
     * 
     * @param flywaySchemaHistory flyway管理
     * @return 结果
     */
    @Override
    public int insertFlywaySchemaHistory(FlywaySchemaHistory flywaySchemaHistory)
    {
        return flywaySchemaHistoryMapper.insertFlywaySchemaHistory(flywaySchemaHistory);
    }

    /**
     * 修改flyway管理
     * 
     * @param flywaySchemaHistory flyway管理
     * @return 结果
     */
    @Override
    public int updateFlywaySchemaHistory(FlywaySchemaHistory flywaySchemaHistory)
    {
        return flywaySchemaHistoryMapper.updateFlywaySchemaHistory(flywaySchemaHistory);
    }

    /**
     * 批量删除flyway管理
     * 
     * @param installedRanks 需要删除的flyway管理主键
     * @return 结果
     */
    @Override
    public int deleteFlywaySchemaHistoryByInstalledRanks(Long[] installedRanks)
    {
        return flywaySchemaHistoryMapper.deleteFlywaySchemaHistoryByInstalledRanks(installedRanks);
    }

    /**
     * 删除flyway管理信息
     * 
     * @param installedRank flyway管理主键
     * @return 结果
     */
    @Override
    public int deleteFlywaySchemaHistoryByInstalledRank(Long installedRank)
    {
        return flywaySchemaHistoryMapper.deleteFlywaySchemaHistoryByInstalledRank(installedRank);
    }
}
