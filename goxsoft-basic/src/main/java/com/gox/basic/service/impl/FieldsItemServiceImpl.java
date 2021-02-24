package com.gox.basic.service.impl;

import com.gox.basic.domain.form.*;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.service.*;
import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.basic.mapper.FieldsItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private IConfigService configService;
    @Autowired
    private IStyleService styleService;
    @Autowired
    private ISlotService slotService;
    @Autowired
    private IPickerOptionsService pickerOptionsService;
    @Autowired
    private IOptionsItemService optionsItemService;
    @Autowired
    private IPropsService propsService;
    @Autowired
    private IAutosizeService autosizeService;
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

    @Override
    public List<TableFieldVo> selectTableFieldByNodeIdAndDeptId(Long nodeId, Long deptId) {

        return fieldsItemMapper.selectTableFields(nodeId,deptId);
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
    @Override
    public int updateTableFieldsBatch(Iterable<TableFieldVo> fieldVos){
        return fieldsItemMapper.updateTableFieldsBatch(fieldVos);
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
            fi.setId(itemId);
            fi.setFormId(formId);
            Config config = fi.getConfig();
            if (config!=null){
                config.setItemId(itemId);
                configService.insertConfig(config);
            }
            Slot slot = fi.getSlot();
            if (slot!=null){
                slot.setItemId(itemId);
                slotService.insertSlot(slot);
            }
            Style style = fi.getStyle();
            if (style!=null){
                style.setItemId(itemId);
                styleService.insertStyle(style);
            }
            PickerOptions pickerOptions = fi.getPickerOptions();
            if (pickerOptions!=null){
                pickerOptions.setItemId(itemId);
                pickerOptionsService.insertPickerOptions(pickerOptions);
            }
            List<OptionsItem> optionsItems = fi.getOptions();
            if (optionsItems!=null&&!optionsItems.isEmpty()){
                optionsItemService.insertOptionsItems(optionsItems,itemId);
            }
            Props props = fi.getProps();
            if (props!=null){
                props.setItemId(itemId);
                propsService.insertProps(props);
            }
            Autosize autosize = fi.getAutosize();
            if (autosize!=null){
                autosize.setItemId(itemId);
                autosizeService.insertAutosize(autosize);
            }
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
    @Override
    public int deleteFieldsByFormId(Long id){
        return fieldsItemMapper.deleteFieldsItemByFormId(id);
    }

    @Override
    public List<TableFieldVo> selectTableTitle(Long nodeId, Long deptId) {
        return fieldsItemMapper.selectTableTitle(nodeId,deptId);
    }
}
