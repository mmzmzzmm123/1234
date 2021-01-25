package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.RightsManagement;

/**
 * 权限管理Mapper接口
 * 
 * @author gox
 * @date 2020-12-29
 */
public interface RightsManagementMapper 
{
    /**
     * 查询权限管理
     * 
     * @param id 权限管理ID
     * @return 权限管理
     */
    public RightsManagement selectRightsManagementById(Long id);
    List<RightsManagement> selectRmByMdId(Long id);
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
     * 删除权限管理
     * 
     * @param id 权限管理ID
     * @return 结果
     */
    public int deleteRightsManagementById(Long id);

    /**
     * 批量删除权限管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRightsManagementByIds(Long[] ids);
}
