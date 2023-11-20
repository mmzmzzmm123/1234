package com.ruoyi.ufreight.service;

import java.util.List;
import com.ruoyi.ufreight.domain.Inventory;

/**
 * 库存Service接口
 * 
 * @author derek
 * @date 2023-11-20
 */
public interface IInventoryService 
{
    /**
     * 查询库存
     * 
     * @param inventoryId 库存主键
     * @return 库存
     */
    public Inventory selectInventoryByInventoryId(Long inventoryId);

    /**
     * 查询库存列表
     * 
     * @param inventory 库存
     * @return 库存集合
     */
    public List<Inventory> selectInventoryList(Inventory inventory);

    /**
     * 新增库存
     * 
     * @param inventory 库存
     * @return 结果
     */
    public int insertInventory(Inventory inventory);

    /**
     * 修改库存
     * 
     * @param inventory 库存
     * @return 结果
     */
    public int updateInventory(Inventory inventory);

    /**
     * 批量删除库存
     * 
     * @param inventoryIds 需要删除的库存主键集合
     * @return 结果
     */
    public int deleteInventoryByInventoryIds(Long[] inventoryIds);

    /**
     * 删除库存信息
     * 
     * @param inventoryId 库存主键
     * @return 结果
     */
    public int deleteInventoryByInventoryId(Long inventoryId);
}
