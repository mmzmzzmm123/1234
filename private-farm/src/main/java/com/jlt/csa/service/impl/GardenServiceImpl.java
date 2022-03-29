package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.GardenMapper;
import com.jlt.csa.domain.Garden;
import com.jlt.csa.service.IGardenService;

/**
 * 菜地划分Service业务层处理
 *
 * @author 郏磊涛
 * @date 2022-03-29
 */
@Service
public class GardenServiceImpl implements IGardenService
{
    @Autowired
    private GardenMapper gardenMapper;

    /**
     * 查询菜地划分
     *
     * @param id 菜地划分主键
     * @return 菜地划分
     */
    @Override
    public Garden selectGardenById(Long id)
    {
        return gardenMapper.selectGardenById(id);
    }

    /**
     * 查询菜地划分列表
     *
     * @param garden 菜地划分
     * @return 菜地划分
     */
    @Override
    public List<Garden> selectGardenList(Garden garden)
    {
        return gardenMapper.selectGardenList(garden);
    }

    /**
     * 新增菜地划分
     *
     * @param garden 菜地划分
     * @return 结果
     */
    @Override
    public int insertGarden(Garden garden)
    {
        garden.setCreateTime(DateUtils.getNowDate());
        return gardenMapper.insertGarden(garden);
    }

    /**
     * 修改菜地划分
     *
     * @param garden 菜地划分
     * @return 结果
     */
    @Override
    public int updateGarden(Garden garden)
    {
        return gardenMapper.updateGarden(garden);
    }

    /**
     * 批量修改菜地状态
     *
     * @param garden 菜地划分
     * @param ids 需要更改的菜地id集合
     * @return 结果
     */
    public int updateGardenStatus(Garden garden, Long[] ids) {
        return gardenMapper.updateGardenStatus(garden, ids);
    }

    /**
     * 批量删除菜地划分
     *
     * @param ids 需要删除的菜地划分主键
     * @return 结果
     */
    @Override
    public int deleteGardenByIds(Long[] ids)
    {
        return gardenMapper.deleteGardenByIds(ids);
    }

    /**
     * 删除菜地划分信息
     *
     * @param id 菜地划分主键
     * @return 结果
     */
    @Override
    public int deleteGardenById(Long id)
    {
        return gardenMapper.deleteGardenById(id);
    }
}
