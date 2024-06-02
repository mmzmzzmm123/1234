package com.baoli.sysmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BlBizAgentInfoMapper;
import com.baoli.sysmanage.domain.BlBizAgentInfo;
import com.baoli.sysmanage.service.IBlBizAgentInfoService;

/**
 * 经销商档案Service业务层处理
 * 
 * @author niujs
 * @date 2024-03-12
 */
@Service
public class BlBizAgentInfoServiceImpl implements IBlBizAgentInfoService 
{
    @Autowired
    private BlBizAgentInfoMapper blBizAgentInfoMapper;

    /**
     * 查询经销商档案
     * 
     * @param id 经销商档案主键
     * @return 经销商档案
     */
    @Override
    public BlBizAgentInfo selectBlBizAgentInfoById(Long id)
    {
        return blBizAgentInfoMapper.selectBlBizAgentInfoById(id);
    }

    /**
     * 查询经销商档案列表
     * 
     * @param blBizAgentInfo 经销商档案
     * @return 经销商档案
     */
    @Override
    public List<BlBizAgentInfo> selectBlBizAgentInfoList(BlBizAgentInfo blBizAgentInfo)
    {
        return blBizAgentInfoMapper.selectBlBizAgentInfoList(blBizAgentInfo);
    }

    /**
     * 新增经销商档案
     * 
     * @param blBizAgentInfo 经销商档案
     * @return 结果
     */
    @Override
    public int insertBlBizAgentInfo(BlBizAgentInfo blBizAgentInfo)
    {
        blBizAgentInfo.setCreateTime(DateUtils.getNowDate());
        return blBizAgentInfoMapper.insertBlBizAgentInfo(blBizAgentInfo);
    }

    /**
     * 修改经销商档案
     * 
     * @param blBizAgentInfo 经销商档案
     * @return 结果
     */
    @Override
    public int updateBlBizAgentInfo(BlBizAgentInfo blBizAgentInfo)
    {
        blBizAgentInfo.setUpdateTime(DateUtils.getNowDate());
        return blBizAgentInfoMapper.updateBlBizAgentInfo(blBizAgentInfo);
    }

    /**
     * 批量删除经销商档案
     * 
     * @param ids 需要删除的经销商档案主键
     * @return 结果
     */
    @Override
    public int deleteBlBizAgentInfoByIds(Long[] ids)
    {
        return blBizAgentInfoMapper.deleteBlBizAgentInfoByIds(ids);
    }

    /**
     * 删除经销商档案信息
     * 
     * @param id 经销商档案主键
     * @return 结果
     */
    @Override
    public int deleteBlBizAgentInfoById(Long id)
    {
        return blBizAgentInfoMapper.deleteBlBizAgentInfoById(id);
    }
}
