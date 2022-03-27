package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.GardenLogAttachMapper;
import com.jlt.csa.domain.GardenLogAttach;
import com.jlt.csa.service.IGardenLogAttachService;

/**
 * 菜地日志附件Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class GardenLogAttachServiceImpl implements IGardenLogAttachService 
{
    @Autowired
    private GardenLogAttachMapper gardenLogAttachMapper;

    /**
     * 查询菜地日志附件
     * 
     * @param id 菜地日志附件主键
     * @return 菜地日志附件
     */
    @Override
    public GardenLogAttach selectGardenLogAttachById(Long id)
    {
        return gardenLogAttachMapper.selectGardenLogAttachById(id);
    }

    /**
     * 查询菜地日志附件列表
     * 
     * @param gardenLogAttach 菜地日志附件
     * @return 菜地日志附件
     */
    @Override
    public List<GardenLogAttach> selectGardenLogAttachList(GardenLogAttach gardenLogAttach)
    {
        return gardenLogAttachMapper.selectGardenLogAttachList(gardenLogAttach);
    }

    /**
     * 新增菜地日志附件
     * 
     * @param gardenLogAttach 菜地日志附件
     * @return 结果
     */
    @Override
    public int insertGardenLogAttach(GardenLogAttach gardenLogAttach)
    {
        gardenLogAttach.setCreateTime(DateUtils.getNowDate());
        return gardenLogAttachMapper.insertGardenLogAttach(gardenLogAttach);
    }

    /**
     * 修改菜地日志附件
     * 
     * @param gardenLogAttach 菜地日志附件
     * @return 结果
     */
    @Override
    public int updateGardenLogAttach(GardenLogAttach gardenLogAttach)
    {
        gardenLogAttach.setUpdateTime(DateUtils.getNowDate());
        return gardenLogAttachMapper.updateGardenLogAttach(gardenLogAttach);
    }

    /**
     * 批量删除菜地日志附件
     * 
     * @param ids 需要删除的菜地日志附件主键
     * @return 结果
     */
    @Override
    public int deleteGardenLogAttachByIds(Long[] ids)
    {
        return gardenLogAttachMapper.deleteGardenLogAttachByIds(ids);
    }

    /**
     * 删除菜地日志附件信息
     * 
     * @param id 菜地日志附件主键
     * @return 结果
     */
    @Override
    public int deleteGardenLogAttachById(Long id)
    {
        return gardenLogAttachMapper.deleteGardenLogAttachById(id);
    }
}
