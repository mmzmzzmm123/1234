package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TRoomPackage;

/**
 * 房间套餐价格Mapper接口
 *
 * @author ruoyi
 * @date 2023-09-14
 */
public interface TRoomPackageMapper extends BaseMapper<TRoomPackage> {
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
     * 删除房间套餐价格
     *
     * @param id 房间套餐价格主键
     * @return 结果
     */
    public int deleteTRoomPackageById(Long id);

    /**
     * 批量删除房间套餐价格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTRoomPackageByIds(Long[] ids);
}
