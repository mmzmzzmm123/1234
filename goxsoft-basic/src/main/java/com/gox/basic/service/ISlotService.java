package com.gox.basic.service;

import com.gox.basic.domain.form.Slot;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface ISlotService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Slot selectSlotById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param slot 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Slot> selectSlotList(Slot slot);

    /**
     * 新增【请填写功能名称】
     * 
     * @param slot 【请填写功能名称】
     * @return 结果
     */
    public int insertSlot(Slot slot);

    /**
     * 修改【请填写功能名称】
     * 
     * @param slot 【请填写功能名称】
     * @return 结果
     */
    public int updateSlot(Slot slot);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSlotByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSlotById(Long id);
}
