package com.gox.basic.service.impl;

import com.gox.basic.domain.form.Style;
import com.gox.basic.mapper.StyleMapper;
import com.gox.basic.service.IStyleService;
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
public class StyleServiceImpl implements IStyleService {
    @Autowired
    private StyleMapper styleMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Style selectStyleById(Long id) {
        return styleMapper.selectStyleById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param style 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Style> selectStyleList(Style style) {
        return styleMapper.selectStyleList(style);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param style 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertStyle(Style style) {
        return styleMapper.insertStyle(style);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param style 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateStyle(Style style) {
        return styleMapper.updateStyle(style);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteStyleByIds(Long[] ids) {
        return styleMapper.deleteStyleByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteStyleById(Long id) {
        return styleMapper.deleteStyleById(id);
    }
}
