package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.PickerOptionsMapper;
import com.gox.system.domain.form.PickerOptions;
import com.gox.system.service.IPickerOptionsService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class PickerOptionsServiceImpl implements IPickerOptionsService 
{
    @Autowired
    private PickerOptionsMapper pickerOptionsMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public PickerOptions selectPickerOptionsById(Long id)
    {
        return pickerOptionsMapper.selectPickerOptionsById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PickerOptions> selectPickerOptionsList(PickerOptions pickerOptions)
    {
        return pickerOptionsMapper.selectPickerOptionsList(pickerOptions);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPickerOptions(PickerOptions pickerOptions)
    {
        return pickerOptionsMapper.insertPickerOptions(pickerOptions);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param pickerOptions 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updatePickerOptions(PickerOptions pickerOptions)
    {
        return pickerOptionsMapper.updatePickerOptions(pickerOptions);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deletePickerOptionsByIds(Long[] ids)
    {
        return pickerOptionsMapper.deletePickerOptionsByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deletePickerOptionsById(Long id)
    {
        return pickerOptionsMapper.deletePickerOptionsById(id);
    }
}
