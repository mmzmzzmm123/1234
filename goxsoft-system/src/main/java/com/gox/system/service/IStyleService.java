package com.gox.system.service;

import java.util.List;
import com.gox.system.domain.form.Style;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface IStyleService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Style selectStyleById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param style 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Style> selectStyleList(Style style);

    /**
     * 新增【请填写功能名称】
     * 
     * @param style 【请填写功能名称】
     * @return 结果
     */
    public int insertStyle(Style style);

    /**
     * 修改【请填写功能名称】
     * 
     * @param style 【请填写功能名称】
     * @return 结果
     */
    public int updateStyle(Style style);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteStyleByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteStyleById(Long id);
}
