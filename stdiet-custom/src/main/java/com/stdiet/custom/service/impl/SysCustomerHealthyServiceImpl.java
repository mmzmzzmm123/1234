package com.stdiet.custom.service.impl;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.bean.ObjectUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.service.ISysCustomerService;
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

    @Autowired
    private ISysCustomerService sysCustomerService;

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
    public AjaxResult insertSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy){
        //客户ID解密
        String customerId = StringUtils.isNotEmpty(sysCustomerHealthy.getCustomerEncId()) ? AesUtils.decrypt(sysCustomerHealthy.getCustomerEncId(), null) : "";
        if(sysCustomerHealthy.getCustomerId() == null && StringUtils.isEmpty(customerId)){
            return AjaxResult.error("客户不存在");
        }
        //判断客户是否存在
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(Long.parseLong(customerId));
        if(sysCustomer == null){
            return AjaxResult.error("客户不存在");
        }
        //判断是否已存在客户健康评估
        SysCustomerHealthy customerHealthy = selectSysCustomerHealthyByCustomerId(Long.parseLong(customerId));
        if(customerHealthy != null){
            return AjaxResult.error("已存在健康评估信息，无法重复添加");
        }
        //设置客户ID
        sysCustomerHealthy.setCustomerId(Long.parseLong(customerId));
        int rows = sysCustomerHealthyMapper.insertSysCustomerHealthy(sysCustomerHealthy);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 编辑客户健康
     * @param sysCustomerHealthy
     * @return
     */
    @Override
    public int updateSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy){
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

    /**
     * 根据手机号查询客户健康评估信息
     * @param phone
     */
    @Override
    public SysCustomerHealthy selectSysCustomerHealthyByPhone(String phone){
        return sysCustomerHealthyMapper.selectSysCustomerHealthyByPhone(phone);
    }

    /**
     * 根据客户ID查询健康评估表信息
     */
    public SysCustomerHealthy selectSysCustomerHealthyByCustomerId(Long customerId){
        return sysCustomerHealthyMapper.selectSysCustomerHealthyByCustomerId(customerId);
    }

    /**
     * 根据客户ID删除客户健康评估信息
     * @param customerId
     * @return
     */
    public int deleteCustomerHealthyByCustomerId(Long customerId){
        return sysCustomerHealthyMapper.deleteCustomerHealthyByCustomerId(customerId);
    }
}