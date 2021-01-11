package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCommisionMapper;
import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.service.ISysCommisionService;

/**
 * 业务提成比例Service业务层处理
 * 
 * @author wonder
 * @date 2020-09-24
 */
@Service
public class SysCommisionServiceImpl implements ISysCommisionService 
{
    @Autowired
    private SysCommisionMapper sysCommisionMapper;

    /**
     * 查询业务提成比例
     * 
     * @param ruleId 业务提成比例ID
     * @return 业务提成比例
     */
    @Override
    public SysCommision selectSysCommisionById(SysCommision sysCommision)
    {
        return sysCommisionMapper.selectSysCommisionById(sysCommision);
    }

    /**
     * 查询业务提成比例列表
     * 
     * @param sysCommision 业务提成比例
     * @return 业务提成比例
     */
    @Override
    public List<SysCommision> selectSysCommisionList(SysCommision sysCommision)
    {
        return sysCommisionMapper.selectSysCommisionList(sysCommision);
    }

    /**
     * 新增业务提成比例
     * 
     * @param sysCommision 业务提成比例
     * @return 结果
     */
    @Override
    public int insertSysCommision(SysCommision sysCommision)
    {
        sysCommision.setCreateTime(DateUtils.getNowDate());
        return sysCommisionMapper.insertSysCommision(sysCommision);
    }

    /**
     * 修改业务提成比例
     * 
     * @param sysCommision 业务提成比例
     * @return 结果
     */
    @Override
    public int updateSysCommision(SysCommision sysCommision)
    {
        sysCommision.setUpdateTime(DateUtils.getNowDate());
        return sysCommisionMapper.updateSysCommision(sysCommision);
    }

    /**
     * 批量删除业务提成比例
     * 
     * @param ruleIds 需要删除的业务提成比例ID
     * @return 结果
     */
    @Override
    public int deleteSysCommisionByIds(Long[] ruleIds)
    {
        return sysCommisionMapper.deleteSysCommisionByIds(ruleIds);
    }

    /**
     * 删除业务提成比例信息
     * 
     * @param ruleId 业务提成比例ID
     * @return 结果
     */
    @Override
    public int deleteSysCommisionById(Long ruleId)
    {
        return sysCommisionMapper.deleteSysCommisionById(ruleId);
    }

    @Override
    public List<SysCommision> selectSysCommisionDetail(SysCommision sysCommision) {
        return sysCommisionMapper.selectSysCommisionDetail(sysCommision);
    }

    @Override
    public List<SysCommision> selectSysCommisionDayDetail(SysCommision sysCommision){
        return sysCommisionMapper.selectSysCommisionDayDetail(sysCommision);
    }
}
