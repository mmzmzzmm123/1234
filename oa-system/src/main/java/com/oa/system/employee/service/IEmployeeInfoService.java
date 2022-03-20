package com.oa.system.employee.service;

import com.oa.system.employee.domain.EmployeeInfo;
import java.util.List;

/**
 * 员工信息Service接口
 * 
 * @author ruoyi
 * @date 2022-03-17
 */
public interface IEmployeeInfoService 
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
     * 批量删除员工信息
     * 
     * @param userIds 需要删除的员工信息主键集合
     * @return 结果
     */
    int deleteEmployeeInfoByUserIds(Long[] userIds);

    /**
     * 删除员工信息信息
     * 
     * @param userId 员工信息主键
     * @return 结果
     */
    int deleteEmployeeInfoByUserId(Long userId);
}
