package com.gox.system.service.impl;

import java.util.Iterator;
import java.util.List;

import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.system.domain.form.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.FieldsItemMapper;
import com.gox.system.domain.form.FieldsItem;
import com.gox.system.service.IFieldsItemService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class FieldsItemServiceImpl implements IFieldsItemService 
{
    @Autowired
    private FieldsItemMapper fieldsItemMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public FieldsItem selectFieldsItemById(Long id)
    {
        return fieldsItemMapper.selectFieldsItemById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param fieldsItem 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<FieldsItem> selectFieldsItemList(FieldsItem fieldsItem)
    {
        return fieldsItemMapper.selectFieldsItemList(fieldsItem);
    }

    /**
     * 新增
     * 
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertFieldsItem(FieldsItem fieldsItem)
    {
        return fieldsItemMapper.insertFieldsItem(fieldsItem);
    }

    /**
     * 批量插入
     *
     * @param fieldsItems fieldsItem 列表 集合
     */
    @Override
    public int insertFieldsItems(Iterable<FieldsItem> fieldsItems,Long formId) {
        int c = 0;
        for (FieldsItem fi : fieldsItems) {
            Long itemId = SnowflakesTools.WORKER.nextId();
            fi.setFormId(formId);
            fi.setId(itemId);
            Config config = fi.getConfig();
            config.setItemId(itemId);
            c += insertFieldsItem(fi);

        }
        return c;
    }


    /**
     * 修改【请填写功能名称】
     * 
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateFieldsItem(FieldsItem fieldsItem)
    {
        return fieldsItemMapper.updateFieldsItem(fieldsItem);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteFieldsItemByIds(Long[] ids)
    {
        return fieldsItemMapper.deleteFieldsItemByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteFieldsItemById(Long id)
    {
        return fieldsItemMapper.deleteFieldsItemById(id);
    }
}
