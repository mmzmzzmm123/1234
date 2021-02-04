package com.gox.basic.service.impl;

import com.gox.basic.domain.RightsManagement;
import com.gox.basic.mapper.RightsManagementMapper;
import com.gox.basic.service.IRightsManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class RightsManagementServiceImpl implements IRightsManagementService 
{
    @Autowired
    private RightsManagementMapper rightsManagementMapper;

    /**
     * 查询权限管理
     * 
     * @param id 权限管理ID
     * @return 权限管理
     */
    @Override
    public RightsManagement selectRightsManagementById(Long id)
    {
        return rightsManagementMapper.selectRightsManagementById(id);
    }

    /**
     * 查询权限管理列表
     * 
     * @param rightsManagement 权限管理
     * @return 权限管理
     */
    @Override
    public List<RightsManagement> selectRightsManagementList(RightsManagement rightsManagement)
    {
        return rightsManagementMapper.selectRightsManagementList(rightsManagement);
    }

    /**
     * 新增权限管理
     * 
     * @param rightsManagement 权限管理
     * @return 结果
     */
    @Override
    public int insertRightsManagement(RightsManagement rightsManagement)
    {
        return rightsManagementMapper.insertRightsManagement(rightsManagement);
    }

    /**
     * 修改权限管理
     * 
     * @param rightsManagement 权限管理
     * @return 结果
     */
    @Override
    public int updateRightsManagement(RightsManagement rightsManagement)
    {
        return rightsManagementMapper.updateRightsManagement(rightsManagement);
    }

    /**
     * 批量删除权限管理
     * 
     * @param ids 需要删除的权限管理ID
     * @return 结果
     */
    @Override
    public int deleteRightsManagementByIds(Long[] ids)
    {
        return rightsManagementMapper.deleteRightsManagementByIds(ids);
    }

    /**
     * 删除权限管理信息
     * 
     * @param id 权限管理ID
     * @return 结果
     */
    @Override
    public int deleteRightsManagementById(Long id)
    {
        return rightsManagementMapper.deleteRightsManagementById(id);
    }
}
