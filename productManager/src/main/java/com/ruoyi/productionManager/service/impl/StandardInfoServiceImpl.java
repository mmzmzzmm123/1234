package com.ruoyi.productionManager.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.productionManager.domain.StandardInfo;
import com.ruoyi.productionManager.mapper.StandardInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.productionManager.service.IStandardInfoService;

/**
 * 标准信息Service业务层处理
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
     * 查询标准信息
     * 
     * @param standardId 标准信息主键
     * @return 标准信息
     */
    @Override
    public StandardInfo selectStandardInfoByStandardId(Long standardId)
    {
        return standardInfoMapper.selectStandardInfoByStandardId(standardId);
    }

    /**
     * 查询标准信息列表
     * 
     * @param standardInfo 标准信息
     * @return 标准信息
     */
    @Override
    public List<StandardInfo> selectStandardInfoList(StandardInfo standardInfo)
    {
        return standardInfoMapper.selectStandardInfoList(standardInfo);
    }

    /**
     * 新增标准信息
     * 
     * @param standardInfo 标准信息
     * @return 结果
     */
    @Override
    public int insertStandardInfo(StandardInfo standardInfo)
    {
        standardInfo.setCreateTime(DateUtils.getNowDate());
        return standardInfoMapper.insertStandardInfo(standardInfo);
    }

    /**
     * 修改标准信息
     * 
     * @param standardInfo 标准信息
     * @return 结果
     */
    @Override
    public int updateStandardInfo(StandardInfo standardInfo)
    {
        standardInfo.setUpdateTime(DateUtils.getNowDate());
        return standardInfoMapper.updateStandardInfo(standardInfo);
    }

    /**
     * 批量删除标准信息
     * 
     * @param standardIds 需要删除的标准信息主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoByStandardIds(Long[] standardIds)
    {
        return standardInfoMapper.deleteStandardInfoByStandardIds(standardIds);
    }

    /**
     * 删除标准信息信息
     * 
     * @param standardId 标准信息主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoByStandardId(Long standardId)
    {
        return standardInfoMapper.deleteStandardInfoByStandardId(standardId);
    }
}
