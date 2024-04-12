package com.ruoyi.autoee.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.autoee.mapper.ABuildingMapper;
import com.ruoyi.autoee.domain.ABuilding;
import com.ruoyi.autoee.service.IABuildingService;

/**
 * 建筑物管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@Service
public class ABuildingServiceImpl implements IABuildingService 
{
    @Autowired
    private ABuildingMapper aBuildingMapper;

    /**
     * 查询建筑物管理
     * 
     * @param id 建筑物管理主键
     * @return 建筑物管理
     */
    @Override
    public ABuilding selectABuildingById(Long id)
    {
        return aBuildingMapper.selectABuildingById(id);
    }

    /**
     * 查询建筑物管理列表
     * 
     * @param aBuilding 建筑物管理
     * @return 建筑物管理
     */
    @Override
    public List<ABuilding> selectABuildingList(ABuilding aBuilding)
    {
        return aBuildingMapper.selectABuildingList(aBuilding);
    }

    /**
     * 新增建筑物管理
     * 
     * @param aBuilding 建筑物管理
     * @return 结果
     */
    @Override
    public int insertABuilding(ABuilding aBuilding)
    {
        aBuilding.setCreateTime(DateUtils.getNowDate());
		aBuilding.setUpdateTime(aBuilding.getCreateTime());
        return aBuildingMapper.insertABuilding(aBuilding);
    }

    /**
     * 修改建筑物管理
     * 
     * @param aBuilding 建筑物管理
     * @return 结果
     */
    @Override
    public int updateABuilding(ABuilding aBuilding)
    {
        aBuilding.setUpdateTime(DateUtils.getNowDate());
        return aBuildingMapper.updateABuilding(aBuilding);
    }

    /**
     * 批量删除建筑物管理
     * 
     * @param ids 需要删除的建筑物管理主键
     * @return 结果
     */
    @Override
    public int deleteABuildingByIds(Long[] ids)
    {
        return aBuildingMapper.deleteABuildingByIds(ids);
    }

    /**
     * 删除建筑物管理信息
     * 
     * @param id 建筑物管理主键
     * @return 结果
     */
    @Override
    public int deleteABuildingById(Long id)
    {
        return aBuildingMapper.deleteABuildingById(id);
    }
}
