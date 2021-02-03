package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.form.FieldsItem;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface FieldsItemMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public FieldsItem selectFieldsItemById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param fieldsItem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<FieldsItem> selectFieldsItemList(FieldsItem fieldsItem);
    public List<FieldsItem> selectFieldsItemListByFormId(Long formId);
    /**
     * 新增【请填写功能名称】
     * 
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    public int insertFieldsItem(FieldsItem fieldsItem);

    /**
     * 修改【请填写功能名称】
     * 
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    public int updateFieldsItem(FieldsItem fieldsItem);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteFieldsItemById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFieldsItemByIds(Long[] ids);
}
