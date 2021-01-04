package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.dto.request.CustomerInvestigateRequest;

/**
 * 客户信息Service接口
 *
 * @author xzj
 * @date 2020-12-31
 */
public interface ISysCustomerService
{
    /**
     * 查询客户信息
     *
     * @param id 客户信息ID
     * @return 客户信息
     */
    public SysCustomer selectSysCustomerById(Long id);

    /**
     * 查询客户信息列表
     *
     * @param sysCustomer 客户信息
     * @return 客户信息集合
     */
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer);

    /**
     * 新增客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    public int insertSysCustomer(SysCustomer sysCustomer);

    /**
     * 修改客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    public int updateSysCustomer(SysCustomer sysCustomer);

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息ID
     * @return 结果
     */
    public int deleteSysCustomerByIds(Long[] ids);

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息ID
     * @return 结果
     */
    public int deleteSysCustomerById(Long id);

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return 结果
     */
    SysCustomer getCustomerByPhone(String phone);

    /**
     * 客户建档资料填写
     *
     * @param customerInvestigateRequest 客户建档相关资料
     * @return 结果
     */
    int addOrupdateCustomerAndSign( CustomerInvestigateRequest customerInvestigateRequest) throws Exception;

    /**
     *  查询客户基础信息以及体征信息列表
     *
     * @param sysCustomer 查询条件
     * @return 结果
     */
    List<SysCustomer> selectSysCustomerAndSignList(SysCustomer sysCustomer);

    /**
     *  根据id查询客户信息（基础信息以及体征信息）
     *
     * @param id 客户id
     * @return 结果
     */
    SysCustomer getCustomerAndSignById(Long id);

    int delCustomerAndSignById(Long[] ids);
}