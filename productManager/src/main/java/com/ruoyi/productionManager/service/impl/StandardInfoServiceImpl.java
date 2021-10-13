package com.ruoyi.productionManager.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.productionManager.mapper.StandardInfoMapper;
import com.ruoyi.productionManager.domain.StandardInfo;
import com.ruoyi.productionManager.service.IStandardInfoService;

/**
 * 试验标准管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
@Service
public class StandardInfoServiceImpl implements IStandardInfoService 
{
    @Autowired
    private StandardInfoMapper standardInfoMapper;

    /**
     * 查询试验标准管理
     * 
     * @param standardId 试验标准管理主键
     * @return 试验标准管理
     */
    @Override
    public StandardInfo selectStandardInfoByStandardId(Long standardId)
    {
        return standardInfoMapper.selectStandardInfoByStandardId(standardId);
    }

    /**
     * 查询试验标准管理列表
     * 
     * @param standardInfo 试验标准管理
     * @return 试验标准管理
     */
    @Override
    public List<StandardInfo> selectStandardInfoList(StandardInfo standardInfo)
    {
        return standardInfoMapper.selectStandardInfoList(standardInfo);
    }

    /**
     * 新增试验标准管理
     * 
     * @param standardInfo 试验标准管理
     * @return 结果
     */
    @Override
    public int insertStandardInfo(StandardInfo standardInfo)
    {
        standardInfo.setCreateTime(DateUtils.getNowDate());
        return standardInfoMapper.insertStandardInfo(standardInfo);
    }

    /**
     * 修改试验标准管理
     * 
     * @param standardInfo 试验标准管理
     * @return 结果
     */
    @Override
    public int updateStandardInfo(StandardInfo standardInfo)
    {
        standardInfo.setUpdateTime(DateUtils.getNowDate());
        return standardInfoMapper.updateStandardInfo(standardInfo);
    }

    /**
     * 批量删除试验标准管理
     * 
     * @param standardIds 需要删除的试验标准管理主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoByStandardIds(Long[] standardIds)
    {
        return standardInfoMapper.deleteStandardInfoByStandardIds(standardIds);
    }

    /**
     * 删除试验标准管理信息
     * 
     * @param standardId 试验标准管理主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoByStandardId(Long standardId)
    {
        return standardInfoMapper.deleteStandardInfoByStandardId(standardId);
    }
}
