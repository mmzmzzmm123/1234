package com.gox.system.service;

import java.util.List;
import com.gox.system.domain.form.PickerOptions;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface IPickerOptionsService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public PickerOptions selectPickerOptionsById(Long id);

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

    int insertPickerOptions(Iterable<PickerOptions> pickerOptions, Long itemId);

    /**
     * 修改【请填写功能名称】
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 结果
     */
    public int updatePickerOptions(PickerOptions pickerOptions);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deletePickerOptionsByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePickerOptionsById(Long id);
}
