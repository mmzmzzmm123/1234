package com.stdiet.custom.mapper;

import java.util.List;

import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import org.apache.ibatis.annotations.Param;

/**
 * 客户体征信息Mapper接口
 *
 * @author xzj
 * @date 2020-12-31
 */
public interface SysCustomerPhysicalSignsMapper
{
    /**
     * 查询客户体征信息
     *
     * @param id 客户体征信息ID
     * @return 客户体征信息
     */
    public SysCustomerPhysicalSigns selectSysCustomerPhysicalSignsById(Long id);

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
     * 删除客户体征信息
     *
     * @param id 客户体征信息ID
     * @return 结果
     */
    public int deleteSysCustomerPhysicalSignsById(Long id);

    /**
     * 批量删除客户体征信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerPhysicalSignsByIds(Long[] ids);


    //查询客户体征信息
    List<SysCustomerPhysicalSigns> selectSysCustomerAndSignList(SysCustomerPhysicalSigns sysCustomerPhysicalSigns);

    /**
     * 根据手机号查询客户以及体征
     * @param phone
     * @return
     */
    SysCustomerPhysicalSigns selectSysCustomerAndSignByPhone(String phone);

    /**
     * 根据客户ID删除对应体征信息
     * @param customerId
     * @return
     */
    int delCustomerSignByCustomerId(@Param("customerId")Long customerId);

    /**
     * 查询客户性别
     * @param cusId
     * @return
     */
    Integer getCustomerSexByCusId(Long cusId);
}