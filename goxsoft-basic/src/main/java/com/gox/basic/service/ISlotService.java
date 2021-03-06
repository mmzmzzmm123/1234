package com.gox.basic.service;

import com.gox.basic.domain.form.Slot;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface ISlotService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Slot selectSlotById(Long id);

    /**
     * 查询  列表
     *
     * @param slot
     * @return   集合
     */
    public List<Slot> selectSlotList(Slot slot);

    /**
     * 新增
     *
     * @param slot
     * @return 结果
     */
    public int insertSlot(Slot slot);

    /**
     * 修改
     *
     * @param slot
     * @return 结果
     */
    public int updateSlot(Slot slot);

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteSlotByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteSlotById(Long id);
}
