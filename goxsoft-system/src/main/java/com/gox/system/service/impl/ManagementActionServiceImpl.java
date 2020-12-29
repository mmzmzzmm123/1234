package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.ManagementActionMapper;
import com.gox.system.domain.ManagementAction;
import com.gox.system.service.IManagementActionService;

/**
 * 管理活动Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class ManagementActionServiceImpl implements IManagementActionService 
{
    @Autowired
    private ManagementActionMapper managementActionMapper;

    /**
     * 查询管理活动
     * 
     * @param actionIdentifier 管理活动ID
     * @return 管理活动
     */
    @Override
    public ManagementAction selectManagementActionById(Long actionIdentifier)
    {
        return managementActionMapper.selectManagementActionById(actionIdentifier);
    }

    /**
     * 查询管理活动列表
     * 
     * @param managementAction 管理活动
     * @return 管理活动
     */
    @Override
    public List<ManagementAction> selectManagementActionList(ManagementAction managementAction)
    {
        return managementActionMapper.selectManagementActionList(managementAction);
    }

    /**
     * 新增管理活动
     * 
     * @param managementAction 管理活动
     * @return 结果
     */
    @Override
    public int insertManagementAction(ManagementAction managementAction)
    {
        return managementActionMapper.insertManagementAction(managementAction);
    }

    /**
     * 修改管理活动
     * 
     * @param managementAction 管理活动
     * @return 结果
     */
    @Override
    public int updateManagementAction(ManagementAction managementAction)
    {
        return managementActionMapper.updateManagementAction(managementAction);
    }

    /**
     * 批量删除管理活动
     * 
     * @param actionIdentifiers 需要删除的管理活动ID
     * @return 结果
     */
    @Override
    public int deleteManagementActionByIds(Long[] actionIdentifiers)
    {
        return managementActionMapper.deleteManagementActionByIds(actionIdentifiers);
    }

    /**
     * 删除管理活动信息
     * 
     * @param actionIdentifier 管理活动ID
     * @return 结果
     */
    @Override
    public int deleteManagementActionById(Long actionIdentifier)
    {
        return managementActionMapper.deleteManagementActionById(actionIdentifier);
    }
}
