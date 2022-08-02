package com.ruoyi.flyway.mapper;

import com.ruoyi.flyway.domain.FlywaySchemaHistory;

import java.util.List;

/**
 * flyway管理Mapper接口
 * 
 * @author yangwb
 * @date 2022-07-07
 */
public interface FlywaySchemaHistoryMapper 
{
    /**
     * 查询flyway管理
     * 
     * @param installedRank flyway管理主键
     * @return flyway管理
     */
    public FlywaySchemaHistory selectFlywaySchemaHistoryByInstalledRank(Long installedRank);

    /**
     * 查询flyway管理列表
     * 
     * @param flywaySchemaHistory flyway管理
     * @return flyway管理集合
     */
    public List<FlywaySchemaHistory> selectFlywaySchemaHistoryList(FlywaySchemaHistory flywaySchemaHistory);

    /**
     * 新增flyway管理
     * 
     * @param flywaySchemaHistory flyway管理
     * @return 结果
     */
    public int insertFlywaySchemaHistory(FlywaySchemaHistory flywaySchemaHistory);

    /**
     * 修改flyway管理
     * 
     * @param flywaySchemaHistory flyway管理
     * @return 结果
     */
    public int updateFlywaySchemaHistory(FlywaySchemaHistory flywaySchemaHistory);

    /**
     * 删除flyway管理
     * 
     * @param installedRank flyway管理主键
     * @return 结果
     */
    public int deleteFlywaySchemaHistoryByInstalledRank(Long installedRank);

    /**
     * 批量删除flyway管理
     * 
     * @param installedRanks 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFlywaySchemaHistoryByInstalledRanks(Long[] installedRanks);
}
