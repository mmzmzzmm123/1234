package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;

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
     * 查询客户体征信息列表
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 客户体征信息集合
     */
    public List<SysCustomerPhysicalSigns> selectSysCustomerPhysicalSignsList(SysCustomerPhysicalSigns sysCustomerPhysicalSigns);

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
}