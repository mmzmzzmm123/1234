package com.gox.basic.service.impl;

import com.gox.basic.domain.form.PickerOptions;
import com.gox.basic.mapper.PickerOptionsMapper;
import com.gox.basic.service.IPickerOptionsService;
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
public class PickerOptionsServiceImpl implements IPickerOptionsService {
    @Autowired
    private PickerOptionsMapper pickerOptionsMapper;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public PickerOptions selectPickerOptionsById(Long id) {
        return pickerOptionsMapper.selectPickerOptionsById(id);
    }

    /**
     * 查询  列表
     *
     * @param pickerOptions
     * @return
     */
    @Override
    public List<PickerOptions> selectPickerOptionsList(PickerOptions pickerOptions) {
        return pickerOptionsMapper.selectPickerOptionsList(pickerOptions);
    }

    /**
     * 新增
     *
     * @param pickerOptions
     * @return 结果
     */
    @Override
    public int insertPickerOptions(PickerOptions pickerOptions) {
        return pickerOptionsMapper.insertPickerOptions(pickerOptions);
    }

    @Override
    public int insertPickerOptions(Iterable<PickerOptions> pickerOptions, Long itemId) {
        int c = 0;
        for (PickerOptions pickerOption : pickerOptions) {
            pickerOption.setItemId(itemId);
            c += insertPickerOptions(pickerOption);
        }
        return c;
    }

    /**
     * 修改
     *
     * @param pickerOptions
     * @return 结果
     */
    @Override
    public int updatePickerOptions(PickerOptions pickerOptions) {
        return pickerOptionsMapper.updatePickerOptions(pickerOptions);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deletePickerOptionsByIds(Long[] ids) {
        return pickerOptionsMapper.deletePickerOptionsByIds(ids);
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deletePickerOptionsById(Long id) {
        return pickerOptionsMapper.deletePickerOptionsById(id);
    }
}
