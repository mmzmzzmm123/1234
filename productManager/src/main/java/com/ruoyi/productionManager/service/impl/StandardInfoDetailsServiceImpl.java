package com.ruoyi.productionManager.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.productionManager.mapper.StandardInfoDetailsMapper;
import com.ruoyi.productionManager.domain.StandardInfoDetails;
import com.ruoyi.productionManager.service.IStandardInfoDetailsService;

/**
 * 标准信息文件Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-18
 */
@Service
public class StandardInfoDetailsServiceImpl implements IStandardInfoDetailsService 
{
    @Autowired
    private StandardInfoDetailsMapper standardInfoDetailsMapper;

    /**
     * 查询标准信息文件
     * 
     * @param detailsId 标准信息文件主键
     * @return 标准信息文件
     */
    @Override
    public StandardInfoDetails selectStandardInfoDetailsByDetailsId(Long detailsId)
    {
        return standardInfoDetailsMapper.selectStandardInfoDetailsByDetailsId(detailsId);
    }

    /**
     * 查询标准信息文件列表
     * 
     * @param standardInfoDetails 标准信息文件
     * @return 标准信息文件
     */
    @Override
    public List<StandardInfoDetails> selectStandardInfoDetailsList(StandardInfoDetails standardInfoDetails)
    {
        return standardInfoDetailsMapper.selectStandardInfoDetailsList(standardInfoDetails);
    }

    /**
     * 新增标准信息文件
     * 
     * @param standardInfoDetails 标准信息文件
     * @return 结果
     */
    @Override
    public int insertStandardInfoDetails(StandardInfoDetails standardInfoDetails)
    {
        standardInfoDetails.setCreateTime(DateUtils.getNowDate());
        return standardInfoDetailsMapper.insertStandardInfoDetails(standardInfoDetails);
    }

    /**
     * 修改标准信息文件
     * 
     * @param standardInfoDetails 标准信息文件
     * @return 结果
     */
    @Override
    public int updateStandardInfoDetails(StandardInfoDetails standardInfoDetails)
    {
        standardInfoDetails.setUpdateTime(DateUtils.getNowDate());
        return standardInfoDetailsMapper.updateStandardInfoDetails(standardInfoDetails);
    }

    /**
     * 批量删除标准信息文件
     * 
     * @param detailsIds 需要删除的标准信息文件主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoDetailsByDetailsIds(Long[] detailsIds)
    {
        return standardInfoDetailsMapper.deleteStandardInfoDetailsByDetailsIds(detailsIds);
    }

    /**
     * 删除标准信息文件信息
     * 
     * @param detailsId 标准信息文件主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoDetailsByDetailsId(Long detailsId)
    {
        return standardInfoDetailsMapper.deleteStandardInfoDetailsByDetailsId(detailsId);
    }
}
