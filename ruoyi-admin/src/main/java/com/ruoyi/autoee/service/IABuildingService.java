package com.ruoyi.autoee.service;

import java.util.List;
import com.ruoyi.autoee.domain.ABuilding;

/**
 * 建筑物管理Service接口
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
public interface IABuildingService 
{
    /**
     * 查询建筑物管理
     * 
     * @param id 建筑物管理主键
     * @return 建筑物管理
     */
    public ABuilding selectABuildingById(Long id);

    /**
     * 查询建筑物管理列表
     * 
     * @param aBuilding 建筑物管理
     * @return 建筑物管理集合
     */
    public List<ABuilding> selectABuildingList(ABuilding aBuilding);

    /**
     * 新增建筑物管理
     * 
     * @param aBuilding 建筑物管理
     * @return 结果
     */
    public int insertABuilding(ABuilding aBuilding);

    /**
     * 修改建筑物管理
     * 
     * @param aBuilding 建筑物管理
     * @return 结果
     */
    public int updateABuilding(ABuilding aBuilding);

    /**
     * 批量删除建筑物管理
     * 
     * @param ids 需要删除的建筑物管理主键集合
     * @return 结果
     */
    public int deleteABuildingByIds(Long[] ids);

    /**
     * 删除建筑物管理信息
     * 
     * @param id 建筑物管理主键
     * @return 结果
     */
    public int deleteABuildingById(Long id);
}
