package com.gox.basic.service.impl;

import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.basic.domain.form.OptionsItem;
import com.gox.basic.domain.form.Slot;
import com.gox.basic.mapper.SlotMapper;
import com.gox.basic.service.IOptionsItemService;
import com.gox.basic.service.ISlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *   Service业务层处理
 *
 * @author gox
 * @date 2021-02-02
 */
@Service
public class SlotServiceImpl implements ISlotService {
    @Autowired
    private SlotMapper slotMapper;
    @Autowired
    private IOptionsItemService optionsItemService;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public Slot selectSlotById(Long id) {
        return slotMapper.selectSlotById(id);
    }

    /**
     * 查询  列表
     *
     * @param slot
     * @return
     */
    @Override
    public List<Slot> selectSlotList(Slot slot) {
        return slotMapper.selectSlotList(slot);
    }

    /**
     * 新增
     *
     * @param slot
     * @return 结果
     */
    @Override
    public int insertSlot(Slot slot) {
        Long slotId = SnowflakesTools.WORKER.nextId();
        List<OptionsItem> ops = slot.getOptions();
        slot.setId(slotId);
        if (ops != null && !ops.isEmpty()) {
            //slot.setItemId(slotId);
            optionsItemService.insertOptionsItemsSlot(ops, slotId);
        }
        return slotMapper.insertSlot(slot);
    }

    /**
     * 修改
     *
     * @param slot
     * @return 结果
     */
    @Override
    public int updateSlot(Slot slot) {
        return slotMapper.updateSlot(slot);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteSlotByIds(Long[] ids) {
        return slotMapper.deleteSlotByIds(ids);
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteSlotById(Long id) {
        return slotMapper.deleteSlotById(id);
    }
}
