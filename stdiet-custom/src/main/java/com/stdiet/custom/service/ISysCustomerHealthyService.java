package com.stdiet.custom.service;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysCustomerHealthy;
import org.apache.ibatis.annotations.Param;

/**
 * 客户健康Service接口
 *
 * @author xzj
 * @date 2021-01-23
 */
public interface ISysCustomerHealthyService
{
    /**
     * 查询客户健康
     *
     * @param id 客户健康ID
     * @return 客户健康
     */
    public SysCustomerHealthy selectSysCustomerHealthyById(Long id);

    /**
     * 查询客户健康列表
     *
     * @param sysCustomerHealthy 客户健康
     * @return 客户健康集合
     */
    public List<SysCustomerHealthy> selectSysCustomerHealthyList(SysCustomerHealthy sysCustomerHealthy);

    /**
     * 新增或修改客户健康
     *
     * @param sysCustomerHealthy 客户健康
     * @return 结果
     */
    public AjaxResult insertOrUpdateSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy);

    /**
     * 批量删除客户健康
     *
     * @param ids 需要删除的客户健康ID
     * @return 结果
     */
    public int deleteSysCustomerHealthyByIds(Long[] ids);

    /**
     * 删除客户健康信息
     *
     * @param id 客户健康ID
     * @return 结果
     */
    public int deleteSysCustomerHealthyById(Long id);

    /**
     * 根据手机号查询客户健康评估信息
     * @param phone
     */
    SysCustomerHealthy selectSysCustomerHealthyByPhone(String phone);
}