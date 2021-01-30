package com.stdiet.custom.service.impl;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.bean.ObjectUtils;
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
    @Override
    public AjaxResult insertOrUpdateSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy)
    {
        //当前登录用户
        //LoginUser loginUser = SecurityUtils.getLoginUser();
        Long cutomerId = 0L;
        //新增
        if(sysCustomerHealthy.getId() == null){
            //根据手机号查询客户健康信息，判断是否已存在
            SysCustomerHealthy customerHealthy = selectSysCustomerHealthyByPhone(sysCustomerHealthy.getPhone());
            if(customerHealthy != null){
                return AjaxResult.error("该手机号已存在");
            }
            //根据手机号查询客户基础信息，判断是否已存在
            SysCustomer phoneCustomer = sysCustomerService.getCustomerByPhone(sysCustomerHealthy.getPhone());
            if(phoneCustomer != null){
                if(!sysCustomerHealthy.getName().equals(phoneCustomer.getName())){
                    return AjaxResult.error("该手机号与姓名不符");
                }
                cutomerId = phoneCustomer.getId();
            }else{
                SysCustomer newSysCustomer  = new SysCustomer();
                newSysCustomer.setName(sysCustomerHealthy.getName());
                newSysCustomer.setPhone(sysCustomerHealthy.getPhone());
                if(sysCustomerService.insertSysCustomer(newSysCustomer) > 0){
                    cutomerId = newSysCustomer.getId();
                }
            }
        }
        //更新
        else{
            SysCustomer oldCustomer = sysCustomerService.selectSysCustomerById(sysCustomerHealthy.getCustomerId());
            //如果修改了手机号，则需要判断手机号是否已经存在
            if(oldCustomer != null && !oldCustomer.getPhone().equals(sysCustomerHealthy.getPhone())) {
                //验证该手机号是否已存在客户健康信息
                SysCustomerHealthy customerHealthy = selectSysCustomerHealthyByPhone(sysCustomerHealthy.getPhone());
                if (customerHealthy != null) {
                    return AjaxResult.error("该手机号已存在");
                }
                //根据手机号查询客户基础信息，判断是否已存在
                SysCustomer phoneCustomer = sysCustomerService.getCustomerByPhone(sysCustomerHealthy.getPhone());
                if(phoneCustomer != null) {
                    return AjaxResult.error("该手机号已存在");
                }
            }
            SysCustomer newSysCustomer  = new SysCustomer();
            newSysCustomer.setId(sysCustomerHealthy.getCustomerId());
            newSysCustomer.setName(sysCustomerHealthy.getName());
            newSysCustomer.setPhone(sysCustomerHealthy.getPhone());
            if(sysCustomerService.updateSysCustomer(newSysCustomer) > 0){
                cutomerId = newSysCustomer.getId();
            }
        }
        int rows = 0;
        if(cutomerId != null && cutomerId > 0){
            sysCustomerHealthy.setCustomerId(cutomerId);
            rows = sysCustomerHealthy.getId() == null ? sysCustomerHealthyMapper.insertSysCustomerHealthy(sysCustomerHealthy) : sysCustomerHealthyMapper.updateSysCustomerHealthy(sysCustomerHealthy);
        }
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
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
}