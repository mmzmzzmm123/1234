package com.ruoyi.project.system.mapper;

import java.util.List;

import com.ruoyi.project.system.domain.SysUserDept;

/**
 * 多幼儿园账户Mapper接口
 *
 * @author tsbz
 * @date 2020-06-15
 */
public interface SysUserDeptMapper {
    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserDeptByUserId(Long userId);

    /**
     * 查询多幼儿园账户
     *
     * @param userId 多幼儿园账户ID
     * @return 多幼儿园账户
     */
    public SysUserDept selectSysUserDeptById(Long userId);

    /**
     * 查询多幼儿园账户列表
     *
     * @param sysUserDept 多幼儿园账户
     * @return 多幼儿园账户集合
     */
    public List<SysUserDept> selectSysUserDeptList(SysUserDept sysUserDept);

    /**
     * 新增多幼儿园账户
     *
     * @param sysUserDept 多幼儿园账户
     * @return 结果
     */
    public int insertSysUserDept(SysUserDept sysUserDept);

    /**
     * 修改多幼儿园账户
     *
     * @param sysUserDept 多幼儿园账户
     * @return 结果
     */
    public int updateSysUserDept(SysUserDept sysUserDept);

    /**
     * 删除多幼儿园账户
     *
     * @param userId 多幼儿园账户ID
     * @return 结果
     */
    public int deleteSysUserDeptById(Long userId);

    /**
     * 批量删除多幼儿园账户
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserDeptByIds(Long[] userIds);

    /**
     * 批量新增用户部门信息
     *
     * @param userDeptList 用户角色列表
     * @return 结果
     */
    public int batchUserDept(List<SysUserDept> userDeptList);
}
