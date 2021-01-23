package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerHealthyMapper;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.service.ISysCustomerHealthyService;

/**
 * 客户健康Service业务层处理
 *
 * @author xzj
 * @date 2021-01-23
 */
@Service
public class SysCustomerHealthyServiceImpl implements ISysCustomerHealthyService
{
    @Autowired
    private SysCustomerHealthyMapper sysCustomerHealthyMapper;

    /**
     * 查询客户健康
     *
     * @param id 客户健康ID
     * @return 客户健康
     */
    @Override
    public SysCustomerHealthy selectSysCustomerHealthyById(Long id)
    {
        return sysCustomerHealthyMapper.selectSysCustomerHealthyById(id);
    }

    /**
     * 查询客户健康列表
     *
     * @param sysCustomerHealthy 客户健康
     * @return 客户健康
     */
    @Override
    public List<SysCustomerHealthy> selectSysCustomerHealthyList(SysCustomerHealthy sysCustomerHealthy)
    {
        return sysCustomerHealthyMapper.selectSysCustomerHealthyList(sysCustomerHealthy);
    }

    /**
     * 新增客户健康
     *
     * @param sysCustomerHealthy 客户健康
     * @return 结果
     */
    @Override
    public int insertSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy)
    {
        sysCustomerHealthy.setCreateTime(DateUtils.getNowDate());
        return sysCustomerHealthyMapper.insertSysCustomerHealthy(sysCustomerHealthy);
    }

    /**
     * 修改客户健康
     *
     * @param sysCustomerHealthy 客户健康
     * @return 结果
     */
    @Override
    public int updateSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy)
    {
        sysCustomerHealthy.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerHealthyMapper.updateSysCustomerHealthy(sysCustomerHealthy);
    }

    /**
     * 批量删除客户健康
     *
     * @param ids 需要删除的客户健康ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerHealthyByIds(Long[] ids)
    {
        return sysCustomerHealthyMapper.deleteSysCustomerHealthyByIds(ids);
    }

    /**
     * 删除客户健康信息
     *
     * @param id 客户健康ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerHealthyById(Long id)
    {
        return sysCustomerHealthyMapper.deleteSysCustomerHealthyById(id);
    }
}