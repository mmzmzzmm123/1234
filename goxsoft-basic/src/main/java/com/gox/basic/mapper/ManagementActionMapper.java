package com.gox.basic.mapper;

import com.gox.basic.domain.ManagementAction;

import java.util.List;

/**
 * 管理活动Mapper接口
 *
 * @author gox
 * @date 2020-12-29
 */
public interface ManagementActionMapper {
    /**
     * 查询管理活动
     *
     * @param actionIdentifier 管理活动ID
     * @return 管理活动
     */
    public ManagementAction selectManagementActionById(Long actionIdentifier);

    /**
     * 查询管理活动列表
     *
     * @param managementAction 管理活动
     * @return 管理活动集合
     */
    public List<ManagementAction> selectManagementActionList(ManagementAction managementAction);

    /**
     * 新增管理活动
     *
     * @param managementAction 管理活动
     * @return 结果
     */
    public int insertManagementAction(ManagementAction managementAction);

    /**
     * 修改管理活动
     *
     * @param managementAction 管理活动
     * @return 结果
     */
    public int updateManagementAction(ManagementAction managementAction);

    /**
     * 删除管理活动
     *
     * @param actionIdentifier 管理活动ID
     * @return 结果
     */
    public int deleteManagementActionById(Long actionIdentifier);

    /**
     * 批量删除管理活动
     *
     * @param actionIdentifiers 需要删除的数据ID
     * @return 结果
     */
    public int deleteManagementActionByIds(Long[] actionIdentifiers);
}
