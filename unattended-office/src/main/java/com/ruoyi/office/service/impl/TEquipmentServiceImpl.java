package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TEquipmentMapper;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.service.ITEquipmentService;

/**
 * 设备列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TEquipmentServiceImpl extends ServiceImpl<TEquipmentMapper, TEquipment> implements ITEquipmentService
{
    @Autowired
    private TEquipmentMapper tEquipmentMapper;

    /**
     * 查询设备列表
     * 
     * @param id 设备列表主键
     * @return 设备列表
     */
    @Override
    public TEquipment selectTEquipmentById(Long id)
    {
        return tEquipmentMapper.selectTEquipmentById(id);
    }

    /**
     * 查询设备列表列表
     * 
     * @param tEquipment 设备列表
     * @return 设备列表
     */
    @Override
    public List<TEquipment> selectTEquipmentList(TEquipment tEquipment)
    {
        return tEquipmentMapper.selectTEquipmentList(tEquipment);
    }

    /**
     * 新增设备列表
     * 
     * @param tEquipment 设备列表
     * @return 结果
     */
    @Override
    public int insertTEquipment(TEquipment tEquipment)
    {
        tEquipment.setCreateTime(DateUtils.getNowDate());
        return tEquipmentMapper.insertTEquipment(tEquipment);
    }

    /**
     * 修改设备列表
     * 
     * @param tEquipment 设备列表
     * @return 结果
     */
    @Override
    public int updateTEquipment(TEquipment tEquipment)
    {
        tEquipment.setUpdateTime(DateUtils.getNowDate());
        return tEquipmentMapper.updateTEquipment(tEquipment);
    }

    /**
     * 批量删除设备列表
     * 
     * @param ids 需要删除的设备列表主键
     * @return 结果
     */
    @Override
    public int deleteTEquipmentByIds(Long[] ids)
    {
        return tEquipmentMapper.deleteTEquipmentByIds(ids);
    }

    /**
     * 删除设备列表信息
     * 
     * @param id 设备列表主键
     * @return 结果
     */
    @Override
    public int deleteTEquipmentById(Long id)
    {
        return tEquipmentMapper.deleteTEquipmentById(id);
    }
}
