package com.gox.basic.mapper;

import com.gox.basic.domain.form.PickerOptions;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface PickerOptionsMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public PickerOptions selectPickerOptionsById(Long id);

    public PickerOptions selectPickerOptionsByItemId(Long itemId);

    /**
     * 查询  列表
     *
     * @param pickerOptions
     * @return   集合
     */
    public List<PickerOptions> selectPickerOptionsList(PickerOptions pickerOptions);

    /**
     * 新增
     *
     * @param pickerOptions
     * @return 结果
     */
    public int insertPickerOptions(PickerOptions pickerOptions);

    /**
     * 修改
     *
     * @param pickerOptions
     * @return 结果
     */
    public int updatePickerOptions(PickerOptions pickerOptions);

    /**
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deletePickerOptionsById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePickerOptionsByIds(Long[] ids);
}
