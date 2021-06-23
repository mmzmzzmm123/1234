package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysCustomerHealthyExtended;

/**
 * 客户健康信息补充Mapper接口
 *
 * @author wonder
 * @date 2021-06-21
 */
public interface SysCustomerHealthyExtendedMapper
{
    /**
     * 查询客户健康信息补充
     *
     * @param id 客户健康信息补充ID
     * @return 客户健康信息补充
     */
    public SysCustomerHealthyExtended selectSysCustomerHealthyExtendedById(Long id);

    /**
     * 查询客户健康信息补充列表
     *
     * @param sysCustomerHealthyExtended 客户健康信息补充
     * @return 客户健康信息补充集合
     */
    public List<SysCustomerHealthyExtended> selectSysCustomerHealthyExtendedList(SysCustomerHealthyExtended sysCustomerHealthyExtended);

    /**
     * 新增客户健康信息补充
     *
     * @param sysCustomerHealthyExtended 客户健康信息补充
     * @return 结果
     */
    public int insertSysCustomerHealthyExtended(SysCustomerHealthyExtended sysCustomerHealthyExtended);

    /**
     * 修改客户健康信息补充
     *
     * @param sysCustomerHealthyExtended 客户健康信息补充
     * @return 结果
     */
    public int updateSysCustomerHealthyExtended(SysCustomerHealthyExtended sysCustomerHealthyExtended);

    /**
     * 删除客户健康信息补充
     *
     * @param id 客户健康信息补充ID
     * @return 结果
     */
    public int deleteSysCustomerHealthyExtendedById(Long id);

    /**
     * 批量删除客户健康信息补充
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerHealthyExtendedByIds(Long[] ids);
}