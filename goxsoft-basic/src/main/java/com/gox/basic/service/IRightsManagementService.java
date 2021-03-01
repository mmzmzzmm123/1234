package com.gox.basic.service;

import com.gox.basic.domain.RightsManagement;

import java.util.List;

/**
 * 权限管理Service接口
 *
 * @author gox
 * @date 2020-12-29
 */
public interface IRightsManagementService {
    /**
     * 查询权限管理
     *
     * @param id 权限管理ID
     * @return 权限管理
     */
    public RightsManagement selectRightsManagementById(Long id);

    /**
     * 查询权限管理列表
     *
     * @param rightsManagement 权限管理
     * @return 权限管理集合
     */
    public List<RightsManagement> selectRightsManagementList(RightsManagement rightsManagement);

    /**
     * 新增权限管理
     *
     * @param rightsManagement 权限管理
     * @return 结果
     */
    public int insertRightsManagement(RightsManagement rightsManagement);

    /**
     * 修改权限管理
     *
     * @param rightsManagement 权限管理
     * @return 结果
     */
    public int updateRightsManagement(RightsManagement rightsManagement);

    /**
     * 批量删除权限管理
     *
     * @param ids 需要删除的权限管理ID
     * @return 结果
     */
    public int deleteRightsManagementByIds(Long[] ids);

    /**
     * 删除权限管理信息
     *
     * @param id 权限管理ID
     * @return 结果
     */
    public int deleteRightsManagementById(Long id);
}
