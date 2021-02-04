package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxDistributionMapper;
import com.stdiet.custom.domain.SysWxDistribution;
import com.stdiet.custom.service.ISysWxDistributionService;

/**
 * 微信分配管理Service业务层处理
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@Service
public class SysWxDistributionServiceImpl implements ISysWxDistributionService
{
    @Autowired
    private SysWxDistributionMapper sysWxDistributionMapper;

    /**
     * 查询微信分配管理
     *
     * @param id 微信分配管理ID
     * @return 微信分配管理
     */
    @Override
    public SysWxDistribution selectSysWxDistributionById(Long id)
    {
        return sysWxDistributionMapper.selectSysWxDistributionById(id);
    }

    /**
     * 查询微信分配管理列表
     *
     * @param sysWxDistribution 微信分配管理
     * @return 微信分配管理
     */
    @Override
    public List<SysWxDistribution> selectSysWxDistributionList(SysWxDistribution sysWxDistribution)
    {
        return sysWxDistributionMapper.selectSysWxDistributionList(sysWxDistribution);
    }

    /**
     * 新增微信分配管理
     *
     * @param sysWxDistribution 微信分配管理
     * @return 结果
     */
    @Override
    public int insertSysWxDistribution(SysWxDistribution sysWxDistribution)
    {
        sysWxDistribution.setCreateTime(DateUtils.getNowDate());
        return sysWxDistributionMapper.insertSysWxDistribution(sysWxDistribution);
    }

    /**
     * 修改微信分配管理
     *
     * @param sysWxDistribution 微信分配管理
     * @return 结果
     */
    @Override
    public int updateSysWxDistribution(SysWxDistribution sysWxDistribution)
    {
        sysWxDistribution.setUpdateTime(DateUtils.getNowDate());
        return sysWxDistributionMapper.updateSysWxDistribution(sysWxDistribution);
    }

    /**
     * 批量删除微信分配管理
     *
     * @param ids 需要删除的微信分配管理ID
     * @return 结果
     */
    @Override
    public int deleteSysWxDistributionByIds(Long[] ids)
    {
        return sysWxDistributionMapper.deleteSysWxDistributionByIds(ids);
    }

    /**
     * 删除微信分配管理信息
     *
     * @param id 微信分配管理ID
     * @return 结果
     */
    @Override
    public int deleteSysWxDistributionById(Long id)
    {
        return sysWxDistributionMapper.deleteSysWxDistributionById(id);
    }

    /**
     * 根据微信ID查询是否分配记录
     * @param wxId
     * @return
     */
    @Override
    public SysWxDistribution selectWxDistributionByWxId(Long wxId){
        return sysWxDistributionMapper.selectWxDistributionByWxId(wxId);
    }

    /**
     * 根据用户ID查询该用户被分配的微信号
     * @param userId
     * @return
     */
    public List<SysWxDistribution> selectDistributionWxByUserId(Long userId){
        return sysWxDistributionMapper.selectDistributionWxByUserId(userId);
    }
}