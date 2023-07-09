package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.office.domain.TEquipment;

/**
 * 设备列表Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-29
 */
public interface TEquipmentMapper extends BaseMapper<TEquipment> {
    /**
     * 查询设备列表
     *
     * @param id 设备列表主键
     * @return 设备列表
     */
    public TEquipment selectTEquipmentById(Long id);

    /**
     * 查询设备列表列表
     *
     * @param tEquipment 设备列表
     * @return 设备列表集合
     */
    public List<TEquipment> selectTEquipmentList(TEquipment tEquipment);

    /**
     * 新增设备列表
     *
     * @param tEquipment 设备列表
     * @return 结果
     */
    public int insertTEquipment(TEquipment tEquipment);

    /**
     * 修改设备列表
     *
     * @param tEquipment 设备列表
     * @return 结果
     */
    public int updateTEquipment(TEquipment tEquipment);

    /**
     * 删除设备列表
     *
     * @param id 设备列表主键
     * @return 结果
     */
    public int deleteTEquipmentById(Long id);

    /**
     * 批量删除设备列表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTEquipmentByIds(Long[] ids);

}
