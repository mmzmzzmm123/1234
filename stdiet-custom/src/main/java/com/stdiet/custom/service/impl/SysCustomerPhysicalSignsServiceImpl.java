package com.stdiet.custom.service.impl;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.bean.ObjectUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.dto.request.CustomerInvestigateRequest;
import com.stdiet.custom.service.ISysCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.service.ISysCustomerPhysicalSignsService;

/**
 * 客户体征信息Service业务层处理
 *
 * @author xzj
 * @date 2020-12-31
 */
@Service
public class SysCustomerPhysicalSignsServiceImpl implements ISysCustomerPhysicalSignsService
{
    @Autowired
    private SysCustomerPhysicalSignsMapper sysCustomerPhysicalSignsMapper;

    @Autowired
    private ISysCustomerService sysCustomerService;

    /**
     * 查询客户体征信息
     *
     * @param id 客户体征信息ID
     * @return 客户体征信息
     */
    @Override
    public SysCustomerPhysicalSigns selectSysCustomerPhysicalSignsById(Long id)
    {
        return sysCustomerPhysicalSignsMapper.selectSysCustomerPhysicalSignsById(id);
    }

    @Override
    public SysCustomerPhysicalSigns selectSysCustomerPhysicalSignsByCusId(Long cusId) {
        return sysCustomerPhysicalSignsMapper.selectSysCustomerPhysicalSignsByCusId(cusId);
    }

    /**
     * 新增客户体征信息
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 结果
     */
    @Override
    public int insertSysCustomerPhysicalSigns(SysCustomerPhysicalSigns sysCustomerPhysicalSigns)
    {
        sysCustomerPhysicalSigns.setCreateTime(DateUtils.getNowDate());
        return sysCustomerPhysicalSignsMapper.insertSysCustomerPhysicalSigns(sysCustomerPhysicalSigns);
    }

    /**
     * 修改客户体征信息
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 结果
     */
    @Override
    public int updateSysCustomerPhysicalSigns(SysCustomerPhysicalSigns sysCustomerPhysicalSigns)
    {
        sysCustomerPhysicalSigns.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerPhysicalSignsMapper.updateSysCustomerPhysicalSigns(sysCustomerPhysicalSigns);
    }

    /**
     * 批量删除客户体征信息
     *
     * @param ids 需要删除的客户体征信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerPhysicalSignsByIds(Long[] ids)
    {
        return sysCustomerPhysicalSignsMapper.deleteSysCustomerPhysicalSignsByIds(ids);
    }

    /**
     * 删除客户体征信息信息
     *
     * @param id 客户体征信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerPhysicalSignsById(Long id)
    {
        return sysCustomerPhysicalSignsMapper.deleteSysCustomerPhysicalSignsById(id);
    }

    /**
     * 客户体征资料填写
     *
     * @param customerInvestigateRequest 客户体征资料
     * @return 结果
     */
    public AjaxResult addOrupdateCustomerAndSign(CustomerInvestigateRequest customerInvestigateRequest) throws Exception{
        //当前登录用户
        //LoginUser loginUser = SecurityUtils.getLoginUser();
        Long cutomerId = 0L;
        //新增
        if(customerInvestigateRequest.getId() == null){
            //根据手机号查询客户体征信息，判断是否已存在
            SysCustomerPhysicalSigns sysCustomerPhysicalSigns = selectSysCustomerAndSignByPhone(customerInvestigateRequest.getPhone());
            if(sysCustomerPhysicalSigns != null){
                return AjaxResult.error("该手机号已存在");
            }
            //根据手机号查询客户基础信息，判断是否已存在
            SysCustomer phoneCustomer = sysCustomerService.getCustomerByPhone(customerInvestigateRequest.getPhone());
            if(phoneCustomer != null){
                if(!customerInvestigateRequest.getName().equals(phoneCustomer.getName())){
                    return AjaxResult.error("该手机号与姓名不符");
                }
                cutomerId = phoneCustomer.getId();
            }else{
                SysCustomer newSysCustomer  = new SysCustomer();
                newSysCustomer.setName(customerInvestigateRequest.getName());
                newSysCustomer.setPhone(customerInvestigateRequest.getPhone());
                if(sysCustomerService.insertSysCustomer(newSysCustomer) > 0){
                    cutomerId = newSysCustomer.getId();
                }
            }
        }
        //更新
        else{
            SysCustomer oldCustomer = sysCustomerService.selectSysCustomerById(customerInvestigateRequest.getCustomerId());
            //如果修改了手机号，则需要判断手机号是否已经存在
            if(oldCustomer != null && !oldCustomer.getPhone().equals(customerInvestigateRequest.getPhone())) {
                //验证该手机号是否已存在客户体征信息
                SysCustomerPhysicalSigns sysCustomerPhysicalSigns = selectSysCustomerAndSignByPhone(customerInvestigateRequest.getPhone());
                if (sysCustomerPhysicalSigns != null) {
                    return AjaxResult.error("该手机号已存在");
                }
                //根据手机号查询客户基础信息，判断是否已存在
                SysCustomer phoneCustomer = sysCustomerService.getCustomerByPhone(customerInvestigateRequest.getPhone());
                if(phoneCustomer != null) {
                    return AjaxResult.error("该手机号已存在");
                }
            }
            SysCustomer newSysCustomer  = new SysCustomer();
            newSysCustomer.setId(customerInvestigateRequest.getCustomerId());
            newSysCustomer.setName(customerInvestigateRequest.getName());
            newSysCustomer.setPhone(customerInvestigateRequest.getPhone());
            if(sysCustomerService.updateSysCustomer(newSysCustomer) > 0){
                cutomerId = newSysCustomer.getId();
            }
        }
        int rows = 0;
        if(cutomerId != null && cutomerId > 0){
            SysCustomerPhysicalSigns customerSigns = ObjectUtils.getObjectByObject(customerInvestigateRequest, SysCustomerPhysicalSigns.class);
            customerSigns.setCustomerId(cutomerId);
            customerSigns.setId(customerInvestigateRequest.getId());
            rows = customerInvestigateRequest.getId() == null ? insertSysCustomerPhysicalSigns(customerSigns) : updateSysCustomerPhysicalSigns(customerSigns);
        }
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     *  查询客户基础信息以及体征信息列表
     *
     * @param sysCustomerPhysicalSigns 查询条件
     * @return 结果
     */
    public List<SysCustomerPhysicalSigns> selectSysCustomerAndSignList(SysCustomerPhysicalSigns sysCustomerPhysicalSigns){
        return sysCustomerPhysicalSignsMapper.selectSysCustomerAndSignList(sysCustomerPhysicalSigns);
    }

    /**
     * 根据手机号查询客户以及体征
     * @param phone
     * @return
     */
    @Override
    public SysCustomerPhysicalSigns  selectSysCustomerAndSignByPhone(String phone){
        return sysCustomerPhysicalSignsMapper.selectSysCustomerAndSignByPhone(phone);
    }

    /**
     * 根据客户ID删除对应体征信息
     * @param customerId
     * @return
     */
    @Override
    public int delCustomerSignByCustomerId(Long customerId){
        return sysCustomerPhysicalSignsMapper.delCustomerSignByCustomerId(customerId);
    }

    /**
     * 查询客户性别
     * @param cusId
     * @return
     */
    @Override
    public Integer getCustomerSexByCusId(Long cusId){
        return sysCustomerPhysicalSignsMapper.getCustomerSexByCusId(cusId);
    }
}