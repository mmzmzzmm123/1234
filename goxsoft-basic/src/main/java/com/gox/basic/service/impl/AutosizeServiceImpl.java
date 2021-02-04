package com.gox.basic.service.impl;

import com.gox.basic.domain.form.Autosize;
import com.gox.basic.mapper.AutosizeMapper;
import com.gox.basic.service.IAutosizeService;
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
public class AutosizeServiceImpl implements IAutosizeService 
{
    @Autowired
    private AutosizeMapper autosizeMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Autosize selectAutosizeById(Long id)
    {
        return autosizeMapper.selectAutosizeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param autosize 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Autosize> selectAutosizeList(Autosize autosize)
    {
        return autosizeMapper.selectAutosizeList(autosize);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param autosize 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAutosize(Autosize autosize)
    {
        return autosizeMapper.insertAutosize(autosize);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param autosize 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAutosize(Autosize autosize)
    {
        return autosizeMapper.updateAutosize(autosize);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteAutosizeByIds(Long[] ids)
    {
        return autosizeMapper.deleteAutosizeByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteAutosizeById(Long id)
    {
        return autosizeMapper.deleteAutosizeById(id);
    }
}
