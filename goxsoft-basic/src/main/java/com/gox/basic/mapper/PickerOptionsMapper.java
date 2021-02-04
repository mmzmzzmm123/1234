package com.gox.basic.mapper;

import com.gox.basic.domain.form.PickerOptions;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface PickerOptionsMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public PickerOptions selectPickerOptionsById(Long id);
    public PickerOptions selectPickerOptionsByItemId(Long itemId);
    /**
     * 查询【请填写功能名称】列表
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PickerOptions> selectPickerOptionsList(PickerOptions pickerOptions);

    /**
     * 新增【请填写功能名称】
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 结果
     */
    public int insertPickerOptions(PickerOptions pickerOptions);

    /**
     * 修改【请填写功能名称】
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 结果
     */
    public int updatePickerOptions(PickerOptions pickerOptions);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePickerOptionsById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePickerOptionsByIds(Long[] ids);
}
