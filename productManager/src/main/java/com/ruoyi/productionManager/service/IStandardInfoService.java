package com.ruoyi.productionManager.service;

import com.ruoyi.productionManager.domain.StandardInfo;

import java.util.List;

/**
 * 标准信息Service接口
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
public interface IStandardInfoService 
{
    /**
     * 查询标准信息
     * 
     * @param standardId 标准信息主键
     * @return 标准信息
     */
    public StandardInfo selectStandardInfoByStandardId(Long standardId);

    /**
     * 查询标准信息列表
     * 
     * @param standardInfo 标准信息
     * @return 标准信息集合
     */
    public List<StandardInfo> selectStandardInfoList(StandardInfo standardInfo);

    /**
     * 新增标准信息
     * 
     * @param standardInfo 标准信息
     * @return 结果
     */
    public int insertStandardInfo(StandardInfo standardInfo);

    /**
     * 修改标准信息
     * 
     * @param standardInfo 标准信息
     * @return 结果
     */
    public int updateStandardInfo(StandardInfo standardInfo);

    /**
     * 批量删除标准信息
     * 
     * @param standardIds 需要删除的标准信息主键集合
     * @return 结果
     */
    public int deleteStandardInfoByStandardIds(Long[] standardIds);

    /**
     * 删除标准信息信息
     * 
     * @param standardId 标准信息主键
     * @return 结果
     */
    public int deleteStandardInfoByStandardId(Long standardId);
}
