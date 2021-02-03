package com.gox.system.service.impl;

import java.util.List;

import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.system.domain.form.OptionsItem;
import com.gox.system.service.IOptionsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.SlotMapper;
import com.gox.system.domain.form.Slot;
import com.gox.system.service.ISlotService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class SlotServiceImpl implements ISlotService 
{
    @Autowired
    private SlotMapper slotMapper;
    @Autowired
    private IOptionsItemService optionsItemService;
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Slot selectSlotById(Long id)
    {
        return slotMapper.selectSlotById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param slot 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Slot> selectSlotList(Slot slot)
    {
        return slotMapper.selectSlotList(slot);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param slot 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSlot(Slot slot)
    {
        Long slotId = SnowflakesTools.WORKER.nextId();
        List<OptionsItem> ops = slot.getOptions();
        slot.setId(slotId);
        if (ops!=null&&!ops.isEmpty()){
            //slot.setItemId(slotId);
            optionsItemService.insertOptionsItemsSlot(ops,slotId);
        }
        return slotMapper.insertSlot(slot);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param slot 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSlot(Slot slot)
    {
        return slotMapper.updateSlot(slot);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSlotByIds(Long[] ids)
    {
        return slotMapper.deleteSlotByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSlotById(Long id)
    {
        return slotMapper.deleteSlotById(id);
    }
}
