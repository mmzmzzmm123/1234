package com.ruoyi.project.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.SysUserDeptMapper;
import com.ruoyi.project.system.domain.SysUserDept;
import com.ruoyi.project.system.service.ISysUserDeptService;

/**
 * 多幼儿园账户Service业务层处理
 *
 * @author tsbz
 * @date 2020-06-15
 */
@Service
public class SysUserDeptServiceImpl implements ISysUserDeptService {
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    /**
     * 查询多幼儿园账户
     *
     * @param userId 多幼儿园账户ID
     * @return 多幼儿园账户
     */
    @Override
    public SysUserDept selectSysUserDeptById(Long userId) {
        return sysUserDeptMapper.selectSysUserDeptById(userId);
    }

    /**
     * 查询多幼儿园账户列表
     *
     * @param sysUserDept 多幼儿园账户
     * @return 多幼儿园账户
     */
    @Override
    public List<SysUserDept> selectSysUserDeptList(SysUserDept sysUserDept) {
        return sysUserDeptMapper.selectSysUserDeptList(sysUserDept);
    }

    /**
     * 新增多幼儿园账户
     *
     * @param sysUserDept 多幼儿园账户
     * @return 结果
     */
    @Override
    public int insertSysUserDept(SysUserDept sysUserDept) {
        return sysUserDeptMapper.insertSysUserDept(sysUserDept);
    }

    /**
     * 修改多幼儿园账户
     *
     * @param sysUserDept 多幼儿园账户
     * @return 结果
     */
    @Override
    public int updateSysUserDept(SysUserDept sysUserDept) {
        return sysUserDeptMapper.updateSysUserDept(sysUserDept);
    }

    /**
     * 批量删除多幼儿园账户
     *
     * @param userIds 需要删除的多幼儿园账户ID
     * @return 结果
     */
    @Override
    public int deleteSysUserDeptByIds(Long[] userIds) {
        return sysUserDeptMapper.deleteSysUserDeptByIds(userIds);
    }

    /**
     * 删除多幼儿园账户信息
     *
     * @param userId 多幼儿园账户ID
     * @return 结果
     */
    @Override
    public int deleteSysUserDeptById(Long userId) {
        return sysUserDeptMapper.deleteSysUserDeptById(userId);
    }
}
