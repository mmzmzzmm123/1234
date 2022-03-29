package com.ruoyi.pay.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pay.mapper.SysPayMapper;
import com.ruoyi.pay.domain.SysPay;
import com.ruoyi.pay.service.ISysPayService;

/**
 * 支付相关Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
@Service
public class SysPayServiceImpl implements ISysPayService 
{
    @Autowired
    private SysPayMapper sysPayMapper;

    /**
     * 查询支付相关
     * 
     * @param id 支付相关主键
     * @return 支付相关
     */
    @Override
    public SysPay selectSysPayById(Long id)
    {
        return sysPayMapper.selectSysPayById(id);
    }

    /**
     * 查询支付相关列表
     * 
     * @param sysPay 支付相关
     * @return 支付相关
     */
    @Override
    public List<SysPay> selectSysPayList(SysPay sysPay)
    {
        return sysPayMapper.selectSysPayList(sysPay);
    }

    /**
     * 新增支付相关
     * 
     * @param sysPay 支付相关
     * @return 结果
     */
    @Override
    public int insertSysPay(SysPay sysPay)
    {
        sysPay.setCreateTime(DateUtils.getNowDate());
        return sysPayMapper.insertSysPay(sysPay);
    }

    /**
     * 修改支付相关
     * 
     * @param sysPay 支付相关
     * @return 结果
     */
    @Override
    public int updateSysPay(SysPay sysPay)
    {
        sysPay.setUpdateTime(DateUtils.getNowDate());
        return sysPayMapper.updateSysPay(sysPay);
    }

    /**
     * 批量删除支付相关
     * 
     * @param ids 需要删除的支付相关主键
     * @return 结果
     */
    @Override
    public int deleteSysPayByIds(Long[] ids)
    {
        return sysPayMapper.deleteSysPayByIds(ids);
    }

    /**
     * 删除支付相关信息
     * 
     * @param id 支付相关主键
     * @return 结果
     */
    @Override
    public int deleteSysPayById(Long id)
    {
        return sysPayMapper.deleteSysPayById(id);
    }
}
