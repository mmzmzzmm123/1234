package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.domain.vo.EquipeAvailableQryVo;

/**
 * 设备列表Service接口
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
public interface ITEquipmentService 
{
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
     * 批量删除设备列表
     * 
     * @param ids 需要删除的设备列表主键集合
     * @return 结果
     */
    public int deleteTEquipmentByIds(Long[] ids);

    /**
     * 删除设备列表信息
     * 
     * @param id 设备列表主键
     * @return 结果
     */
    public int deleteTEquipmentById(Long id);

    List<TEquipment> selectAvailableEquipmentList(EquipeAvailableQryVo tEquipment);
}
