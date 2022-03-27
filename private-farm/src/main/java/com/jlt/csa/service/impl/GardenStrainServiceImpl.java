package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.GardenStrainMapper;
import com.jlt.csa.domain.GardenStrain;
import com.jlt.csa.service.IGardenStrainService;

/**
 * 菜地品种Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class GardenStrainServiceImpl implements IGardenStrainService 
{
    @Autowired
    private GardenStrainMapper gardenStrainMapper;

    /**
     * 查询菜地品种
     * 
     * @param id 菜地品种主键
     * @return 菜地品种
     */
    @Override
    public GardenStrain selectGardenStrainById(Long id)
    {
        return gardenStrainMapper.selectGardenStrainById(id);
    }

    /**
     * 查询菜地品种列表
     * 
     * @param gardenStrain 菜地品种
     * @return 菜地品种
     */
    @Override
    public List<GardenStrain> selectGardenStrainList(GardenStrain gardenStrain)
    {
        return gardenStrainMapper.selectGardenStrainList(gardenStrain);
    }

    /**
     * 新增菜地品种
     * 
     * @param gardenStrain 菜地品种
     * @return 结果
     */
    @Override
    public int insertGardenStrain(GardenStrain gardenStrain)
    {
        gardenStrain.setCreateTime(DateUtils.getNowDate());
        return gardenStrainMapper.insertGardenStrain(gardenStrain);
    }

    /**
     * 修改菜地品种
     * 
     * @param gardenStrain 菜地品种
     * @return 结果
     */
    @Override
    public int updateGardenStrain(GardenStrain gardenStrain)
    {
        gardenStrain.setUpdateTime(DateUtils.getNowDate());
        return gardenStrainMapper.updateGardenStrain(gardenStrain);
    }

    /**
     * 批量删除菜地品种
     * 
     * @param ids 需要删除的菜地品种主键
     * @return 结果
     */
    @Override
    public int deleteGardenStrainByIds(Long[] ids)
    {
        return gardenStrainMapper.deleteGardenStrainByIds(ids);
    }

    /**
     * 删除菜地品种信息
     * 
     * @param id 菜地品种主键
     * @return 结果
     */
    @Override
    public int deleteGardenStrainById(Long id)
    {
        return gardenStrainMapper.deleteGardenStrainById(id);
    }
}
