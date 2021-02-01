package com.stdiet.custom.service;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.dto.request.CustomerInvestigateRequest;
import org.apache.ibatis.annotations.Param;

/**
 * 客户体征信息Service接口
 *
 * @author xzj
 * @date 2020-12-31
 */
public interface ISysCustomerPhysicalSignsService {
    /**
     * 查询客户体征信息
     *
     * @param id 客户体征信息ID
     * @return 客户体征信息
     */
    public SysCustomerPhysicalSigns selectSysCustomerPhysicalSignsById(Long id);

    /**
     * 根据客户ID查询体征数据
     * @param id
     * @return
     */
    public SysCustomerPhysicalSigns selectSysCustomerPhysicalSignsByCusId(Long id);

    /**
     * 新增客户体征信息
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 结果
     */
    public int insertSysCustomerPhysicalSigns(SysCustomerPhysicalSigns sysCustomerPhysicalSigns);

    /**
     * 修改客户体征信息
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 结果
     */
    public int updateSysCustomerPhysicalSigns(SysCustomerPhysicalSigns sysCustomerPhysicalSigns);

    /**
     * 批量删除客户体征信息
     *
     * @param ids 需要删除的客户体征信息ID
     * @return 结果
     */
    public int deleteSysCustomerPhysicalSignsByIds(Long[] ids);

    /**
     * 删除客户体征信息信息
     *
     * @param id 客户体征信息ID
     * @return 结果
     */
    public int deleteSysCustomerPhysicalSignsById(Long id);

    /**
     * 客户体征资料填写
     *
     * @param customerInvestigateRequest 客户建档相关资料
     * @return 结果
     */
    AjaxResult addOrupdateCustomerAndSign(CustomerInvestigateRequest customerInvestigateRequest) throws Exception;

    /**
     *  查询客户基础信息以及体征信息列表
     *
     * @param sysCustomerPhysicalSigns 查询条件
     * @return 结果
     */
    List<SysCustomerPhysicalSigns> selectSysCustomerAndSignList(SysCustomerPhysicalSigns sysCustomerPhysicalSigns);

    /**
     * 根据手机号查询客户以及体征
     * @param phone
     * @return
     */
    SysCustomerPhysicalSigns  selectSysCustomerAndSignByPhone(String phone);

    /**
     * 根据客户ID删除对应体征信息
     * @param customerId
     * @return
     */
    int delCustomerSignByCustomerId(Long customerId);

}