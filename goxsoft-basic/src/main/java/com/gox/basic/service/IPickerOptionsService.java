package com.gox.basic.service;

import com.gox.basic.domain.form.PickerOptions;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IPickerOptionsService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public PickerOptions selectPickerOptionsById(Long id);

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

    int insertPickerOptions(Iterable<PickerOptions> pickerOptions, Long itemId);

    /**
     * 修改
     *
     * @param pickerOptions
     * @return 结果
     */
    public int updatePickerOptions(PickerOptions pickerOptions);

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deletePickerOptionsByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deletePickerOptionsById(Long id);
}
