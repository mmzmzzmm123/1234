package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TRoomPackage;

/**
 * 房间套餐价格Service接口
 * 
 * @author ruoyi
 * @date 2023-09-14
 */
public interface ITRoomPackageService 
{
    /**
     * 查询房间套餐价格
     * 
     * @param id 房间套餐价格主键
     * @return 房间套餐价格
     */
    public TRoomPackage selectTRoomPackageById(Long id);

    /**
     * 查询房间套餐价格列表
     * 
     * @param tRoomPackage 房间套餐价格
     * @return 房间套餐价格集合
     */
    public List<TRoomPackage> selectTRoomPackageList(TRoomPackage tRoomPackage);

    /**
     * 新增房间套餐价格
     * 
     * @param tRoomPackage 房间套餐价格
     * @return 结果
     */
    public int insertTRoomPackage(TRoomPackage tRoomPackage);

    /**
     * 修改房间套餐价格
     * 
     * @param tRoomPackage 房间套餐价格
     * @return 结果
     */
    public int updateTRoomPackage(TRoomPackage tRoomPackage);

    /**
     * 批量删除房间套餐价格
     * 
     * @param ids 需要删除的房间套餐价格主键集合
     * @return 结果
     */
    public int deleteTRoomPackageByIds(Long[] ids);

    /**
     * 删除房间套餐价格信息
     * 
     * @param id 房间套餐价格主键
     * @return 结果
     */
    public int deleteTRoomPackageById(Long id);
}
