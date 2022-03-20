package com.oa.system.employee.service.impl;

import com.oa.system.employee.domain.EmployeeInfo;
import com.oa.system.employee.mapper.EmployeeInfoMapper;
import com.oa.system.employee.service.IEmployeeInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 员工信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-17
 */
@Service
public class EmployeeInfoServiceImpl implements IEmployeeInfoService
{
    private final EmployeeInfoMapper employeeInfoMapper;

    @Autowired
    public EmployeeInfoServiceImpl(EmployeeInfoMapper employeeInfoMapper) {
        this.employeeInfoMapper = employeeInfoMapper;
    }

    /**
     * 查询员工信息
     * 
     * @param userId 员工信息主键
     * @return 员工信息
     */
    @Override
    public EmployeeInfo selectEmployeeInfoByUserId(Long userId)
    {
        return employeeInfoMapper.selectEmployeeInfoByUserId(userId);
    }

    /**
     * 查询员工信息列表
     * 
     * @param employeeInfo 员工信息
     * @return 员工信息
     */
    @Override
    public List<EmployeeInfo> selectEmployeeInfoList(EmployeeInfo employeeInfo)
    {
        return employeeInfoMapper.selectEmployeeInfoList(employeeInfo);
    }

    /**
     * 新增员工信息
     * 
     * @param employeeInfo 员工信息
     * @return 结果
     */
    @Override
    public int insertEmployeeInfo(EmployeeInfo employeeInfo)
    {
        return employeeInfoMapper.insertEmployeeInfo(employeeInfo);
    }

    /**
     * 修改员工信息
     * 
     * @param employeeInfo 员工信息
     * @return 结果
     */
    @Override
    public int updateEmployeeInfo(EmployeeInfo employeeInfo)
    {
        return employeeInfoMapper.updateEmployeeInfo(employeeInfo);
    }

    /**
     * 批量删除员工信息
     * 
     * @param userIds 需要删除的员工信息主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeInfoByUserIds(Long[] userIds)
    {
        return employeeInfoMapper.deleteEmployeeInfoByUserIds(userIds);
    }

    /**
     * 删除员工信息信息
     * 
     * @param userId 员工信息主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeInfoByUserId(Long userId)
    {
        return employeeInfoMapper.deleteEmployeeInfoByUserId(userId);
    }
}
