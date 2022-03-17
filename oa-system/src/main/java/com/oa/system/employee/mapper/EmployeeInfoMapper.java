package com.oa.system.employee.mapper;

import com.oa.system.employee.domain.EmployeeInfo;
import java.util.List;

/**
 * 员工信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-17
 */
public interface EmployeeInfoMapper 
{
    /**
     * 查询员工信息
     * 
     * @param userId 员工信息主键
     * @return 员工信息
     */
    EmployeeInfo selectEmployeeInfoByUserId(Long userId);

    /**
     * 查询员工信息列表
     * 
     * @param employeeInfo 员工信息
     * @return 员工信息集合
     */
    List<EmployeeInfo> selectEmployeeInfoList(EmployeeInfo employeeInfo);

    /**
     * 新增员工信息
     * 
     * @param employeeInfo 员工信息
     * @return 结果
     */
    int insertEmployeeInfo(EmployeeInfo employeeInfo);

    /**
     * 修改员工信息
     * 
     * @param employeeInfo 员工信息
     * @return 结果
     */
    int updateEmployeeInfo(EmployeeInfo employeeInfo);

    /**
     * 删除员工信息
     * 
     * @param userId 员工信息主键
     * @return 结果
     */
    int deleteEmployeeInfoByUserId(Long userId);

    /**
     * 批量删除员工信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEmployeeInfoByUserIds(Long[] userIds);
}
