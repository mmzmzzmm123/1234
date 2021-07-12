package com.apollo.usv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apollo.usv.mapper.BoatStatusMapper;
import com.apollo.usv.domain.BoatStatus;
import com.apollo.usv.service.IBoatStatusService;

/**
 * 航船状态Service业务层处理
 * 
 * @author lwq
 * @date 2021-07-02
 */
@Service
public class BoatStatusServiceImpl implements IBoatStatusService 
{
    @Autowired
    private BoatStatusMapper boatStatusMapper;

    /**
     * 查询航船状态
     * 
     * @param id 航船状态ID
     * @return 航船状态
     */
    @Override
    public BoatStatus selectBoatStatusById(Long id)
    {
        return boatStatusMapper.selectBoatStatusById(id);
    }

    /**
     * 查询航船状态列表
     * 
     * @param boatStatus 航船状态
     * @return 航船状态
     */
    @Override
    public List<BoatStatus> selectBoatStatusList(BoatStatus boatStatus)
    {
        return boatStatusMapper.selectBoatStatusList(boatStatus);
    }

    /**
     * 新增航船状态
     * 
     * @param boatStatus 航船状态
     * @return 结果
     */
    @Override
    public int insertBoatStatus(BoatStatus boatStatus)
    {
        return boatStatusMapper.insertBoatStatus(boatStatus);
    }

    /**
     * 修改航船状态
     * 
     * @param boatStatus 航船状态
     * @return 结果
     */
    @Override
    public int updateBoatStatus(BoatStatus boatStatus)
    {
        return boatStatusMapper.updateBoatStatus(boatStatus);
    }

    /**
     * 批量删除航船状态
     * 
     * @param ids 需要删除的航船状态ID
     * @return 结果
     */
    @Override
    public int deleteBoatStatusByIds(Long[] ids)
    {
        return boatStatusMapper.deleteBoatStatusByIds(ids);
    }

    /**
     * 删除航船状态信息
     * 
     * @param id 航船状态ID
     * @return 结果
     */
    @Override
    public int deleteBoatStatusById(Long id)
    {
        return boatStatusMapper.deleteBoatStatusById(id);
    }
}
